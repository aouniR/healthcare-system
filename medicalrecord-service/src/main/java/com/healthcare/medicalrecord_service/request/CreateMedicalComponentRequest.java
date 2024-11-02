package com.healthcare.medicalrecord_service.request;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMedicalComponentRequest{

    @NotNull(message = "PatientId is required")
    private UUID patientId;
}
