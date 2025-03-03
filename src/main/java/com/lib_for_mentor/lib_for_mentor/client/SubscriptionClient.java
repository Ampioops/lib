package com.lib_for_mentor.lib_for_mentor.client;

import com.lib_for_mentor.lib_for_mentor.client.dto.SubscriptionRequest;
import com.lib_for_mentor.lib_for_mentor.client.dto.SubscriptionResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "subscription-service", url = "http://host.docker.internal:8889")
public interface SubscriptionClient {

    @PostMapping(value = "/subs/subscription/")
    void createSubscription(@RequestBody SubscriptionRequest request);

    @GetMapping(value = "/subs/subscription/user/{userId}")
    Page<SubscriptionResponse> getSubscriptionsByUser(
            @PathVariable("userId") Integer userId,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit
    );
}
