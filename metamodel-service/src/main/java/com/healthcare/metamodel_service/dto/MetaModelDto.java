package com.healthcare.metamodel_service.dto;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetaModelDto {
    private UUID id;
    private String description;
    private UUID creatorId;
    private List<UUID> composantsIds;
}


