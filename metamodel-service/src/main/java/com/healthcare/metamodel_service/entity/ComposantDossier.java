package com.healthcare.metamodel_service.entity;

import lombok.*;
import java.util.UUID;
import jakarta.persistence.*;

@Data

@Entity
public class ComposantDossier {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="composantDossierID")
    private UUID id;
    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "metaModelID")
    @Column(name="metaModel")
    private Metamodel metamodel;

}


