package com.example.shipping.domain.entity;

import java.time.LocalDateTime;

import com.example.shipping.common.CarProvider;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SHIPPINGS")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id", unique = true)
    private Integer shippingId;

    @Column(name = "shipping_number", unique = true)
    private String shippingNumber;

    @Column(name = "shipping_car_id")
    private String shippingCarId;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "shipping_state")
    private String shippingState;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "arrival_at")
    private LocalDateTime arrivalAt;

    @Column(name = "order_id")
    private Integer orderId;

    public void assignShippingNumber() {
        this.shippingNumber = String.format("SHIP-%03d", this.shippingId);
    }

    public void assignCar(CarProvider.CarInfo car) {
        this.carModel = car.getCarModel();
        this.shippingCarId = car.getCarId();
    }
}
