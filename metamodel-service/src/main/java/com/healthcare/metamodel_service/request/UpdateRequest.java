package com.healthcare.metamodel_service.request;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRequest {
    @NotNull(message = "fields is required")
    private JsonNode fields;
}
