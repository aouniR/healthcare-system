package com.healthcare.medicalrecord_service.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "MedicalRecordSchema")
public class MedicalRecordSchema {

    @Id
    private UUID id = UUID.randomUUID();
    
    private static final @Getter MedicalRecordSchema  instance = new MedicalRecordSchema();
    private UUID metamodelId;
    private Map<String, Object> fields;

    private MedicalRecordSchema() {}

    public void initialize(UUID newMetamodelId, JsonNode newFields) {
        setMetamodelId(newMetamodelId);
        setFields(newFields);
    }

    private void setMetamodelId(UUID newMetamodelId) {
        if (this.metamodelId == null) { 
            this.metamodelId = newMetamodelId;
        } else {
            throw new IllegalStateException("metamodelId has already been set and cannot be changed.");
        }
    }

    private void setFields(JsonNode newFields) {
        if (this.fields == null) { 
            try {
                ObjectMapper mapper = new ObjectMapper();
                this.fields = mapper.convertValue(newFields, Map.class);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Failed to convert JsonNode to Map.", e);
            }
        } else {
            throw new IllegalStateException("fields has already been set and cannot be changed.");
        }
    }

}
