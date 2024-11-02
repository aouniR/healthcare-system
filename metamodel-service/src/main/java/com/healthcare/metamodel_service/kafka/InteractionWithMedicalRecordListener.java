package com.healthcare.metamodel_service.kafka;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.metamodel_service.dto.MetaModelDto;
import com.healthcare.metamodel_service.dto.RequestDto;
import com.healthcare.metamodel_service.entity.TypeMetaModel;
import com.healthcare.metamodel_service.repository.MetaModelRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InteractionWithMedicalRecordListener {
    
    private final InteractionWithMedicalRecordProducer interactionWithMedicalRecordProducer; 
    private static final Logger log = LoggerFactory.getLogger(InteractionWithMedicalRecordListener.class);
    private final MetaModelRepository metaModelRepository;
    private final ObjectMapper objectMapper; 

    @KafkaListener(topics = "${spring.kafka.topic.metaModelProducer-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenForMetaModelFieldsRequest(@Header(KafkaHeaders.RECEIVED_KEY) String key, @Payload String message) {
        log.info("Received fields request with key: {} and message: {}", key, message);
        
        if ("METAMODEL-FIELDS-REQUEST".equals(key)) {
            try {
                RequestDto requestDto = objectMapper.readValue(message, RequestDto.class);

                UUID metamodelId = requestDto.getMetamodelId();
                TypeMetaModel typeMetaModel = requestDto.getTypeMetaModel();
                Optional<MetaModelDto> metamodelOpt = fetchMetaModel(metamodelId, typeMetaModel);
                metamodelOpt.ifPresentOrElse(
                    metamodel -> interactionWithMedicalRecordProducer.sendMetamodelResponse(metamodel),
                    () -> {
                        log.warn("No meta model found for ID: {}", metamodelId);
                        interactionWithMedicalRecordProducer.sendMetamodelResponse(null);
                    }
                );
            } catch (Exception e) {
                log.error("Failed to process notification", e);
                throw new RuntimeException("Failed to save notification", e);
            }
        }
    }

    private Optional<MetaModelDto> fetchMetaModel(UUID metamodelId, TypeMetaModel typeMetaModel) {
        return metaModelRepository.findById(metamodelId)
            .filter(metamodel -> metamodel.getType() == typeMetaModel)
            .map(metamodel -> {
                MetaModelDto metaModelDto = new MetaModelDto();
                metaModelDto.setId(metamodel.getId());
                metaModelDto.setDescription(metamodel.getDescription());
                metaModelDto.setFields(metamodel.getFields());
                metaModelDto.setType(metamodel.getType());
                metaModelDto.setCreatorId(metamodel.getCreatorId());
                metaModelDto.setCreationTimestamp(metamodel.getCreationTimestamp());
                metaModelDto.setUpdateTimestamp(metamodel.getUpdateTimestamp());
                return metaModelDto;
            });
    }
}
