package com.healthcare.metamodel_service.entity;

import java.util.UUID;

import jakarta.persistence.*;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Metamodel getMetamodel() {
        return metamodel;
    }

    public void setMetamodel(Metamodel metamodel) {
        this.metamodel = metamodel;
    }
}


