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

        ShippingEntity entity = request.toEntity();

        // order-service에서 차종 조회, 실패 시 mock 사용
        if (request.getOrderId() != null) {
            try {
                InternalOrderResponse order = orderServiceClient.getOrderInternal(request.getOrderId()).getData();
                if (order != null) {
                    entity.setCarModel(order.getCarModel());
                    entity.setShippingCarId("VH-" + String.format("%04d", request.getOrderId()));
                } else {
                    entity.assignCar(carProvider.getRandomCar());
                }
            } catch (Exception e) {
                log.warn("order-service 조회 실패, mock 차량 사용: {}", e.getMessage());
                entity.assignCar(carProvider.getRandomCar());
            }
        } else {
            entity.assignCar(carProvider.getRandomCar());
        }

        ShippingEntity saved = shippingRepository.save(entity);
        saved.assignShippingNumber();

        return ShippingResponseDTO.fromEntity(saved);
    }
}
