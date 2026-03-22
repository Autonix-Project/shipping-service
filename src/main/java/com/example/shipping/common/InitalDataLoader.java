package com.example.shipping.common;

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
    private final CarProvider carProvider;

    @Override
    public void run(String... args) throws Exception {
        log.info("=== InitialDataLoaer run called ===");
        saveIfNotExists("SHIP-001", "출고대기", 0L);
        saveIfNotExists("SHIP-002", "배송중", 2L);
        saveIfNotExists("SHIP-003", "배송완료", 3L);
    }

    private void saveIfNotExists(String shippingNumber, String shippingState,

            Long daysToAdd) {
        if (shippingRepository.existsByShippingNumber(shippingNumber)) {
            return;
        }

        CarProvider.CarInfo car = carProvider.getRandomCar();
        LocalDateTime now = LocalDateTime.now();

        ShippingEntity ship = ShippingEntity.builder()
                .shippingNumber(shippingNumber)
                .shippingState(shippingState)
                .shippingCarId(car.getCarId())
                .carModel(car.getCarModel())
                .createdAt(now)
                .arrivalAt(now.plusDays(2))
                .orderId(1)
                .build();

        shippingRepository.save(ship);
    }
}
