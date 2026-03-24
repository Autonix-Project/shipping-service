package com.example.shipping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.shipping.domain.dto.InternalOrderResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;

@FeignClient(name = "order-service", url = "${services.order.url}")
public interface OrderServiceClient {

    @GetMapping("/orders/{orderId}/internal")
    ApiResult<InternalOrderResponse> getOrderInternal(@PathVariable Integer orderId);

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    class ApiResult<T> {
        private T data;
    }
}
