package com.hubzlo.hubzlo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;

@Component
public class JwtTokenProvider {

    // Usando o SecretKey com mais segurança ao invés de String
    private final SecretKey jwtSecretKey;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Gerar o token JWT a partir do Authentication
    public String generateToken(Authentication authentication) {
        String email = authentication.getName();

        // Extrair as roles do usuário autenticado
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        // Adicionar as roles ao token JWT
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", String.join(",", roles))  // Adicionar as roles ao payload do JWT
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // Validar o token e retornar o e-mail do usuário (ou null se inválido)
    public String getEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Verificar se o token está expirado
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
