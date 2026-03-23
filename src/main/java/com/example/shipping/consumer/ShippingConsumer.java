package com.example.shipping.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.shipping.domain.dto.ShippingRequestDTO;
import com.example.shipping.service.ShippingService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShippingConsumer {

    private final ShippingService shippingService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "shipping.ready")
    public void shippingConsumer(String message) {

        log.info("Kafka message received: {}", message);

        try {
            ShippingRequestDTO request = objectMapper.readValue(message, ShippingRequestDTO.class);
            shippingService.create(request);

        } catch (Exception e) {
            log.error("Failed to process Kafka message", e);
        }
    }

}
