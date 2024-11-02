package com.healthcare.medicalrecord_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.healthcare.medicalrecord_service.entity.MedicalProcedure;
import com.healthcare.medicalrecord_service.entity.MedicalProcedureSchema;
import com.healthcare.medicalrecord_service.dto.MedicalProcedureDto;
import com.healthcare.medicalrecord_service.dto.MedicalComponentSchemaDto;

@Component
@RequiredArgsConstructor
public class MedicalProcedureEventProducer{

    @Value("${spring.kafka.topic.medicalProcedure-events}")
    private String medicalProcedureEventsTopic;

    private final KafkaTemplate<String, MedicalProcedureDto> medicalProcedureKafkaTemplate;
    private final KafkaTemplate<String, MedicalComponentSchemaDto> medicalProcedureSchemaKafkaTemplate;

    public void sendMedicalProcedureCreatedEvent(MedicalProcedure medicalProcedure) {
        MedicalProcedureDto medicalProcedureDto = new MedicalProcedureDto();
        medicalProcedureDto.setId(medicalProcedure.getId());
        medicalProcedureDto.setPatientId(medicalProcedure.getPatientId());
        medicalProcedureDto.setMedicalProcedureData(medicalProcedure.getMedicalProcedureData());
        medicalProcedureDto.setSetCreationTimestamp(medicalProcedure.getCreationTimestamp());
        medicalProcedureDto.setUpdateTimestamp(medicalProcedure.getUpdateTimestamp());
        medicalProcedureKafkaTemplate.send(medicalProcedureEventsTopic, "MEDICALPROCEDURE_CREATED", medicalProcedureDto);
    }

    public void sendMedicalProcedureSchemaCreatedEvent(MedicalProcedureSchema medicalProcedureSchema) {
        MedicalComponentSchemaDto medicalComponentSchemaDto = new MedicalComponentSchemaDto();
        medicalComponentSchemaDto.setId(medicalProcedureSchema.getId());
        medicalComponentSchemaDto.setMetamodelId(medicalProcedureSchema.getMetamodelId());
        medicalComponentSchemaDto.setFields(medicalProcedureSchema.getFields());
        medicalProcedureSchemaKafkaTemplate.send(medicalProcedureEventsTopic, "MEDICALPROCEDURESCHEMA_CREATED", medicalComponentSchemaDto);
    }

    public void sendMedicalProcedureDeletedEvent(MedicalProcedure medicalProcedure) {
        MedicalProcedureDto medicalProcedureDto = new MedicalProcedureDto();
        medicalProcedureDto.setId(medicalProcedure.getId());
        medicalProcedureDto.setPatientId(medicalProcedure.getPatientId());
        medicalProcedureDto.setMedicalProcedureData(medicalProcedure.getMedicalProcedureData());
        medicalProcedureDto.setSetCreationTimestamp(medicalProcedure.getCreationTimestamp());
        medicalProcedureDto.setUpdateTimestamp(medicalProcedure.getUpdateTimestamp());
        medicalProcedureKafkaTemplate.send(medicalProcedureEventsTopic, "MEDICALPROCEDURE_DELETED", medicalProcedureDto);
    }

    public void sendMedicalProcedureSchemaDeletedEvent(MedicalProcedureSchema medicalProcedureSchema) {
        MedicalComponentSchemaDto medicalComponentSchemaDto = new MedicalComponentSchemaDto();
        medicalComponentSchemaDto.setId(medicalProcedureSchema.getId());
        medicalComponentSchemaDto.setMetamodelId(medicalProcedureSchema.getMetamodelId());
        medicalComponentSchemaDto.setFields(medicalProcedureSchema.getFields());
        medicalProcedureSchemaKafkaTemplate.send(medicalProcedureEventsTopic, "MEDICALPROCEDURESCHEMA_DELETED", medicalComponentSchemaDto);
    }

    public void sendMedicalProcedureUpdatedEvent(MedicalProcedure medicalProcedure) {
        MedicalProcedureDto medicalProcedureDto = new MedicalProcedureDto();
        medicalProcedureDto.setId(medicalProcedure.getId());
        medicalProcedureDto.setPatientId(medicalProcedure.getPatientId());
        medicalProcedureDto.setMedicalProcedureData(medicalProcedure.getMedicalProcedureData());
        medicalProcedureDto.setSetCreationTimestamp(medicalProcedure.getCreationTimestamp());
        medicalProcedureDto.setUpdateTimestamp(medicalProcedure.getUpdateTimestamp());
        medicalProcedureKafkaTemplate.send(medicalProcedureEventsTopic, "MEDICALPROCEDURE_UPDATED", medicalProcedureDto);
    }
}