package com.veterinaria.demo.model.entity;

import com.veterinaria.demo.enums.ProcedureType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Procedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProcedureType type;
    private LocalDateTime date;
    private BigDecimal price;
    private String description;

    @ManyToOne
    @JoinColumn(name = "animalId")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User veterinarian;

}
