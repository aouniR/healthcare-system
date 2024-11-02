package com.healthcare.medicalrecord_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalComponentSchemaDto {
    private UUID id;
    private UUID metamodelId;
    private Map<String, Object> fields;
}
