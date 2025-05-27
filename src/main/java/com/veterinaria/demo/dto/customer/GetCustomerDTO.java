package com.veterinaria.demo.dto.customer;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetCustomerDTO(
        Long id,
        String name,
        String phone,
        String cpf,
        LocalDate creationDate
){}
