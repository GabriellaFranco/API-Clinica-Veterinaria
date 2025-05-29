package com.veterinaria.demo.model.entity;

import com.veterinaria.demo.enums.UserProfile;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user_profiles")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Column(nullable = true)
    private String crmv_number;

    @Enumerated(EnumType.STRING)
    private UserProfile profile;

    @OneToMany(mappedBy = "veterinarian")
    @Nullable
    private List<Procedure> procedures;

    @OneToMany
    private List<Authority> authorities;

}
