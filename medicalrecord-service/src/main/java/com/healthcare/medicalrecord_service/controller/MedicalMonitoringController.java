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
public class MedicalMonitoringController {
    
    private final MedicalRecordAdminServiceImpl medicalRecordAdminServiceImpl;
    
    @Autowired
    public MedicalMonitoringController(@Lazy MedicalRecordAdminServiceImpl medicalRecordAdminServiceImpl) {
        this.medicalRecordAdminServiceImpl = medicalRecordAdminServiceImpl;
    }

    @GetMapping("/getAllMedicalMonitoring")
    public ResponseEntity<List<MedicalMonitoring>> getAllMedicalMonitoring() {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.getAllMedicalMonitoring());
    }

    @GetMapping("/getNumberMedicalMonitoringByMonth/{year}")
    public List<Integer> getNumberMedicalMonitoringByMonth(@PathVariable int year) {
        return medicalRecordAdminServiceImpl.getNumberMedicalMonitoringByMonth(year);
    }

    @GetMapping("/getMedicalMonitoringSchema")

    public ResponseEntity<Optional<MedicalMonitoringSchema>> getMedicalMonitoringSchema() {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.getMedicalMonitoringSchema());
    }

    @GetMapping("/getMedicalMonitoringById/{id}")
    public ResponseEntity<MedicalMonitoring> getMedicalMonitoringById(@PathVariable UUID id) {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.getMedicalMonitoringById(id));
    }


    @PostMapping("/createMedicalMonitoring")
    public ResponseEntity<MedicalMonitoring> createMedicalMonitoring( @Valid @RequestBody CreateMedicalComponentRequest request,  Authentication authentication) {
        UUID agentId = UUID.fromString((String) authentication.getPrincipal());
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.createMedicalMonitoring(request, agentId));
    }

    @PutMapping("/addFieldsToMedicalMonitoringById/{id}")

    public ResponseEntity<MedicalMonitoring> addFieldsToMedicalMonitoringById(@PathVariable UUID id, @Valid @RequestBody FillCoupleOfMedicalDataRequest request) {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.fillCoupleOfMedicalMonitoring(request, id));
    }

    @PutMapping("/updateFieldOfMedicalMonitoring/{id}")

    public ResponseEntity<MedicalMonitoring> updateFieldOfMedicalMonitoring(@PathVariable UUID id, @Valid @RequestBody UpdateMedicalDataRequest request) {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.updateFieldOfMedicalMonitoring(request, id));
    }

    @PostMapping("/saveMedicalMonitoringSchema")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> saveMedicalMonitoringSchema( @Valid @RequestBody SaveMedicalComponentSchemaRequest request) {
        medicalRecordAdminServiceImpl.saveMedicalMonitoringSchema(request);
        return ResponseEntity.ok("Process started to create a new MedicalMonitoringSchema .." );
    }

}
