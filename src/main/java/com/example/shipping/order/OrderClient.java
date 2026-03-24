package com.example.shipping.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;

@FeignClient(name = "order-service", url = "${services.order.url}")
public interface OrderClient {

    // order-service에게 주문 정보 요청
    @GetMapping("/orders/{id}/internal")
    ApiResult<OrderResponseDTO> getOrder(@PathVariable("id") Integer id);

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    class ApiResult<T> {
        private T data;
    }
}
