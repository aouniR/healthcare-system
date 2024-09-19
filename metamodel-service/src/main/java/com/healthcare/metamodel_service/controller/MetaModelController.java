package com.healthcare.metamodel_service.controller;

import com.healthcare.metamodel_service.entity.MetaModel;
import com.healthcare.metamodel_service.entity.TypeMetaModel;
import com.healthcare.metamodel_service.request.RegisterRequest;
import com.healthcare.metamodel_service.request.AddFieldsRequest;
import com.healthcare.metamodel_service.request.DeleteFieldsRequest;
import com.healthcare.metamodel_service.service.MetaModelServiceImpl;
import org.springframework.security.core.Authentication;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<List<MetaModel>> getAllMetaModels() {
        return ResponseEntity.ok(metaModelServiceImpl.getAllMetaModels());
    }

    @GetMapping("/getMetaModelById/{id}")
    public ResponseEntity<MetaModel> getMetaModelById(@PathVariable UUID id) {
        return ResponseEntity.ok(metaModelServiceImpl.getMetaModelById(id));
    }

    @GetMapping("/getMetaModelByType/{type}")
    public ResponseEntity<Optional<List<MetaModel>>> getMetaModelByType(@PathVariable TypeMetaModel type) {
        return ResponseEntity.ok(metaModelServiceImpl.getMetaModelByType(type));
    }

    @PutMapping("/addFieldsToMetaModelById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MetaModel> addFieldsToMetaModelById(@PathVariable UUID id, @Valid @RequestBody AddFieldsRequest request) {
        return ResponseEntity.ok(metaModelServiceImpl.addFieldsToMetaModelById(id,request));
    }

    @DeleteMapping("/deleteFieldFromMetaModelById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MetaModel> deleteFieldFromMetaModelById(@PathVariable UUID id, @Valid @RequestBody DeleteFieldsRequest request) {
        return ResponseEntity.ok(metaModelServiceImpl.deleteFieldFromMetaModelById(id,request));
    }

    @DeleteMapping("/deleteMetaModelById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMetaModelById(@PathVariable UUID id) {
        metaModelServiceImpl.deleteMEtaModelById(id);
        return ResponseEntity.noContent().build();
    }
}
