package com.lib_for_mentor.lib_for_mentor.client.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
@Builder
public class SubscriptionRequest {
    private UUID userId;
    private String type;
    private UUID referenceId;
    private LocalDateTime createdAt;
}
