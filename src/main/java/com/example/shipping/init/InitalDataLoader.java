package com.example.shipping.init;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.shipping.domain.entity.ShippingEntity;
import com.example.shipping.repository.ShippingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitalDataLoader implements CommandLineRunner {

    private final ShippingRepository shippingRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("=== InitialDataLoaer run called ===");
        saveIfNotExists("SHIP-001", "출고대기", "CAR-001", "Model X", 0L);
        saveIfNotExists("SHIP-002", "배송중", "CAR-002", "Model S", 2L);
        saveIfNotExists("SHIP-003", "배송완료", "CAR-001", "Model X", 3L);
    }

    private void saveIfNotExists(String shippingNumber, String shippingState, String shippingCarId,
            String carModel,
            Long daysToAdd) {
        if (shippingRepository.existsByShippingNumber(shippingNumber)) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        ShippingEntity ship = ShippingEntity.builder()
                .shippingNumber(shippingNumber)
                .shippingState(shippingState)
                .shippingCarId(shippingCarId)
                .carModel(carModel)
                .createdAt(now)
                .arrivalAt(now.plusDays(2))
                .order_id(1)
                .build();

        shippingRepository.save(ship);
    }
}
