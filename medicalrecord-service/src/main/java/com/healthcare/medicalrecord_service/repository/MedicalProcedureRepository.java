package com.healthcare.medicalrecord_service.repository;

import com.healthcare.medicalrecord_service.entity.MedicalProcedure;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface MedicalProcedureRepository extends MongoRepository<MedicalProcedure, UUID> {
}


