package com.healthcare.metamodel_service.repository;


import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.metamodel_service.entity.Metamodel;

@Repository
public interface MetamodelRepository extends JpaRepository<Metamodel, UUID> {
}
