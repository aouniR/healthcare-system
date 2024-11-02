package com.healthcare.medicalrecord_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalProcedureDto {
    private UUID id;
    private UUID patientId;
    private UUID medicalAgentId;
    private Map<String, Object> medicalProcedureData;
    private LocalDateTime setCreationTimestamp;
    private LocalDateTime updateTimestamp;
}
