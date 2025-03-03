package com.lib_for_mentor.lib_for_mentor.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoResponse {
    private Integer userId;
    private String numberPhone;
    private String email;
    private SubscriptionResponse subscription;
}
