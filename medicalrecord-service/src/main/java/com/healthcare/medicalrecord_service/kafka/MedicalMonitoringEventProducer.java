package com.healthcare.medicalrecord_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.healthcare.medicalrecord_service.entity.MedicalMonitoring;
import com.healthcare.medicalrecord_service.entity.MedicalMonitoringSchema;
import com.healthcare.medicalrecord_service.dto.MedicalMonitoringDto;
import com.healthcare.medicalrecord_service.dto.MedicalComponentSchemaDto;

@Component
@RequiredArgsConstructor
public class MedicalMonitoringEventProducer{

    @Value("${spring.kafka.topic.medicalMonitoring-events}")
    private String medicalMonitoringEventsTopic;

    private final KafkaTemplate<String, MedicalMonitoringDto> medicalMonitoringKafkaTemplate;
    private final KafkaTemplate<String, MedicalComponentSchemaDto> medicalMonitoringSchemaKafkaTemplate;

    public void sendMedicalMonitoringCreatedEvent(MedicalMonitoring medicalMonitoring) {
        MedicalMonitoringDto medicalMonitoringDto = new MedicalMonitoringDto();
        medicalMonitoringDto.setId(medicalMonitoring.getId());
        medicalMonitoringDto.setPatientId(medicalMonitoring.getPatientId());
        medicalMonitoringDto.setMedicalMonitoringData(medicalMonitoring.getMedicalMonitoringData());
        medicalMonitoringDto.setSetCreationTimestamp(medicalMonitoring.getCreationTimestamp());
        medicalMonitoringDto.setUpdateTimestamp(medicalMonitoring.getUpdateTimestamp());
        medicalMonitoringKafkaTemplate.send(medicalMonitoringEventsTopic, "MEDICALMONITORING_CREATED", medicalMonitoringDto);
    }

    public void sendMedicalMonitoringSchemaCreatedEvent(MedicalComponentSchemaDto medicalMonitoringSchema) {
        MedicalComponentSchemaDto medicalComponentSchemaDto = new MedicalComponentSchemaDto();
        medicalComponentSchemaDto.setId(medicalMonitoringSchema.getId());
        medicalComponentSchemaDto.setMetamodelId(medicalMonitoringSchema.getMetamodelId());
        medicalComponentSchemaDto.setFields(medicalMonitoringSchema.getFields());
        medicalMonitoringSchemaKafkaTemplate.send(medicalMonitoringEventsTopic, "MEDICALMONITORINGSCHEMA_CREATED", medicalComponentSchemaDto);
    }

    public void sendMedicalMonitoringDeletedEvent(MedicalMonitoring medicalMonitoring) {
        MedicalMonitoringDto medicalMonitoringDto = new MedicalMonitoringDto();
        medicalMonitoringDto.setId(medicalMonitoring.getId());
        medicalMonitoringDto.setPatientId(medicalMonitoring.getPatientId());
        medicalMonitoringDto.setMedicalMonitoringData(medicalMonitoring.getMedicalMonitoringData());
        medicalMonitoringDto.setSetCreationTimestamp(medicalMonitoring.getCreationTimestamp());
        medicalMonitoringDto.setUpdateTimestamp(medicalMonitoring.getUpdateTimestamp());
        medicalMonitoringKafkaTemplate.send(medicalMonitoringEventsTopic, "MEDICALMONITORING_DELETED", medicalMonitoringDto);
    }

    public void sendMedicalMonitoringSchemaDeletedEvent(MedicalMonitoringSchema medicalMonitoringSchema) {
        MedicalComponentSchemaDto medicalComponentSchemaDto = new MedicalComponentSchemaDto();
        medicalComponentSchemaDto.setId(medicalMonitoringSchema.getId());
        medicalComponentSchemaDto.setMetamodelId(medicalMonitoringSchema.getMetamodelId());
        medicalComponentSchemaDto.setFields(medicalMonitoringSchema.getFields());
        medicalMonitoringSchemaKafkaTemplate.send(medicalMonitoringEventsTopic, "MEDICALMONITORINGSCHEMA_DELETED", medicalComponentSchemaDto);
    }

    public void sendMedicalMonitoringUpdatedEvent(MedicalMonitoring medicalMonitoring) {
        MedicalMonitoringDto medicalMonitoringDto = new MedicalMonitoringDto();
        medicalMonitoringDto.setId(medicalMonitoring.getId());
        medicalMonitoringDto.setPatientId(medicalMonitoring.getPatientId());
        medicalMonitoringDto.setMedicalMonitoringData(medicalMonitoring.getMedicalMonitoringData());
        medicalMonitoringDto.setSetCreationTimestamp(medicalMonitoring.getCreationTimestamp());
        medicalMonitoringDto.setUpdateTimestamp(medicalMonitoring.getUpdateTimestamp());
        medicalMonitoringKafkaTemplate.send(medicalMonitoringEventsTopic, "MEDICALMONITORING_UPDATED", medicalMonitoringDto);
    }
}