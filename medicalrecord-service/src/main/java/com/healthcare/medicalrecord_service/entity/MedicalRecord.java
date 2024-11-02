package com.healthcare.medicalrecord_service.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Document(collection = "medicalRecords")
public class MedicalRecord {

    @Id
    private UUID id = UUID.randomUUID();
    
    private  UUID patientId;

    private  UUID agentId;

    private Map<String, Object> patientData= new HashMap<>();

    @CreatedDate
    private  LocalDateTime creationTimestamp;

    @LastModifiedDate
    private LocalDateTime updateTimestamp;

    public void initializePatientDataFields(Map<String, Object> fields){
        this.creationTimestamp = LocalDateTime.now();
        
        if (fields != null && !fields.isEmpty()) {
            fields.keySet().forEach(fieldName -> {
                this.patientData.put(fieldName, null); 
            });
        } else {
            throw new IllegalStateException("Fields in the schema are not properly initialized.");
        }
    }

    public void setPatientData(String fieldName, JsonNode value) {
        validateFieldName(fieldName);
        this.patientData.put(fieldName, value);
    }

    public void setCoupleOfPatientData(JsonNode fieldValues) {
        if (fieldValues.isObject()) {
            fieldValues.fields().forEachRemaining(entry -> {
                String fieldName = entry.getKey();
                JsonNode value = entry.getValue();
                validateFieldName(fieldName);
                patientData.put(fieldName, value);
            });
        } else {
            throw new IllegalArgumentException("Expected a JSON object");
        }
    }

    public Object getPatientData(String fieldName) {
        return this.patientData.get(fieldName);
    }

    public void setPatientId(UUID patientId){
        if(this.patientId == null){
            this.patientId = patientId;
        }else{
            throw new IllegalStateException("patientId has already been set and cannot be changed.");
        }
    }

    public void setMedicalAgentId(UUID agentId) {
        if (this.agentId == null) {
            this.agentId = agentId;
        } else {
            throw new IllegalStateException("agentId has already been set and cannot be changed.");
        }
    }

    private void validateFieldName(String fieldName) {
        if (!this.patientData.containsKey(fieldName)) {
            throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }

}
