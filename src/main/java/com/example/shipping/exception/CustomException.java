package com.example.shipping.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

// 서비스에서 던지는 예외
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

}
