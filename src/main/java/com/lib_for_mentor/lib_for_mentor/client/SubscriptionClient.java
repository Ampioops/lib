package com.lib_for_mentor.lib_for_mentor.client;

import com.lib_for_mentor.lib_for_mentor.client.dto.SubscriptionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "subscription-service", url = "http://host.docker.internal:8889")
public interface SubscriptionClient {

    @PostMapping(value = "/subs/subscription/")
    void createSubscription(@RequestBody SubscriptionRequest request);
}
