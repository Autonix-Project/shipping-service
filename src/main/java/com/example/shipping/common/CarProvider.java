package com.example.shipping.common;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Component
public class CarProvider {
    private final List<CarInfo> cars = List.of(
            new CarInfo("CAR-001", "Model X"),
            new CarInfo("CAR-002", "Model S"),
            new CarInfo("CAR-003", "Model Z"),
            new CarInfo("CAR-04", "Model Y"));

    public CarInfo getRandomCar() {
        return cars.get(ThreadLocalRandom.current().nextInt(cars.size()));
    }

    @Getter
    @AllArgsConstructor
    public static class CarInfo {
        private String carId;
        private String carModel;
    }
}
