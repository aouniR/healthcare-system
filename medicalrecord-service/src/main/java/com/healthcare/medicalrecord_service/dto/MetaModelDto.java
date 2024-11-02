package com.healthcare.medicalrecord_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.healthcare.medicalrecord_service.entity.TypeMetaModel;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaModelDto {
    private UUID id;
    private String description;
    private JsonNode fields;
    private TypeMetaModel type;
    private UUID creatorId;
    private LocalDateTime creationTimestamp;
    private LocalDateTime updateTimestamp;
}


