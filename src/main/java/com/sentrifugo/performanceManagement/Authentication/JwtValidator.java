package com.sentrifugo.performanceManagement.Authentication;

import com.sentrifugo.performanceManagement.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {


    @Value("${custom.apiKey}")
    private String secretKey; // Consider moving this to application.properties

    @Autowired
    private UsersRepository userRepository;

    public String validateToken(String token) {
        try {
            System.out.println("Token"+token);
            Claims claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
            String email = claims.get("email", String.class);
            System.out.println(email);
            String user = userRepository.findByEmail(email);
            System.out.println(user);
            return user != null ? user : null; // Return username or null
        } catch (Exception e) {
            System.out.println(e+"hi");
            return null; // Token invalid
        }
    }
}