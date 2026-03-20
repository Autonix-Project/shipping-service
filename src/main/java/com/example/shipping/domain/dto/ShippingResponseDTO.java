package com.example.shipping.domain.dto;

import java.time.LocalDateTime;

import com.example.shipping.domain.entity.ShippingEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShippingResponseDTO {

    private Integer shippingId;

    private String shippingNumber;
    private String shippingCarId;
    private String shippingCarModel;
    private String shippingState;

    private LocalDateTime createdAt;
    private LocalDateTime arrivalAt;

    public static ShippingResponseDTO fromEntity(ShippingEntity entity) {
        return ShippingResponseDTO.builder()
                .shippingId(entity.getShippingId())
                .shippingNumber(entity.getShippingNumber())
                .shippingState(entity.getShippingState())
                .shippingCarId(entity.getShippingCarId())
                .shippingCarModel(entity.getShippingCarModel())
                .createdAt(entity.getCreatedAt())
                .arrivalAt(entity.getArrivalAt())
                .build();
    }

}
