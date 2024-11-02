package com.healthcare.medicalrecord_service.repository;

import com.healthcare.medicalrecord_service.entity.MedicalMonitoringSchema;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface MedicalMonitoringSchemaRepository extends MongoRepository<MedicalMonitoringSchema, UUID> {
}


