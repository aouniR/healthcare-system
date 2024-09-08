package com.healthcare.metamodel_service.service;

import java.util.List;
import java.util.UUID;
import com.healthcare.metamodel_service.entity.MetaModel;
import com.healthcare.metamodel_service.request.RegisterRequest;

public interface MetaModelService {
    void requestComposants(List<UUID> ids);
    List<MetaModel> getAllMetaModels();
    MetaModel getMetaModelById(UUID id);
    MetaModel updateMetaModelById(UUID id,RegisterRequest request);
    MetaModel saveMetamodel(RegisterRequest request, UUID userId);
    void deleteMEtaModelById(UUID id);
}

