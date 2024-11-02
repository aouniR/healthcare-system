package com.healthcare.medicalrecord_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.healthcare.medicalrecord_service.entity.MedicalRecord;
import com.healthcare.medicalrecord_service.entity.MedicalRecordSchema;
import com.healthcare.medicalrecord_service.dto.MedicalRecordDto;
import com.healthcare.medicalrecord_service.dto.MedicalComponentSchemaDto;

@Component
@RequiredArgsConstructor
public class MedicalRecordEventProducer{

    @Value("${spring.kafka.topic.medicalRecord-events}")
    private String medicalRecordEventsTopic;

    private final KafkaTemplate<String, MedicalRecordDto> medicalRecordKafkaTemplate;
    private final KafkaTemplate<String, MedicalComponentSchemaDto> medicalRecordSchemaKafkaTemplate;

    public void sendMedicalRecordCreatedEvent(MedicalRecord medicalRecord) {
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto();
        medicalRecordDto.setId(medicalRecord.getId());
        medicalRecordDto.setPatientId(medicalRecord.getPatientId());
        medicalRecordDto.setPatientData(medicalRecord.getPatientData());
        medicalRecordDto.setSetCreationTimestamp(medicalRecord.getCreationTimestamp());
        medicalRecordDto.setUpdateTimestamp(medicalRecord.getUpdateTimestamp());
        medicalRecordKafkaTemplate.send(medicalRecordEventsTopic, "MEDICALRECORD_CREATED", medicalRecordDto);
    }

    public void sendMedicalRecordSchemaCreatedEvent(MedicalRecordSchema medicalRecordSchema) {
        MedicalComponentSchemaDto medicalComponentSchemaDto = new MedicalComponentSchemaDto();
        medicalComponentSchemaDto.setId(medicalRecordSchema.getId());
        medicalComponentSchemaDto.setMetamodelId(medicalRecordSchema.getMetamodelId());
        medicalComponentSchemaDto.setFields(medicalRecordSchema.getFields());
        medicalRecordSchemaKafkaTemplate.send(medicalRecordEventsTopic, "MEDICALRECORDSCHEMA_CREATED", medicalComponentSchemaDto);
    }

    public void sendMedicalRecordDeletedEvent(MedicalRecord medicalRecord) {
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto();
        medicalRecordDto.setId(medicalRecord.getId());
        medicalRecordDto.setPatientId(medicalRecord.getPatientId());
        medicalRecordDto.setPatientData(medicalRecord.getPatientData());
        medicalRecordDto.setSetCreationTimestamp(medicalRecord.getCreationTimestamp());
        medicalRecordDto.setUpdateTimestamp(medicalRecord.getUpdateTimestamp());
        medicalRecordKafkaTemplate.send(medicalRecordEventsTopic, "MEDICALRECORD_DELETED", medicalRecordDto);
    }

    public void sendMedicalRecordSchemaDeletedEvent(MedicalRecordSchema medicalRecordSchema) {
        MedicalComponentSchemaDto medicalComponentSchemaDto = new MedicalComponentSchemaDto();
        medicalComponentSchemaDto.setId(medicalRecordSchema.getId());
        medicalComponentSchemaDto.setMetamodelId(medicalRecordSchema.getMetamodelId());
        medicalComponentSchemaDto.setFields(medicalRecordSchema.getFields());
        medicalRecordSchemaKafkaTemplate.send(medicalRecordEventsTopic, "MEDICALRECORDSCHEMA_DELETED", medicalComponentSchemaDto);
    }

    public void sendMedicalRecordUpdatedEvent(MedicalRecord medicalRecord) {
        MedicalRecordDto medicalRecordDto = new MedicalRecordDto();
        medicalRecordDto.setId(medicalRecord.getId());
        medicalRecordDto.setPatientId(medicalRecord.getPatientId());
        medicalRecordDto.setPatientData(medicalRecord.getPatientData());
        medicalRecordDto.setSetCreationTimestamp(medicalRecord.getCreationTimestamp());
        medicalRecordDto.setUpdateTimestamp(medicalRecord.getUpdateTimestamp());
        medicalRecordKafkaTemplate.send(medicalRecordEventsTopic, "MEDICALRECORD_UPDATED", medicalRecordDto);
    }
}