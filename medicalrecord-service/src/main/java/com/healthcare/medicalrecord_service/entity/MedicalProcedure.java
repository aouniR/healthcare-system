package com.healthcare.medicalrecord_service.entity;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "medicalProcedures")
public class MedicalProcedure {

    @Id
    private UUID id = UUID.randomUUID();

    private UUID patientId;

    private UUID medicalAgentId;

    private Map<String, Object> medicalProcedureData= new HashMap<>();

    @CreatedDate
    private LocalDateTime creationTimestamp;

    @LastModifiedDate
    private LocalDateTime updateTimestamp;


    public void initializePatientDataFields(Map<String, Object> fields){
        this.creationTimestamp = LocalDateTime.now();
        
        if (fields != null && !fields.isEmpty()) {
            fields.keySet().forEach(fieldName -> {
                this.medicalProcedureData.put(fieldName, null); 
            });
        } else {
            throw new IllegalStateException("Fields in the schema are not properly initialized.");
        }
    }
    

    public void setMedicalProcedureData(String fieldName, JsonNode value) {
        validateFieldName(fieldName);
        this.medicalProcedureData.put(fieldName,value);
    }

    public void setMultipleMedicalProcedureData(JsonNode fieldValues) {
        if (fieldValues.isObject()) {
            fieldValues.fields().forEachRemaining(entry -> {
                String fieldName = entry.getKey();
                JsonNode value = entry.getValue();
                validateFieldName(fieldName);
                if (value.isNull()) {
                    medicalProcedureData.put(fieldName, null);
                } else if (value.isTextual()) {
                    medicalProcedureData.put(fieldName, value.asText());
                } else if (value.isNumber()) {
                    medicalProcedureData.put(fieldName, value.numberValue());
                } else if (value.isBoolean()) {
                    medicalProcedureData.put(fieldName, value.asBoolean());
                } else {
                    throw new IllegalArgumentException("Unsupported JSON type for field: " + fieldName);
                }
            });
        } else {
            throw new IllegalArgumentException("Expected a JSON object");
        }
    }
    
    
    public Object getMedicalProcedureData(String fieldName) {
        return this.medicalProcedureData.get(fieldName);
    }


    public void setPatientId(UUID newPatientId) {
        if (this.patientId == null) {
            this.patientId = newPatientId;
        } else {
            throw new IllegalStateException("patientId has already been set and cannot be changed.");
        }
    }

    public void setMedicalAgentId(UUID newMedicalAgentId) {
        if (this.medicalAgentId == null) {
            this.medicalAgentId = newMedicalAgentId;
        } else {
            throw new IllegalStateException("medicalAgentId has already been set and cannot be changed.");
        }
    }

    private void validateFieldName(String fieldName) {
        if (!this.medicalProcedureData.containsKey(fieldName)) {
            throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
