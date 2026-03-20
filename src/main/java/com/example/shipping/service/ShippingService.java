package com.example.shipping.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.shipping.domain.dto.ShippingResponseDTO;
import com.example.shipping.repository.ShippingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {

    private final ShippingRepository shippingRepository;

    public List<ShippingResponseDTO> getList() {
        log.info("=== Shipping Service List ===");

        return shippingRepository.findAll().stream().map(ShippingResponseDTO::fromEntity).toList();
    }

}
