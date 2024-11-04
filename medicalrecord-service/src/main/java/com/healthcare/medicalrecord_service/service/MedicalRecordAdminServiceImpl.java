package com.healthcare.medicalrecord_service.service;


import com.healthcare.medicalrecord_service.dto.MetaModelDto;
import com.healthcare.medicalrecord_service.entity.*;
import com.healthcare.medicalrecord_service.kafka.*;
import com.healthcare.medicalrecord_service.repository.*;
import com.healthcare.medicalrecord_service.request.*;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicalRecordAdminServiceImpl implements MedicalRecordAdminService {
    
    private final MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private final MedicalProcedureRepository medicalProcedureRepository;
    private final MedicalMonitoringRepository medicalMonitoringRepository;
    private final MedicalRecordSchemaRepository medicalRecordSchemaRepository;
    private final MedicalProcedureSchemaRepository medicalProcedureSchemaRepository;
    private final MedicalMonitoringSchemaRepository medicalMonitoringSchemaRepository;
    private static final Logger log = LoggerFactory.getLogger(MedicalRecordAdminServiceImpl.class);
    private final MedicalRecordEventProducer medicalRecordEventProducer;
    private final MedicalProcedureEventProducer medicalProcedureEventProducer;
    private final MedicalMonitoringEventProducer medicalMonitoringEventProducer;
    private final ConcurrentHashMap<UUID, CompletableFuture<MetaModelDto>> pendingRequests = new ConcurrentHashMap<>();
    private final MetaModelListener  metaModelListener;

    @Override
    public List<MedicalRecord> getAllMedicalRecord(){
        return medicalRecordRepository.findAll();
    }

    @Override
    public List<Integer> getNumberMedicalRecordsByMonth(int year) {
        List<MedicalRecord> records = medicalRecordRepository.findAll();
        List<Integer> monthCounts = new ArrayList<>(12);

        for (int i = 0; i < 12; i++) {
            monthCounts.add(0);
        }

        for (MedicalRecord record : records) {
            LocalDateTime creationDate = record.getCreationTimestamp();
            if (creationDate.getYear() == year) {
                int month = creationDate.getMonthValue(); 
                monthCounts.set(month - 1, monthCounts.get(month - 1) + 1); 
            }
        }
        return monthCounts;
    }

    @Override
    public List<MedicalProcedure> getAllMedicalProcedure(){
        List<MedicalProcedure> procedures = medicalProcedureRepository.findAll();
        log.info("Retrieved {} medical procedures", procedures.size());
        return procedures;
    }

    @Override
    public List<Integer> getNumberMedicalProcedureByMonth(int year) {
        List<MedicalProcedure> procedures = medicalProcedureRepository.findAll();
        List<Integer> monthCounts = new ArrayList<>(12);

        for (int i = 0; i < 12; i++) {
            monthCounts.add(0);
        }

        for (MedicalProcedure procedure : procedures) {
            LocalDateTime creationDate = procedure.getCreationTimestamp();
            if (creationDate.getYear() == year) {
                int month = creationDate.getMonthValue(); 
                monthCounts.set(month - 1, monthCounts.get(month - 1) + 1); 
            }
        }
        return monthCounts;
    }

    @Override
    public List<MedicalMonitoring> getAllMedicalMonitoring(){
        return medicalMonitoringRepository.findAll();
    }

    @Override
    public List<Integer> getNumberMedicalMonitoringByMonth(int year) {
        List<MedicalMonitoring> monitorings = medicalMonitoringRepository.findAll();
        List<Integer> monthCounts = new ArrayList<>(12);

        for (int i = 0; i < 12; i++) {
            monthCounts.add(0);
        }

        for (MedicalMonitoring mono : monitorings) {
            LocalDateTime creationDate = mono.getCreationTimestamp();
            if (creationDate.getYear() == year) {
                int month = creationDate.getMonthValue(); 
                monthCounts.set(month - 1, monthCounts.get(month - 1) + 1); 
            }
        }
        return monthCounts;
    }

    @Override
    public MedicalRecord getMedicalRecordById(UUID id){
        return medicalRecordRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
    }

    @Override
    public MedicalProcedure getMedicalProcedureById(UUID id){
        return medicalProcedureRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("MedicalProcedure not found"));
    }

    @Override
    public void deleteMedicalProcedureById(UUID id) {
        MedicalProcedure toDelete = medicalProcedureRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("MedicalProcedure not found"));
            medicalProcedureRepository.delete(toDelete);
    }



    @Override
    public List<MedicalProcedure> getMedicalProcedureByPatientId(UUID id){
        List<MedicalProcedure> allProcedures = medicalProcedureRepository.findAll();
        return allProcedures.stream()
            .filter(procedure -> procedure.getPatientId().equals(id))
            .collect(Collectors.toList());
    }


    @Override
    public MedicalMonitoring getMedicalMonitoringById(UUID id){
        return medicalMonitoringRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("MedicalMonitotring not found"));

    }

    @Override
    public void deleteMedicalMonitoringById(UUID id) {
        MedicalMonitoring toDelete = medicalMonitoringRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("MedicalMonitoring not found"));
            medicalMonitoringRepository.delete(toDelete);
    }

    @Override
    public List<MedicalMonitoring> getMedicalMonitoringByPatientId(UUID id){
        List<MedicalMonitoring> allMonitoring = medicalMonitoringRepository.findAll();
        return allMonitoring.stream()
            .filter(procedure -> procedure.getPatientId().equals(id))
            .collect(Collectors.toList());
    }

    @Override
    public MedicalRecord createMedicalRecord(UUID agentId){
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setMedicalAgentId(agentId);
        medicalRecord.setPatientId(medicalRecord.getId());
        try {
            Optional<MedicalRecordSchema> medicalMedicalRecordSchemaOpt = getMedicalRecordSchema();
        
            if (medicalMedicalRecordSchemaOpt.isPresent()) {

                MedicalRecordSchema medicalMedicalRecordSchema = medicalMedicalRecordSchemaOpt.get();
                medicalRecord.initializePatientDataFields(medicalMedicalRecordSchema.getFields());
            } else {
                throw new RuntimeException("No Medical Record Schema found.");
            }

            medicalRecord = medicalRecordRepository.save(medicalRecord);
            medicalRecordEventProducer.sendMedicalRecordCreatedEvent(medicalRecord);
        } catch (Exception e) {
            throw new RuntimeException("Error creating medical record", e);
        }
        return medicalRecord;
    }

    @Override
    public MedicalProcedure createMedicalProcedure(CreateMedicalComponentRequest request, UUID agentId){
        MedicalProcedure medicalProcedure = new MedicalProcedure();
        medicalProcedure.setMedicalAgentId(agentId);
        medicalProcedure.setPatientId(request.getPatientId());

        try {
            Optional<MedicalProcedureSchema> medicalMedicalProcedureSchemaOpt = getMedicalProcedureSchema();
        
            if (medicalMedicalProcedureSchemaOpt.isPresent()) {

                MedicalProcedureSchema medicalProcedureSchema = medicalMedicalProcedureSchemaOpt.get();
                medicalProcedure.initializePatientDataFields(medicalProcedureSchema.getFields());
            } else {
                throw new RuntimeException("No Medical Record Schema found.");
            }

            medicalProcedure = medicalProcedureRepository.save(medicalProcedure);
            medicalProcedureEventProducer.sendMedicalProcedureCreatedEvent(medicalProcedure);
        } catch (Exception e) {
            throw new RuntimeException("Error creating medical record", e);
        }
        return medicalProcedure;
    }
    
    @Override
    public MedicalMonitoring createMedicalMonitoring(CreateMedicalComponentRequest request, UUID agentId){
        MedicalMonitoring medicalMonitoring = new MedicalMonitoring();
        medicalMonitoring.setMedicalAgentId(agentId);
        medicalMonitoring.setPatientId(request.getPatientId());

        try {
            Optional<MedicalMonitoringSchema> medicalMedicalMonitoringSchemaOpt = getMedicalMonitoringSchema();
        
            if (medicalMedicalMonitoringSchemaOpt.isPresent()) {

                MedicalMonitoringSchema medicalMonitoringSchema = medicalMedicalMonitoringSchemaOpt.get();
                medicalMonitoring.initializePatientDataFields(medicalMonitoringSchema.getFields());
            } else {
                throw new RuntimeException("No Medical Record Schema found.");
            }

            medicalMonitoring = medicalMonitoringRepository.save(medicalMonitoring);
            medicalMonitoringEventProducer.sendMedicalMonitoringCreatedEvent(medicalMonitoring);
        } catch (Exception e) {
            throw new RuntimeException("Error creating medical record", e);
        }
        return medicalMonitoring;
    }

    @Override
    public MedicalRecord fillCoupleOfMedicalRecord(FillCoupleOfMedicalDataRequest request, UUID dataId){
        
        MedicalRecord medicalRecord = medicalRecordRepository.findById(dataId)
                .orElseThrow(() -> new IllegalArgumentException("MedicalRecord not found"));
        
        try {
            medicalRecord.setCoupleOfPatientData(request.getMedicalData());
            medicalRecordRepository.save(medicalRecord);
            medicalRecordEventProducer.sendMedicalRecordUpdatedEvent(medicalRecord);
        } catch (Exception e) {
            throw new RuntimeException("Error updating medical record", e);
        }

        return medicalRecord;
    }

    @Override
    public MedicalProcedure fillCoupleOfMedicalProcedure(FillCoupleOfMedicalDataRequest request, UUID dataId){
        
        MedicalProcedure medicalProcedure = medicalProcedureRepository.findById(dataId)
                .orElseThrow(() -> new IllegalArgumentException("MedicalProcedure not found"));

        try {
            medicalProcedure.setMultipleMedicalProcedureData(request.getMedicalData());
            medicalProcedureRepository.save(medicalProcedure);
            medicalProcedureEventProducer.sendMedicalProcedureCreatedEvent(medicalProcedure);
        } catch (Exception e) {
            throw new RuntimeException("Error updating medical record", e);
        }

        return medicalProcedure;
    }

    @Override
    public MedicalMonitoring fillCoupleOfMedicalMonitoring(FillCoupleOfMedicalDataRequest request, UUID agentId){
        
        MedicalMonitoring medicalMonitoring = medicalMonitoringRepository.findById(agentId)
                .orElseThrow(() -> new IllegalArgumentException("MedicalMonitoring not found"));
        
        try {
            medicalMonitoring.setMultipleMedicalMonitoringData(request.getMedicalData());
            medicalMonitoringRepository.save(medicalMonitoring);
            medicalMonitoringEventProducer.sendMedicalMonitoringCreatedEvent(medicalMonitoring);
        } catch (Exception e) {
            throw new RuntimeException("Error updating medical record", e);
        }

        return medicalMonitoring;
    }

    @Override
    public MedicalRecord updateFieldOfMedicalRecord(UpdateMedicalDataRequest request, UUID dataId){
        
        MedicalRecord medicalRecord = medicalRecordRepository.findById(dataId)
                .orElseThrow(() -> new IllegalArgumentException("MedicalRecord not found"));
        
        try {
                medicalRecord.setPatientData(request.getFieldName(),request.getValue());
                medicalRecordRepository.save(medicalRecord);
                medicalRecordEventProducer.sendMedicalRecordUpdatedEvent(medicalRecord);
            } catch (Exception e) {
                throw new RuntimeException("Error updating medical record", e);
            }
        
        return medicalRecord;
    }

    @Override
    public MedicalProcedure updateFieldOfMedicalProcedure(UpdateMedicalDataRequest request, UUID dataId){
        
        MedicalProcedure medicalProcedure = medicalProcedureRepository.findById(dataId)
                .orElseThrow(() -> new IllegalArgumentException("MedicalProcedure not found"));
        
        try {
                medicalProcedure.setMedicalProcedureData(request.getFieldName(),request.getValue());
                medicalProcedureRepository.save(medicalProcedure);
                medicalProcedureEventProducer.sendMedicalProcedureUpdatedEvent(medicalProcedure);
            } catch (Exception e) {
                throw new RuntimeException("Error updating medical record", e);
            }
        
        return medicalProcedure;
    }

    @Override
    public MedicalMonitoring updateFieldOfMedicalMonitoring(UpdateMedicalDataRequest request, UUID dataId){
        
        MedicalMonitoring medicalMonitoring = medicalMonitoringRepository.findById(dataId)
                .orElseThrow(() -> new IllegalArgumentException("MedicalMonitoring not found"));
        
        try {
                medicalMonitoring.setMedicalMonitoringData(request.getFieldName(),request.getValue());
                medicalMonitoringRepository.save(medicalMonitoring);
                medicalMonitoringEventProducer.sendMedicalMonitoringUpdatedEvent(medicalMonitoring);
            } catch (Exception e) {
                throw new RuntimeException("Error updating medical record", e);
            }
        
        return medicalMonitoring;
    }

    @Override
    public Optional<MedicalRecordSchema> getMedicalRecordSchema(){
        return medicalRecordSchemaRepository.findAll().stream().findFirst();
    }
    
    @Override
    public Optional<MedicalProcedureSchema> getMedicalProcedureSchema(){
        return medicalProcedureSchemaRepository.findAll().stream().findFirst();
    }
    
    @Override
    public Optional<MedicalMonitoringSchema> getMedicalMonitoringSchema(){
        return medicalMonitoringSchemaRepository.findAll().stream().findFirst();
    }

    @Override
    public CompletableFuture<MedicalRecordSchema> saveMedicalRecordSchema(SaveMedicalComponentSchemaRequest request){
        CompletableFuture<MetaModelDto> futureResponse = new CompletableFuture<>();
        pendingRequests.put(request.getMetamodelId(), futureResponse);
        metaModelListener.sendMetamodelFieldsRequestForSchema(request.getMetamodelId(), TypeMetaModel.DOSSIER_MEDICAL);
    
        return futureResponse.thenApply(metaModelDto -> {
            MedicalRecordSchema medicalRecordSchema = MedicalRecordSchema.getInstance();
            medicalRecordSchema.initialize(metaModelDto.getId(), metaModelDto.getFields());
            try {
                MedicalRecordSchema savedMedicalRecordSchema = medicalRecordSchemaRepository.save(medicalRecordSchema);
                return savedMedicalRecordSchema;
            } catch (Exception e) {
                log.error("Error saving MedicalProcedureSchema: {}", e.getMessage(), e);

            }      
            return medicalRecordSchema;
        });
    }

    @Override
    public CompletableFuture<MedicalProcedureSchema> saveMedicalProcedureSchema(SaveMedicalComponentSchemaRequest request) {
        CompletableFuture<MetaModelDto> futureResponse = new CompletableFuture<>();
        pendingRequests.put(request.getMetamodelId(), futureResponse);
        metaModelListener.sendMetamodelFieldsRequestForSchema(request.getMetamodelId(), TypeMetaModel.ACTE_MEDICAL);
    
        return futureResponse.thenApply(metaModelDto -> {
            MedicalProcedureSchema medicalProcedureSchema = MedicalProcedureSchema.getInstance();
            medicalProcedureSchema.initialize(metaModelDto.getId(), metaModelDto.getFields());
            try {
                MedicalProcedureSchema savedMedicalProcedureSchema = medicalProcedureSchemaRepository.save(medicalProcedureSchema);
                return savedMedicalProcedureSchema;
            } catch (Exception e) {
                log.error("Error saving MedicalProcedureSchema: {}", e.getMessage(), e);
            }      
            return medicalProcedureSchema;
        });
    }

    @Override
    public CompletableFuture<MedicalMonitoringSchema> saveMedicalMonitoringSchema(SaveMedicalComponentSchemaRequest request){
        CompletableFuture<MetaModelDto> futureResponse = new CompletableFuture<>();
        pendingRequests.put(request.getMetamodelId(), futureResponse);
        metaModelListener.sendMetamodelFieldsRequestForSchema(request.getMetamodelId(), TypeMetaModel.SUIVI);
    
        return futureResponse.thenApply(metaModelDto -> {
            MedicalMonitoringSchema medicalMonitoringSchema = MedicalMonitoringSchema.getInstance();
            medicalMonitoringSchema.initialize(metaModelDto.getId(), metaModelDto.getFields());
            try {
                MedicalMonitoringSchema savedMedicalMonitoringSchema = medicalMonitoringSchemaRepository.save(medicalMonitoringSchema);
                return savedMedicalMonitoringSchema;
            } catch (Exception e) {
                log.error("Error saving MedicalProcedureSchema: {}", e.getMessage(), e);
            }      
            return medicalMonitoringSchema;
        });
    }

    public void completeResponse(UUID requestId, MetaModelDto response) {
        CompletableFuture<MetaModelDto> futureResponse = pendingRequests.get(requestId);
        if (futureResponse != null) {
            futureResponse.complete(response);
        }
    }

}

