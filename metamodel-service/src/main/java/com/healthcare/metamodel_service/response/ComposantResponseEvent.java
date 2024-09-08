package com.healthcare.metamodel_service.response;

import java.util.UUID;

import lombok.Data;

@Data
public class ComposantResponseEvent {
    private UUID metaModelId;  
    private UUID composantDossierId;
}
