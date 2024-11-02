package com.healthcare.medicalrecord_service.repository;

import com.healthcare.medicalrecord_service.entity.MedicalMonitoring;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface MedicalMonitoringRepository extends MongoRepository<MedicalMonitoring, UUID> {
}


