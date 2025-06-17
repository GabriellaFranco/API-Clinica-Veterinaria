package com.veterinaria.demo.configuration.security;

import com.veterinaria.demo.exception.CustomBasicAuthEntryPoint;
import com.veterinaria.demo.filters.PerformanceLoggingFilter;
import com.veterinaria.demo.filters.RequestLoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Profile("!prod")
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final RequestLoggingFilter requestLoggingFilter;
    private final PerformanceLoggingFilter performanceLoggingFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/animals","/animals/**","/procedures","/procedures/**")
                        .hasAnyRole("VETERINARIAN", "RECEPTION", "ADMIN")
                        .requestMatchers("/customers","/customers/**").hasAnyRole("RECEPTION", "ADMIN")
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/users", "/users/**").hasRole("ADMIN")
                        .requestMatchers("/login", "/system-error-reason").permitAll()
                )
                .addFilterBefore(requestLoggingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(performanceLoggingFilter, UsernamePasswordAuthenticationFilter.class);

        http.formLogin(Customizer.withDefaults());
        http.httpBasic(sbc -> sbc.authenticationEntryPoint(new CustomBasicAuthEntryPoint()));

        return http.build();

    }
}
