package com.healthcare.medicalrecord_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.healthcare.medicalrecord_service.entity.*;
import com.healthcare.medicalrecord_service.request.*;


public interface MedicalRecordAdminService {
    List<MedicalRecord> getAllMedicalRecord();
    List<Integer> getNumberMedicalRecordsByMonth(int year);
    List<MedicalProcedure> getAllMedicalProcedure();
    List<Integer> getNumberMedicalProcedureByMonth(int year);
    List<MedicalMonitoring> getAllMedicalMonitoring();
    List<Integer> getNumberMedicalMonitoringByMonth(int year);
    MedicalRecord getMedicalRecordById(UUID id);
    MedicalProcedure getMedicalProcedureById(UUID id);
    List<MedicalProcedure> getMedicalProcedureByPatientId(UUID id);
    void deleteMedicalProcedureById(UUID id);
    MedicalMonitoring getMedicalMonitoringById(UUID id);
    List<MedicalMonitoring> getMedicalMonitoringByPatientId(UUID id);
    void deleteMedicalMonitoringById(UUID id); 
    MedicalRecord createMedicalRecord(UUID agentId);
    MedicalProcedure createMedicalProcedure(CreateMedicalComponentRequest request, UUID agentId);
    MedicalMonitoring createMedicalMonitoring(CreateMedicalComponentRequest request, UUID agentId);
    MedicalRecord fillCoupleOfMedicalRecord(FillCoupleOfMedicalDataRequest request, UUID dataId);
    MedicalProcedure fillCoupleOfMedicalProcedure(FillCoupleOfMedicalDataRequest request, UUID dataId);
    MedicalMonitoring fillCoupleOfMedicalMonitoring(FillCoupleOfMedicalDataRequest request, UUID dataId);

    MedicalRecord updateFieldOfMedicalRecord(UpdateMedicalDataRequest request, UUID dataId);
    MedicalProcedure updateFieldOfMedicalProcedure(UpdateMedicalDataRequest request, UUID dataId);
    MedicalMonitoring updateFieldOfMedicalMonitoring(UpdateMedicalDataRequest request, UUID dataId);

    Optional<MedicalRecordSchema>  getMedicalRecordSchema();
    Optional<MedicalProcedureSchema>  getMedicalProcedureSchema();
    Optional<MedicalMonitoringSchema> getMedicalMonitoringSchema();
    CompletableFuture<MedicalRecordSchema> saveMedicalRecordSchema(SaveMedicalComponentSchemaRequest request);
    CompletableFuture<MedicalProcedureSchema> saveMedicalProcedureSchema(SaveMedicalComponentSchemaRequest request);
    CompletableFuture<MedicalMonitoringSchema> saveMedicalMonitoringSchema(SaveMedicalComponentSchemaRequest request);
}

