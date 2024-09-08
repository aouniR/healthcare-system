package com.healthcare.metamodel_service.kafka;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.healthcare.metamodel_service.entity.MetaModel;

@Component
@RequiredArgsConstructor
public class MetaModeleEventProducer  {

    @Value("${spring.kafka.topic.metamodel-events}")
    private String metaModelEventsTopic;
    private KafkaTemplate<String, MetaModel> kafkaTemplate;

    public void sendMetaModelCreatedEvent(MetaModel metaModel) {
        kafkaTemplate.send(metaModelEventsTopic, "METAMODEL_CREATED", metaModel);
    }

    public void sendMetaModelDeletedEvent(MetaModel metaModel) {
        kafkaTemplate.send(metaModelEventsTopic, "METAMODEL_DELETED", metaModel);
    }

    public void sendMetaModelUpdatedEvent(MetaModel metaModel) {
        kafkaTemplate.send(metaModelEventsTopic, "METAMODEL_UPDATED", metaModel);
    }
}