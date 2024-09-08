package com.healthcare.metamodel_service.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull(message = "Description is required")
    private String description;
}
