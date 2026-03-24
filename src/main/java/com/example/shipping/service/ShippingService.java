package com.example.shipping.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.shipping.client.OrderServiceClient;
import com.example.shipping.common.CarProvider;
import com.example.shipping.domain.dto.InternalOrderResponse;
import com.example.shipping.domain.dto.ShippingRequestDTO;
import com.example.shipping.domain.dto.ShippingResponseDTO;
import com.example.shipping.domain.entity.ShippingEntity;
import com.example.shipping.exception.CustomException;
import com.example.shipping.exception.ErrorCode;
import com.example.shipping.repository.ShippingRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {

    private final ShippingRepository shippingRepository;
    private final CarProvider carProvider;
    private final OrderServiceClient orderServiceClient;

    public List<ShippingResponseDTO> getList() {
        log.info("=== Shipping Service getList ===");

        return shippingRepository.findAll().stream().map(ShippingResponseDTO::fromEntity).toList();
    }

    public ShippingResponseDTO getShipping(Integer shipping_id) {
        log.info("=== Shipping Service getShipping ===");

        ShippingEntity shipping = shippingRepository.findById(shipping_id)
                .orElseThrow(() -> new CustomException(ErrorCode.SHIPPING_NOT_FOUND));

        return ShippingResponseDTO.fromEntity(shipping);
    }

    @Transactional
    @CircuitBreaker(name = "shippingService")
    public ShippingResponseDTO create(ShippingRequestDTO request) {
        log.info("=== Shipping Service create ===");

        ShippingEntity saved = shippingRepository.save(request.toEntity());

        // shippingNumber
        String shippingNumber = String.format("SHIP-%03d", saved.getShippingId());
        saved.setShippingNumber(shippingNumber);

        // order-service에서 차종 조회
        if (request.getOrderId() != null) {
            try {
                InternalOrderResponse order = orderServiceClient.getOrderInternal(request.getOrderId()).getData();
                if (order != null) {
                    saved.setCarModel(order.getCarModel());
                    saved.setShippingCarId("VH-" + String.format("%04d", saved.getShippingId()));
                } else {
                    setMockCar(saved);
                }
            } catch (Exception e) {
                log.warn("order-service 조회 실패, mock 차량 사용: {}", e.getMessage());
                setMockCar(saved);
            }
        } else {
            setMockCar(saved);
        }

        return ShippingResponseDTO.fromEntity(saved);
    }

    private void setMockCar(ShippingEntity entity) {
        CarProvider.CarInfo car = carProvider.getRandomCar();
        entity.setCarModel(car.getCarModel());
        entity.setShippingCarId(car.getCarId());
    }
}
