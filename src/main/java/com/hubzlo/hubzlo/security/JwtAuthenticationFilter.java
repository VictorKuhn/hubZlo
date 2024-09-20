package com.hubzlo.hubzlo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Extrair o token JWT da solicitação
            String jwt = getJwtFromRequest(request);

            // Verifica se o token é válido
            if (jwt != null && tokenProvider.validateToken(jwt)) {
                // Pega o email do token
                String email = tokenProvider.getEmailFromJWT(jwt);

                // Carrega o usuário associado ao e-mail
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // Cria a autenticação do usuário
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Detalhes de autenticação adicionais
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Define a autenticação no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException ex) {
            logger.warn("Token JWT expirado", ex);
        } catch (Exception ex) {
            logger.error("Falha ao configurar a autenticação do usuário no contexto de segurança", ex);
        }

        // Continuar o filtro
        filterChain.doFilter(request, response);
    }

    // Método auxiliar para extrair o token JWT do cabeçalho da solicitação
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
