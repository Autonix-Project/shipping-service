package com.example.shipping.common;

import java.util.List;
import java.util.Random;
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
            new CarInfo("CAR-004", "Model Y"));

    public CarInfo getRandomCar() {
        return cars.get(ThreadLocalRandom.current().nextInt(cars.size()));
    }

    @Getter
    @AllArgsConstructor
    public static class CarInfo {
        private String carId;
        private String carModel;
    }

    public enum ShippingState {
        READY("출고대기"),
        IN_PROGRESS("배송중"),
        COMPLETED("배송완료");

        private final String label;

        ShippingState(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        private static final ShippingState[] VALUES = values();
        private static final int SIZE = VALUES.length;
        private static final Random RANDOM = new Random();

        public static ShippingState getRandomState() {
            return VALUES[RANDOM.nextInt(SIZE)];
        }

    }
}
