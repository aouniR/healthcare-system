package com.healthcare.metamodel_service.service;


import java.util.List;
import java.util.UUID;

import com.healthcare.metamodel_service.entity.Metamodel;

public interface MetamodelService {
    List<Metamodel> getAllMetamodels();
    Metamodel getMetamodelById(UUID id);
    Metamodel saveMetamodel(Metamodel metamodel);
    void deleteMetamodel(UUID id);
}

