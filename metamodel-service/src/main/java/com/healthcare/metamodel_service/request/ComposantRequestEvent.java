package com.healthcare.metamodel_service.request;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ComposantRequestEvent {
    private List<UUID> ids;
}
