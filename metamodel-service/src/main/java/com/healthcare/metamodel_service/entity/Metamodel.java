package com.healthcare.metamodel_service.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class Metamodel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="metaModelID")
    private UUID id;
    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "metamodel", cascade = CascadeType.ALL)
    @Column(name="composants")
    private List<ComposantDossier> composants;

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

    public List<ComposantDossier> getComposants() {
        return composants;
    }

    public void setComposants(List<ComposantDossier> composants) {
        this.composants = composants;
    }
}
