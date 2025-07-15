package com.veterinaria.demo.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Data
public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;
}
