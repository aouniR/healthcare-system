package com.healthcare.metamodel_service.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.metamodel_service.entity.MetaModel;

@Repository
public interface MetaModelRepository extends JpaRepository<MetaModel, UUID>{
    List<MetaModel> findAllByOrderByCreationTimestampDesc();
}

