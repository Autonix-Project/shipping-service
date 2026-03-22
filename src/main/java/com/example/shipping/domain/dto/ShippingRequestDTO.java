package com.example.shipping.domain.dto;

import java.time.LocalDateTime;

import com.example.shipping.domain.entity.ShippingEntity;

import lombok.Getter;

@Getter
public class ShippingRequestDTO {
    private Integer orderId;

    private String shippingCarId;
    private String carModel;
    private String shippingState;
    private LocalDateTime createdAt;
    private LocalDateTime arrivalAt;

    public ShippingEntity toEntity() {
        return ShippingEntity.builder()
                .orderId(orderId)
                .shippingState("출고대기")
                .createdAt(LocalDateTime.now())
                .arrivalAt(LocalDateTime.now().plusDays(3))
                .build();
    }
}
