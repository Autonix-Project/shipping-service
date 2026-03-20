package com.example.shipping.exception;

import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
// 클라이언트 응답 - json
public class ErrorResponse {

    private int status;
    private String error;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatus().value())  //HttpStatus.value
                        .error(errorCode.getStatus().name())    //HttpStatus.name
                        .code(errorCode.name())                 //custom
                        .message(errorCode.getMessage())        
                        .build());
    }
}