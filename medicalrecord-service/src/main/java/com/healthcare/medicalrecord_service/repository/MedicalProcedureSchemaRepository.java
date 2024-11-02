package com.healthcare.medicalrecord_service.repository;

import com.healthcare.medicalrecord_service.entity.MedicalProcedureSchema;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface MedicalProcedureSchemaRepository extends MongoRepository<MedicalProcedureSchema, UUID> {
}


