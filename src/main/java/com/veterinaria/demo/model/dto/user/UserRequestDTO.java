package com.veterinaria.demo.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record UserRequestDTO(

        @NotNull @NotBlank
        @Size(min = 4)
        String name,

        @NotNull
        @Email
        String email,

        @NotNull
        @Size(min = 6, max = 12)
        String password,

        @Nullable
        String crmv_number
) {
}
