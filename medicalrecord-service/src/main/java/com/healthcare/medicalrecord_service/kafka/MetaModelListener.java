package com.healthcare.medicalrecord_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import com.healthcare.medicalrecord_service.entity.TypeMetaModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.medicalrecord_service.dto.MetaModelDto;
import com.healthcare.medicalrecord_service.dto.RequestDto;
import com.healthcare.medicalrecord_service.service.MedicalRecordAdminServiceImpl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MetaModelListener {

    @Value("${spring.kafka.topic.metaModelProducer-events}")
    private String metaModelProducerEventsTopic;

    private static final Logger log = LoggerFactory.getLogger(MetaModelListener.class);
    private ObjectMapper objectMapper;
    private final MedicalRecordAdminServiceImpl  medicalRecordAdminServiceImpl;
    private final KafkaTemplate<String, RequestDto> metaModelFieldsRequest;

    @Autowired
    public MetaModelListener(@Lazy MedicalRecordAdminServiceImpl medicalRecordAdminServiceImpl,
                            KafkaTemplate<String, RequestDto> metaModelFieldsRequest,
                            ObjectMapper objectMapper
                               ) {
        this.objectMapper = objectMapper;
        this.metaModelFieldsRequest = metaModelFieldsRequest;
        this.medicalRecordAdminServiceImpl = medicalRecordAdminServiceImpl;
    }

    @KafkaListener(topics = "${spring.kafka.topic.metaModelListener-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void waitForMetamodelResponse(@Header(KafkaHeaders.RECEIVED_KEY) String key, @Payload String message) {
        log.info("Received message with key: {} and value: {}", key, message);
        if ("METAMODEL".equals(key)) {
            try {
                MetaModelDto metamodel = objectMapper.readValue(message, MetaModelDto.class);
                medicalRecordAdminServiceImpl.completeResponse(metamodel.getId(), metamodel);
            } catch (Exception e) {
                throw new RuntimeException("Error mapping data", e);
            }
        }
    }
    

    //Schema Request to fill the fields from Metamodel-service
    public void sendMetamodelFieldsRequestForSchema(UUID metamodelId, TypeMetaModel typeMetaModel) {
        RequestDto fieldsRequest = new RequestDto(metamodelId, typeMetaModel);
        metaModelFieldsRequest.send(metaModelProducerEventsTopic, "METAMODEL-FIELDS-REQUEST", fieldsRequest);
    }
}