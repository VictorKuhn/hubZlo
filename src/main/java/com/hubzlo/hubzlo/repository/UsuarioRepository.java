package com.hubzlo.hubzlo.repository;

import com.hubzlo.hubzlo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar um usuário pelo e-mail
    Optional<Usuario> findByEmail(String email);

    // Verificar se um e-mail já está em uso
    Boolean existsByEmail(String email);
}
