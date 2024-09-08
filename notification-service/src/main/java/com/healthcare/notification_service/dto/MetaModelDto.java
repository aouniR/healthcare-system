package com.healthcare.notification_service.dto;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MetaModelDto {
    private UUID id;
    private String description;
    private List<UUID> composantDossierIds;
    private UUID creatorId;
    private LocalDateTime creationTimestamp;
    private LocalDateTime updateTimestamp; 
}
