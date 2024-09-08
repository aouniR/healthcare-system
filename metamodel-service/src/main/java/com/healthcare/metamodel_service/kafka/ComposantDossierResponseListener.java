package com.healthcare.metamodel_service.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.healthcare.metamodel_service.entity.MetaModel;
import com.healthcare.metamodel_service.repository.MetaModelRepository;
import com.healthcare.metamodel_service.response.ComposantResponseEvent;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComposantDossierResponseListener {

    @Value("${spring.kafka.topic.metamodel-events}")
    private String metaModelEventsTopic;
    private final KafkaTemplate<String, MetaModel> kafkaTemplate;
    private final MetaModelRepository metaModelRepository;

    @KafkaListener(topics = "${spring.kafka.topic.composant-response-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleComposantResponse(ComposantResponseEvent responseEvent) {
        MetaModel metaModel = metaModelRepository.findById(responseEvent.getMetaModelId())
            .orElseThrow(() -> new EntityNotFoundException("MetaModel not found"));
        metaModel.getComposantDossierIds().addLast(responseEvent.getComposantDossierId());
        kafkaTemplate.send(metaModelEventsTopic, "MetaModel_UPDATED", metaModel);
    } 
}
