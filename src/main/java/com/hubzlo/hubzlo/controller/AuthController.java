package com.hubzlo.hubzlo.controller;

import com.hubzlo.hubzlo.dto.LoginRequestDTO;
import com.hubzlo.hubzlo.dto.SignupRequestDTO;
import com.hubzlo.hubzlo.model.Usuario;
import com.hubzlo.hubzlo.payload.JwtAuthenticationResponse;
import com.hubzlo.hubzlo.repository.UsuarioRepository;
import com.hubzlo.hubzlo.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    // Endpoint para login do usuário
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getSenha()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    // Endpoint para registro do usuário
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {

        // Verifica se o email já está registrado
        if (usuarioRepository.existsByEmail(signupRequestDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Erro: Este email já está sendo utilizado!");
        }

        // Criação de um novo usuário
        Usuario usuario = new Usuario();
        usuario.setEmail(signupRequestDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(signupRequestDTO.getSenha()));
        usuario.setRole(signupRequestDTO.getRole()); // Define a role fornecida na requisição

        // Salva o usuário no banco de dados
        usuarioRepository.save(usuario);

        // Gera um token JWT para o usuário recém-registrado
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signupRequestDTO.getEmail(),
                        signupRequestDTO.getSenha()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
}
