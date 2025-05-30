package com.veterinaria.demo.model.dto.customer;

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
