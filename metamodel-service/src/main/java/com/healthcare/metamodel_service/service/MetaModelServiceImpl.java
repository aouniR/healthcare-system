package com.healthcare.metamodel_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.healthcare.metamodel_service.entity.MetaModel;
import com.healthcare.metamodel_service.entity.TypeMetaModel;
import com.healthcare.metamodel_service.kafka.MetaModeleEventProducer;
import com.healthcare.metamodel_service.repository.MetaModelRepository;
import com.healthcare.metamodel_service.request.RegisterRequest;
import com.healthcare.metamodel_service.request.AddFieldsRequest;
import com.healthcare.metamodel_service.request.DeleteFieldsRequest;

import jakarta.ws.rs.NotFoundException;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MetaModelServiceImpl implements MetaModelService {

    private final MetaModelRepository metaModelRepository;
    private MetaModeleEventProducer metaModelEventProducer;

    @Override
    public List<MetaModel> getAllMetaModels() {
        return metaModelRepository.findAllByOrderByCreationTimestampDesc();
    }

    @Override
    public List<Integer> getNumberMetaModelsByMonth(int year) {
        List<MetaModel> metamodels = metaModelRepository.findAll();
        List<Integer> monthCounts = new ArrayList<>(12);

        for (int i = 0; i < 12; i++) {
            monthCounts.add(0);
        }

        for (MetaModel mono : metamodels) {
            LocalDateTime creationDate = mono.getCreationTimestamp();
            if (creationDate.getYear() == year) {
                int month = creationDate.getMonthValue(); 
                monthCounts.set(month - 1, monthCounts.get(month - 1) + 1); 
            }
        }
        return monthCounts;
    }

    @Override
    public MetaModel getMetaModelById(UUID id) {
        return metaModelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MetaModel not found"));
    }

    @Override
    public Optional<List<MetaModel>> getMetaModelByType(TypeMetaModel type){
        Optional<List<MetaModel>> metaModels = metaModelRepository.findByType(type);
        return metaModels;
    }

    @Override
    public MetaModel addFieldsToMetaModelById(UUID id, AddFieldsRequest request) {
        MetaModel existingMetaModel = metaModelRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("MetaModel not found"));

        JsonNode existingFields = existingMetaModel.getFields();
        JsonNode newFields = request.getFields();

        if (existingFields != null && existingFields.isObject()) {
            ((ObjectNode) existingFields).setAll((ObjectNode) newFields);
            existingMetaModel.setFields(existingFields);
        }else{
            existingMetaModel.setFields(newFields);
        }
 
        MetaModel savedMetaModel = metaModelRepository.save(existingMetaModel);
        metaModelEventProducer.sendMetaModelUpdatedEvent(savedMetaModel);

        return savedMetaModel;
    }

    @Override
    public MetaModel deleteFieldFromMetaModelById(UUID id,DeleteFieldsRequest request) {
        MetaModel existingMetaModel = metaModelRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("MetaModel not found"));

        JsonNode existingFields = existingMetaModel.getFields();

        if (existingFields != null && existingFields.isObject()) {
            ((ObjectNode) existingFields).remove(request.getFieldKey());
            existingMetaModel.setFields(existingFields);
        } else {
            throw new IllegalStateException("Fields are not in a valid format.");
        }
 
        MetaModel savedMetaModel = metaModelRepository.save(existingMetaModel);
        metaModelEventProducer.sendMetaModelUpdatedEvent(savedMetaModel);

        return savedMetaModel;
    }

    @Override
    public MetaModel saveMetamodel(RegisterRequest request, UUID userId) {

        MetaModel toSave = MetaModel.builder()
            .description(request.getDescription())
            .type(request.getType())
            .fields(request.getFields())
            .creatorId(userId)
            .build();
    
            MetaModel savedMetaModel = metaModelRepository.save(toSave);
    
        metaModelEventProducer.sendMetaModelCreatedEvent(savedMetaModel);
        return savedMetaModel;
    }
    

    @Override
    public void deleteMEtaModelById(UUID id) {
        MetaModel toDelete = metaModelRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("MetaModel not found"));
            metaModelRepository.delete(toDelete);
        metaModelEventProducer.sendMetaModelDeletedEvent(toDelete);
    }

}

