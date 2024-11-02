package com.healthcare.medicalrecord_service.repository;

import com.healthcare.medicalrecord_service.entity.MedicalRecord;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface MedicalRecordRepository extends MongoRepository<MedicalRecord, UUID> {
}


