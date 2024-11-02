package com.healthcare.metamodel_service.entity;

import lombok.*;
import java.util.UUID;
import jakarta.persistence.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.healthcare.metamodel_service.converter.JsonNodeConverter;


@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity(name="metamodels")
@Getter
@Setter
public class MetaModel extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String description;

    @Convert(converter = JsonNodeConverter.class)
    private JsonNode fields;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeMetaModel type;

    @Column(name = "creator_id", nullable = false,updatable = false)
    private UUID creatorId;
}


    