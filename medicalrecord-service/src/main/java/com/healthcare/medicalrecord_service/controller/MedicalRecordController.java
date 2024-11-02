package com.healthcare.medicalrecord_service.controller;

import com.healthcare.medicalrecord_service.entity.*;
import com.healthcare.medicalrecord_service.request.CreateMedicalComponentRequest;
import com.healthcare.medicalrecord_service.request.FillCoupleOfMedicalDataRequest;
import com.healthcare.medicalrecord_service.request.SaveMedicalComponentSchemaRequest;
import com.healthcare.medicalrecord_service.request.UpdateMedicalDataRequest;
import com.healthcare.medicalrecord_service.service.MedicalRecordAdminServiceImpl;
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
public class MedicalRecordController {
    
    private final MedicalRecordAdminServiceImpl medicalRecordAdminServiceImpl;
    
    @Autowired
    public MedicalRecordController(@Lazy MedicalRecordAdminServiceImpl medicalRecordAdminServiceImpl) {
        this.medicalRecordAdminServiceImpl = medicalRecordAdminServiceImpl;
    }

    @GetMapping("/getAllMedicalRecord")
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecord() {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.getAllMedicalRecord());
    }

    @GetMapping("/getNumberMedicalRecordByMonth/{year}")
    public List<Integer> getNumberMedicalRecordByMonth(@PathVariable int year) {
        return medicalRecordAdminServiceImpl.getNumberMedicalRecordsByMonth(year);
    }

    @GetMapping("/getMedicalRecordSchema")

    public ResponseEntity<Optional<MedicalRecordSchema>> getMedicalRecordSchema() {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.getMedicalRecordSchema());
    }

    @GetMapping("/getMedicalRecordById/{id}")
    public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable UUID id) {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.getMedicalRecordById(id));
    }

    @PostMapping("/createMedicalRecord")

    public ResponseEntity<MedicalRecord> createMedicalRecord( @Valid @RequestBody CreateMedicalComponentRequest request,  Authentication authentication) {
        UUID agentId = UUID.fromString((String) authentication.getPrincipal());
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.createMedicalRecord(request, agentId));
    }

    @PutMapping("/addFieldsToMedicalRecordById/{id}")

    public ResponseEntity<MedicalRecord> addFieldsToMedicalRecordById(@PathVariable UUID id, @Valid @RequestBody FillCoupleOfMedicalDataRequest request) {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.fillCoupleOfMedicalRecord(request, id));
    }

    @PutMapping("/updateFieldOfMedicalRecord/{id}")

    public ResponseEntity<MedicalRecord> updateFieldOfMedicalRecord(@PathVariable UUID id, @Valid @RequestBody UpdateMedicalDataRequest request) {
        return ResponseEntity.ok(medicalRecordAdminServiceImpl.updateFieldOfMedicalRecord(request, id));
    }

    @PostMapping("/saveMedicalRecordSchema")

    public ResponseEntity<String> saveMedicalRecordSchema( @Valid @RequestBody SaveMedicalComponentSchemaRequest request) {
        medicalRecordAdminServiceImpl.saveMedicalRecordSchema(request);
        return ResponseEntity.ok("Process started to create a new MedicalRecordSchema .." );
    }

}
