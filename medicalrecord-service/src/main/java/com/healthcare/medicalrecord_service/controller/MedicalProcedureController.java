package com.healthcare.medicalrecord_service.controller;

import com.healthcare.medicalrecord_service.entity.*;
import com.healthcare.medicalrecord_service.request.CreateMedicalComponentRequest;
import com.healthcare.medicalrecord_service.request.FillCoupleOfMedicalDataRequest;
import com.healthcare.medicalrecord_service.request.SaveMedicalComponentSchemaRequest;
import com.healthcare.medicalrecord_service.request.UpdateMedicalDataRequest;
import com.healthcare.medicalrecord_service.service.MedicalRecordAdminServiceImpl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalrecords")
public class MedicalProcedureController {
    
    private final MedicalRecordAdminServiceImpl medicalRecordAdminServiceImpl;
    
    public MedicalProcedureController(@Lazy MedicalRecordAdminServiceImpl medicalRecordAdminServiceImpl) {
        this.medicalRecordAdminServiceImpl = medicalRecordAdminServiceImpl;
    }

    @GetMapping("/getAllMedicalProcedure")
    public ResponseEntity<List<MedicalProcedure>> getAllMedicalProcedure() {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.getAllMedicalProcedure());
    }

    @GetMapping("/getNumberMedicalProcedureByMonth/{year}")
    public List<Integer> getNumberMedicalProcedureByMonth(@PathVariable int year) {
        return medicalRecordAdminServiceImpl.getNumberMedicalProcedureByMonth(year);
    }

    @GetMapping("/getMedicalProcedureSchema")
    public ResponseEntity<Optional<MedicalProcedureSchema>> getMedicalProcedureSchema() {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.getMedicalProcedureSchema());
    }

    @GetMapping("/getMedicalProcedureById/{id}")
    public ResponseEntity<MedicalProcedure> getMedicalProcedureById(@PathVariable UUID id) {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.getMedicalProcedureById(id));
    }

    @GetMapping("/getMedicalProcedureByPatientId/{id}")
    public ResponseEntity<List<MedicalProcedure>> getMedicalProcedureByPatientId(@PathVariable UUID id) {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.getMedicalProcedureByPatientId(id));
    }
    @DeleteMapping("/deleteMedicalProcedureById/{id}")
    @PreAuthorize("hasRole('PROFESSIONNELDESANTE')")
    public ResponseEntity<Void> deleteMedicalProcedureById(@PathVariable UUID id) {
        medicalRecordAdminServiceImpl.deleteMedicalProcedureById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/createMedicalProcedure")
    @PreAuthorize("hasRole('PROFESSIONNELDESANTE')")
    public ResponseEntity<MedicalProcedure> createMedicalProcedure( @Valid @RequestBody CreateMedicalComponentRequest request,  Authentication authentication) {
        UUID agentId = UUID.fromString((String) authentication.getPrincipal());
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.createMedicalProcedure(request, agentId));
    }

    @PutMapping("/addFieldsToMedicalProcedureById/{id}")
    @PreAuthorize("hasRole('PROFESSIONNELDESANTE')")
    public ResponseEntity<MedicalProcedure> addFieldsToMedicalProcedureById(@PathVariable UUID id, @Valid @RequestBody FillCoupleOfMedicalDataRequest request) {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.fillCoupleOfMedicalProcedure(request, id));
    }

    @PutMapping("/updateFieldOfMedicalProcedure/{id}")
    public ResponseEntity<MedicalProcedure> updateFieldOfMedicalProcedure(@PathVariable UUID id, @Valid @RequestBody UpdateMedicalDataRequest request) {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.updateFieldOfMedicalProcedure(request, id));
    }

    @PostMapping("/saveMedicalProcedureSchema")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> saveMedicalProcedureSchema(@Valid @RequestBody SaveMedicalComponentSchemaRequest request) {
        medicalRecordAdminServiceImpl.saveMedicalProcedureSchema(request);
        return ResponseEntity.ok("Process started to create a new ProcedureSchema .." );
    }
}
