package com.lib_for_mentor.lib_for_mentor.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {
    private Integer id;
    private ContactInfoResponse contactInfo;
    private String type;
    private Integer referenceId;
    private LocalDateTime createdAt;
}
