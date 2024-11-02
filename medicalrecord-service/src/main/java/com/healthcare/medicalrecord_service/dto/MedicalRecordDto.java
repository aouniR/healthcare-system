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
public class MedicalRecordDto {
    private UUID id;
    private UUID patientId;
    private Map<String, Object> patientData;
    private LocalDateTime setCreationTimestamp;
    private LocalDateTime updateTimestamp;
}
