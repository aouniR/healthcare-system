package com.healthcare.metamodel_service.controller;

import com.healthcare.metamodel_service.entity.Metamodel;
import com.healthcare.metamodel_service.service.MetamodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/metamodels")
public class MetamodelController {

    
    private final MetamodelService metamodelService;

    @Autowired
    public MetamodelController(MetamodelService metamodelService){
        this.metamodelService=metamodelService;
    }

    @GetMapping
    public List<Metamodel> getAllMetamodels() {
        return metamodelService.getAllMetamodels();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Metamodel> getMetamodelById(@PathVariable UUID id) {
        Metamodel metamodel = metamodelService.getMetamodelById(id);
        return metamodel != null ? ResponseEntity.ok(metamodel) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Metamodel createMetamodel(@RequestBody Metamodel metamodel) {
        return metamodelService.saveMetamodel(metamodel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMetamodel(@PathVariable UUID id) {
        metamodelService.deleteMetamodel(id);
        return ResponseEntity.noContent().build();
    }
}
