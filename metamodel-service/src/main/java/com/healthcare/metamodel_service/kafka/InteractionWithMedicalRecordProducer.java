package com.healthcare.metamodel_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.healthcare.metamodel_service.dto.MetaModelDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InteractionWithMedicalRecordProducer {
    
    @Value("${spring.kafka.topic.composant-response-events}")
    private String composantResponseEventsTopic;
    private static final Logger log = LoggerFactory.getLogger(InteractionWithMedicalRecordProducer.class);
    private final KafkaTemplate<String, MetaModelDto> medicalMecordResponseKafkaTemplate;
    
    public void sendMetamodelResponse(MetaModelDto metamodel) {
            medicalMecordResponseKafkaTemplate.send(composantResponseEventsTopic, "METAMODEL", metamodel);
            log.info("Sent metamodel response : {}", metamodel);
    }
}
