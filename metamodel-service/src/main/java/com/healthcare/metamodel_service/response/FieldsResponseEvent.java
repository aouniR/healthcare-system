package com.healthcare.metamodel_service.response;

import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.healthcare.metamodel_service.entity.TypeMetaModel;

import lombok.Data;

@Data
public class FieldsResponseEvent {
    private UUID metaModelId;  
    private JsonNode fields;
    private TypeMetaModel type;
    private UUID creatorId;
}
