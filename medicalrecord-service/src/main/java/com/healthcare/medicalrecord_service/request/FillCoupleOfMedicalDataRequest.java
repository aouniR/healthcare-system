package com.healthcare.medicalrecord_service.request;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FillCoupleOfMedicalDataRequest{

    @NotNull(message = "MedicalData is required")
    private JsonNode medicalData;
}
