package com.healthcare.metamodel_service.controller;

import com.healthcare.metamodel_service.entity.MetaModel;
import com.healthcare.metamodel_service.request.RegisterRequest;
import com.healthcare.metamodel_service.service.MetaModelServiceImpl;
import org.springframework.security.core.Authentication;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metamodels")
@RequiredArgsConstructor
public class MetaModelController {

    private final MetaModelServiceImpl metaModelServiceImpl;


    @PostMapping("/createMetaModel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MetaModel> createMetaModel( @Valid @RequestBody RegisterRequest request,  Authentication authentication) {
        UUID userId = UUID.fromString((String) authentication.getPrincipal());
        return ResponseEntity.ok(metaModelServiceImpl.saveMetamodel(request, userId));
    }

    @GetMapping("/getAllMetaModels")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MetaModel>> getAllMetaModels() {
        return ResponseEntity.ok(metaModelServiceImpl.getAllMetaModels());
    }

    @GetMapping("/getMetaModelById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MetaModel> getMetaModelById(@PathVariable UUID id) {
        return ResponseEntity.ok(metaModelServiceImpl.getMetaModelById(id));
    }

    @PutMapping("/updateMetaModelById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MetaModel> updateMetaModelById(@PathVariable UUID id, @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(metaModelServiceImpl.updateMetaModelById(id,request));
    }

    @DeleteMapping("/deleteMetaModelById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMetaModelById(@PathVariable UUID id) {
        metaModelServiceImpl.deleteMEtaModelById(id);
        return ResponseEntity.noContent().build();
    }
}
