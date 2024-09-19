package com.healthcare.metamodel_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.healthcare.metamodel_service.entity.MetaModel;
import com.healthcare.metamodel_service.entity.TypeMetaModel;
import com.healthcare.metamodel_service.request.RegisterRequest;
import com.healthcare.metamodel_service.request.AddFieldsRequest;
import com.healthcare.metamodel_service.request.DeleteFieldsRequest;

public interface MetaModelService {
    List<MetaModel> getAllMetaModels();
    Optional<List<MetaModel>> getMetaModelByType(TypeMetaModel type);
    MetaModel getMetaModelById(UUID id);
    MetaModel addFieldsToMetaModelById(UUID id,AddFieldsRequest request);
    MetaModel deleteFieldFromMetaModelById(UUID id,DeleteFieldsRequest request);
    MetaModel saveMetamodel(RegisterRequest request, UUID userId);
    void deleteMEtaModelById(UUID id);
}

