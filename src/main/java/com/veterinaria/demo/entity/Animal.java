package com.veterinaria.demo.entity;

import com.veterinaria.demo.enums.AnimalSpecies;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private AnimalSpecies species;
    private String breed;
    private int age;
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer tutor;

    @OneToMany(mappedBy = "animal")
    private List<Procedure> procedures;
}
