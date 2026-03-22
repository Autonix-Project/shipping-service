package com.example.shipping.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shipping.domain.dto.ShippingRequestDTO;
import com.example.shipping.domain.dto.ShippingResponseDTO;
import com.example.shipping.service.ShippingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/shippings")
@RequiredArgsConstructor
@Slf4j
public class ShippingController {

    private final ShippingService shippingService;

    @GetMapping
    public ResponseEntity<List<ShippingResponseDTO>> list() {

        log.info("=== Shipping Controller list ===");

        return ResponseEntity.ok(shippingService.getList());

    }

    @GetMapping("/{shippingId}")
    public ResponseEntity<ShippingResponseDTO> getMethodName(@PathVariable Integer shippingId) {
        log.info("=== Shipping Controller detail ===");

        return ResponseEntity.ok(shippingService.getShipping(shippingId));
    }

    @PostMapping("/create")
    public ResponseEntity<ShippingResponseDTO> create(@RequestBody ShippingRequestDTO request) {
        log.info("=== Shipping Controller create ===");

        return ResponseEntity.ok(shippingService.create(request));
    }

}
