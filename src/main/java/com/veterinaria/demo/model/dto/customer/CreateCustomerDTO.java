package com.veterinaria.demo.model.dto.customer;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateCustomerDTO(

        @Size(min = 4)
        @NotNull
        @Column(nullable = false)
        String name,

        @Pattern(regexp = "^\\d{11}$", message = "Phone must have 11 digits (area code + number)")
        @NotNull
        @Column(nullable = false)
        String phone,

        @Size(min = 11, max = 11, message = "Cpf must contain 11 digits (only numbers)")
        @NotNull
        @Column(nullable = false)
        String cpf
) {
}
