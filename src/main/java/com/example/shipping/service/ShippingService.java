package com.example.shipping.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.shipping.domain.dto.ShippingResponseDTO;
import com.example.shipping.domain.entity.ShippingEntity;
import com.example.shipping.repository.ShippingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {

    private final ShippingRepository shippingRepository;

    public List<ShippingResponseDTO> getList() {
        log.info("=== Shipping Service getList ===");

        return shippingRepository.findAll().stream().map(ShippingResponseDTO::fromEntity).toList();
    }

    public ShippingResponseDTO getShipping(Integer shipping_id) {
        log.info("=== Shipping Service getShipping ===");

        ShippingEntity shipping = shippingRepository.findById(shipping_id)
                .orElseThrow(() -> new RuntimeException("주문 없음"));

        return ShippingResponseDTO.fromEntity(shipping);
    }
}
