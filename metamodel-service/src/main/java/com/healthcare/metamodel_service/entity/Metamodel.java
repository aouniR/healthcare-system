package com.healthcare.metamodel_service.entity;

import java.util.List;
import java.util.UUID;
import lombok.*;
import jakarta.persistence.*;

@Data
@Table
@Builder
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

}
