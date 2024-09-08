package com.healthcare.metamodel_service.service;

import com.healthcare.metamodel_service.entity.MetaModel;
import com.healthcare.metamodel_service.kafka.MetaModeleEventProducer;
import com.healthcare.metamodel_service.repository.MetaModelRepository;
import com.healthcare.metamodel_service.request.ComposantRequestEvent;
import com.healthcare.metamodel_service.request.RegisterRequest;

import jakarta.ws.rs.NotFoundException;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MetaModelServiceImpl implements MetaModelService {
    
    private final KafkaTemplate<String, ComposantRequestEvent> kafkaTemplate;
    private final MetaModelRepository metaModelRepository;
    private MetaModeleEventProducer metaModelEventProducer;
    private final ModelMapper modelMapper;

    @Override
    public List<MetaModel> getAllMetaModels() {
        return metaModelRepository.findAllByOrderByCreationTimestampDesc();
    }

    @Override
    public MetaModel getMetaModelById(UUID id) {
        return metaModelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MetaModel not found"));
    }

    @Override
    public MetaModel updateMetaModelById(UUID id, RegisterRequest request) {
        MetaModel existingMetaModel = metaModelRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("MetaModel not found"));
        modelMapper.map(request, existingMetaModel);
        MetaModel updatedMetaModel= metaModelRepository.save(existingMetaModel);
        metaModelEventProducer.sendMetaModelUpdatedEvent(updatedMetaModel);
        return updatedMetaModel;
    }

    @Override
    public MetaModel saveMetamodel(RegisterRequest request, UUID userId) {
        MetaModel toSave = MetaModel.builder()
            .description(request.getDescription())
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

    @Override
    public void requestComposants(List<UUID> ids) {
        ComposantRequestEvent event = new ComposantRequestEvent(ids);
        kafkaTemplate.send("composant-request-topic", event);
    }

}

