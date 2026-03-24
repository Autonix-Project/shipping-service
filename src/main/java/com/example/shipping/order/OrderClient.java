package com.example.shipping.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service")
public interface OrderClient {

    // order-service에게 주문 정보 요청
    @GetMapping("/orders/{id}/internal")
    OrderResponseDTO getOrder(@PathVariable("id") Integer id);

}
