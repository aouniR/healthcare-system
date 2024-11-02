package com.healthcare.medicalrecord_service.response;

import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class FieldsResponseEvent {
    private UUID metaModelId;  
    private JsonNode fields;
    private UUID creatorId;
}
