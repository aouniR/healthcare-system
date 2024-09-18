package com.healthcare.metamodel_service.service;

import java.util.List;
import java.util.UUID;

import com.healthcare.metamodel_service.entity.MetaModel;
import com.healthcare.metamodel_service.request.RegisterRequest;
import com.healthcare.metamodel_service.request.UpdateRequest;

public interface MetaModelService {
    List<MetaModel> getAllMetaModels();
    MetaModel getMetaModelById(UUID id);
    MetaModel updateMetaModelById(UUID id,UpdateRequest request);
    MetaModel saveMetamodel(RegisterRequest request, UUID userId);
    void deleteMEtaModelById(UUID id);
}

