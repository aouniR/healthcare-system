package com.healthcare.medicalrecord_service.repository;

import com.healthcare.medicalrecord_service.entity.MedicalRecordSchema;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface MedicalRecordSchemaRepository extends MongoRepository<MedicalRecordSchema, UUID> {
}


