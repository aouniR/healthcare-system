package com.healthcare.metamodel_service.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.healthcare.metamodel_service.entity.TypeMetaModel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull(message = "Description is required")
    private String description;
    @NotNull(message = "TypeMetaModel is required")
    private TypeMetaModel type;
    @NotNull(message = "fields is required")
    private JsonNode fields;
}
