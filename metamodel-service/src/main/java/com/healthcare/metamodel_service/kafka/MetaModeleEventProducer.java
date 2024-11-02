package com.healthcare.metamodel_service.kafka;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.healthcare.metamodel_service.entity.MetaModel;
import com.healthcare.metamodel_service.dto.MetaModelDto;


@Component
@RequiredArgsConstructor
public class MetaModeleEventProducer  {

    @Value("${spring.kafka.topic.metamodel-events}")
    private String metaModelEventsTopic;

    private final KafkaTemplate<String, MetaModelDto> kafkaTemplate;

    public void sendMetaModelCreatedEvent(MetaModel metaModel) {
        MetaModelDto metaModelDto = new MetaModelDto();
        metaModelDto.setId(metaModel.getId());
        metaModelDto.setDescription(metaModel.getDescription());
        metaModelDto.setFields(metaModel.getFields());
        metaModelDto.setType(metaModel.getType());
        metaModelDto.setCreatorId(metaModel.getCreatorId());
        metaModelDto.setCreationTimestamp(metaModel.getCreationTimestamp());
        metaModelDto.setUpdateTimestamp(metaModel.getUpdateTimestamp());
        kafkaTemplate.send(metaModelEventsTopic, "METAMODEL_CREATED", metaModelDto);
    }

    public void sendMetaModelDeletedEvent(MetaModel metaModel) {
        MetaModelDto metaModelDto = new MetaModelDto();
        metaModelDto.setId(metaModel.getId());
        metaModelDto.setDescription(metaModel.getDescription());
        metaModelDto.setFields(metaModel.getFields());
        metaModelDto.setType(metaModel.getType());
        metaModelDto.setCreatorId(metaModel.getCreatorId());
        metaModelDto.setCreationTimestamp(metaModel.getCreationTimestamp());
        metaModelDto.setUpdateTimestamp(metaModel.getUpdateTimestamp());
        kafkaTemplate.send(metaModelEventsTopic, "METAMODEL_DELETED", metaModelDto);
    }

    public void sendMetaModelUpdatedEvent(MetaModel metaModel) {
        MetaModelDto metaModelDto = new MetaModelDto();
        metaModelDto.setId(metaModel.getId());
        metaModelDto.setDescription(metaModel.getDescription());
        metaModelDto.setFields(metaModel.getFields());
        metaModelDto.setType(metaModel.getType());
        metaModelDto.setCreatorId(metaModel.getCreatorId());
        metaModelDto.setCreationTimestamp(metaModel.getCreationTimestamp());
        metaModelDto.setUpdateTimestamp(metaModel.getUpdateTimestamp());
        kafkaTemplate.send(metaModelEventsTopic, "METAMODEL_UPDATED", metaModelDto);
    }
}