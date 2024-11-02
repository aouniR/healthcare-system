package com.healthcare.medicalrecord_service.request;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateMedicalDataRequest{

    @NotNull(message = "FieldName is required")
    private String fieldName;
    @NotNull(message = "FieldValue is required")
    private JsonNode value;
}
