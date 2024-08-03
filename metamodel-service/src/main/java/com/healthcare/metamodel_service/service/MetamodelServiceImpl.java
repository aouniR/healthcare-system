package com.healthcare.metamodel_service.service;


import com.healthcare.metamodel_service.entity.Metamodel;
import com.healthcare.metamodel_service.repository.MetamodelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MetamodelServiceImpl implements MetamodelService {

    
    private final MetamodelRepository metamodelRepository;

    @Autowired
    public MetamodelServiceImpl(MetamodelRepository metamodelRepository){
        this.metamodelRepository=metamodelRepository;
    }

    @Override
    public List<Metamodel> getAllMetamodels() {
        return metamodelRepository.findAll();
    }

    @Override
    public Metamodel getMetamodelById(UUID id) {
        return metamodelRepository.findById(id).orElse(null);
    }

    @Override
    public Metamodel saveMetamodel(Metamodel metamodel) {
        return metamodelRepository.save(metamodel);
    }

    @Override
    public void deleteMetamodel(UUID id) {
        metamodelRepository.deleteById(id);
    }
}
