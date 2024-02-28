//package com.sentrifugo.performanceManagement.Authentication;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//@Component
//public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtValidator jwtValidator;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        String token = null;
//        String username = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            token = authHeader.substring(7); // Remove "Bearer " prefix
//            username = jwtValidator.validateToken(token);
//        }
//
////        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
////            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////            SecurityContextHolder.getContext().setAuthentication(authentication);
////        }
////
////        filterChain.doFilter(request, response);
//        if (username != null) {
//            // Token is valid, proceed with the request
//            filterChain.doFilter(request, response);
//        } else {
//            // Token is invalid or missing, send unauthorized response
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }
//    }
//}
