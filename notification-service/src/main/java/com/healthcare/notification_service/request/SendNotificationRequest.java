package com.healthcare.notification_service.request;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendNotificationRequest {
    private UUID userId;
    private String message;
}


