package com.healthcare.metamodel_service.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.healthcare.metamodel_service.entity.TypeMetaModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    
    private UUID metamodelId;
    private TypeMetaModel typeMetaModel;
}
