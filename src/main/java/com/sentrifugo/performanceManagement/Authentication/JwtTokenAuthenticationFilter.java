package com.sentrifugo.performanceManagement.Authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@CrossOrigin(origins = "${custom.frontendUrl}")
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtValidator jwtValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // First, handle the OPTIONS preflight request as before
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return; // Important to prevent further processing
        }

        // Then, check for the exempted path
        String path = request.getRequestURI();
        System.out.println(path);
        if (path.startsWith("/loginByPassword") || path.equals("/login/verify")) {
            filterChain.doFilter(request, response);
            return; // Skip JWT validation for this path
        }

        if (path.startsWith("/api/sa/download/")) {
            filterChain.doFilter(request, response);
            return; // Skip JWT validation for this path
        }

        // Continue with JWT validation logic for other paths
        String authHeader = request.getHeader("Authorization");
        System.out.println("auth header" + authHeader);
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Remove "Bearer " prefix
            System.out.println("Tok"+token);
            username = jwtValidator.validateToken(token);
        }

        if (username != null) {
            // If a valid username is found, proceed with the filter chain
            filterChain.doFilter(request, response);
        } else {
            // If the token is invalid or missing, send an unauthorized response
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}