package com.veterinaria.demo.model.dto.procedure;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProcedureUpdateDTO(
        String description,
        BigDecimal price
) {
}
