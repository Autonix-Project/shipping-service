package com.example.shipping.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseDTO {
    private Integer orderId;
    private String orderNumber;
    private String carModel;
    private String carColor;
    private String destination;
    private Integer totalQuantity;
}
