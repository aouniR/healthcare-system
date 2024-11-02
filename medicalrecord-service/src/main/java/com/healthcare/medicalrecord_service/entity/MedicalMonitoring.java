package com.healthcare.medicalrecord_service.entity;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Document(collection = "medicalMonitoring")
public class MedicalMonitoring {

    @Id
    private UUID id = UUID.randomUUID();

    private UUID patientId;
    
    private UUID administrationAgentId;

    private Map<String, Object> medicalMonitoringData= new HashMap<>();

    @CreatedDate
    private LocalDateTime creationTimestamp;

    @LastModifiedDate
    private LocalDateTime updateTimestamp;

    public void initializePatientDataFields(Map<String, Object> fields){
        this.creationTimestamp = LocalDateTime.now();
        
        if (fields != null && !fields.isEmpty()) {
            fields.keySet().forEach(fieldName -> {
                this.medicalMonitoringData.put(fieldName, null); 
            });
        } else {
            throw new IllegalStateException("Fields in the schema are not properly initialized.");
        }
    }
    

    public void setMedicalMonitoringData(String fieldName, JsonNode value) {
        validateFieldName(fieldName);
        this.medicalMonitoringData.put(fieldName,value);
    }

    public void setMultipleMedicalMonitoringData(JsonNode fieldValues) {
        if (fieldValues.isObject()) {
            fieldValues.fields().forEachRemaining(entry -> {
                String fieldName = entry.getKey();
                JsonNode value = entry.getValue();
                validateFieldName(fieldName);
                medicalMonitoringData.put(fieldName, value);
            });
        } else {
            throw new IllegalArgumentException("Expected a JSON object");
        }
    }
    

    public Object getMedicalMonitoringData(String fieldName) {
        return medicalMonitoringData.get(fieldName);
    }


    public void setPatientId(UUID patientId) {
        if (this.patientId == null) {
            this.patientId = patientId;
        } else {
            throw new IllegalStateException("patientId has already been set and cannot be changed.");
        }
    }

    public void setMedicalAgentId(UUID newAdministrationAgentId) {
        if (this.administrationAgentId == null) {
            this.administrationAgentId = newAdministrationAgentId;
        } else {
            throw new IllegalStateException("administrationAgentId has already been set and cannot be changed.");
        }
    }


    private void validateFieldName(String fieldName) {
        if (!medicalMonitoringData.containsKey(fieldName)) {
            throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
