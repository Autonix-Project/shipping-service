package com.example.shipping.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.shipping.common.CarProvider;
import com.example.shipping.domain.dto.ShippingRequestDTO;
import com.example.shipping.domain.dto.ShippingResponseDTO;
import com.example.shipping.domain.entity.ShippingEntity;
import com.example.shipping.exception.CustomException;
import com.example.shipping.exception.ErrorCode;
import com.example.shipping.order.OrderClient;
import com.example.shipping.order.OrderResponseDTO;
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
    private final OrderClient orderClient;

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

        CarProvider.CarInfo car = resolveCarInfo(request.getOrderId());
        entity.assignCar(car);

        ShippingEntity saved = shippingRepository.save(entity);
        saved.assignShippingNumber();

        return ShippingResponseDTO.fromEntity(saved);
    }

    private CarProvider.CarInfo resolveCarInfo(Integer orderId) {
        if (orderId != null) {
            try {
                OrderResponseDTO order = orderClient.getOrder(orderId).getData();
                if (order != null && order.getCarModel() != null) {
                    return new CarProvider.CarInfo(
                            "VH-" + String.format("%04d", orderId),
                            order.getCarModel());
                }
            } catch (Exception e) {
                log.warn("order-service 조회 실패, mock 차량 사용: {}", e.getMessage());
            }
        }
        return carProvider.getRandomCar();
    }
}
