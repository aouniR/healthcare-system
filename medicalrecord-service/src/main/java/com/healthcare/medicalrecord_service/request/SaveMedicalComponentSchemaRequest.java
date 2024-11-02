package com.healthcare.medicalrecord_service.request;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaveMedicalComponentSchemaRequest {
    @NotNull(message = "MetaModelId is required")
        private UUID metamodelId;
}
