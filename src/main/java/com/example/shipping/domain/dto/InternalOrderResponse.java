package com.example.shipping.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InternalOrderResponse {
    private Long orderId;
    private String orderNumber;
    private String carModel;
    private String carColor;
    private String destination;
    private Integer totalQuantity;
}
