package com.nanakusa.secureauthapi.filter;

import com.nanakusa.secureauthapi.entity.User;
import com.nanakusa.secureauthapi.repository.UserRepository;
import com.nanakusa.secureauthapi.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // permitir rutas publicas
        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            String email = JwtUtil.validateToken(token);

            if (email != null) {

                // 🔥 CONSULTA A LA DB
                User user = userRepository.findByEmail(email).orElse(null);

                if (user != null) {

                    String role = user.getRole().name();

                    // AUTENTICAR CON SPRING (SPRING SECURITY)👈
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                            );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }

        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
