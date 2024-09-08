package com.healthcare.metamodel_service.entity;

import lombok.*;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

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
    private String description;

    @ElementCollection
    @CollectionTable(name = "metamodel_composants", joinColumns = @JoinColumn(name = "metamodel_id"))
    @Column(name = "composant_dossier_id")
    private List<UUID> composantDossierIds;

    @Column(name = "creator_id", nullable = false,updatable = false)
    private UUID creatorId;
}


    