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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/shippings")
@RequiredArgsConstructor
@Tag(name = "Shipping Service", description = "Shipping Service API")
@Slf4j
public class ShippingController {

    private final ShippingService shippingService;

    @GetMapping
    @Operation(summary = "배송 주문 전체 조회", description = "배송 주문 전체 조회에 사용하는 API")
    public ResponseEntity<List<ShippingResponseDTO>> list() {

        log.info("=== Shipping Controller list ===");

        return ResponseEntity.ok(shippingService.getList());

    }

    @GetMapping("/{shippingId}")
    @Operation(summary = "특정 배송 주문 조회", description = "배송 주문 Id로 특정 배송 주문 한 개 조회할 때 사용하는 API")
    public ResponseEntity<ShippingResponseDTO> getMethodName(@PathVariable Integer shippingId) {
        log.info("=== Shipping Controller detail ===");

        return ResponseEntity.ok(shippingService.getShipping(shippingId));
    }

    @PostMapping("/create")
    @Operation(summary = "배송 주문 생성", description = "QC_PASS 된 주문에 대해 배송 주문 생성하는 API")
    public ResponseEntity<ShippingResponseDTO> create(@RequestBody ShippingRequestDTO request) {
        log.info("=== Shipping Controller create ===");

        return ResponseEntity.ok(shippingService.create(request));
    }

}
