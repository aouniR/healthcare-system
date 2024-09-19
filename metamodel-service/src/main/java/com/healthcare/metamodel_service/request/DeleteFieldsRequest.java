package com.healthcare.metamodel_service.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteFieldsRequest {
    @NotNull(message = "fieldKey is required")
    private String fieldKey;
}
