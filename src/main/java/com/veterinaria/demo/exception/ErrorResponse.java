package com.veterinaria.demo.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ErrorResponse {

    private int status;
    private String message;
    private String timestamp;
}
