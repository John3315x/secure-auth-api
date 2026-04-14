package com.nanakusa.secureauthapi.config;

import com.nanakusa.secureauthapi.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // ❌ Desactiva CSRF porque es API
                .csrf(csrf -> csrf.disable())

                // 🔥 SIN SESIONES (clave para JWT)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 🔐 Reglas de acceso (Qué rutas necesitan autenticación y cuáles no)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // 👈 público
                        .requestMatchers("/api/admin/**").hasRole("ADMIN") // 👈 solo ADMIN
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN") // 👈 ambos
                        .anyRequest().authenticated() // 👈 protegido (SI)
                )

                // 🔑 Agregar filtro JWT
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
