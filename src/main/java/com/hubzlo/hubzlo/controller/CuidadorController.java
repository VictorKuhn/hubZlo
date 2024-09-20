package com.hubzlo.hubzlo.controller;

import com.hubzlo.hubzlo.model.Cuidador;
import com.hubzlo.hubzlo.repository.CuidadorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuidadores")
public class CuidadorController {

    private final CuidadorRepository cuidadorRepository;

    public CuidadorController(CuidadorRepository cuidadorRepository) {
        this.cuidadorRepository = cuidadorRepository;
    }

    // Criar cuidador
    @PostMapping("/create")
    public ResponseEntity<Cuidador> criarCuidador(@RequestBody Cuidador cuidador) {
        Cuidador savedCuidador = cuidadorRepository.save(cuidador);
        return ResponseEntity.ok(savedCuidador);
    }

    // Listar todos os cuidadores (somente cuidadores podem acessar)
    @GetMapping("/all")
    public List<Cuidador> listarCuidadores() {
        return cuidadorRepository.findAll();
    }

    // Obter cuidador por ID (somente cuidadores podem acessar)
    @GetMapping("/{id}")
    public ResponseEntity<Cuidador> obterCuidador(@PathVariable Long id) {
        Cuidador cuidador = cuidadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuidador não encontrado"));
        return ResponseEntity.ok(cuidador);
    }

    // Atualizar cuidador (somente cuidadores podem atualizar seus dados)
    @PutMapping("/update/{id}")
    public ResponseEntity<Cuidador> atualizarCuidador(@PathVariable Long id, @RequestBody Cuidador detalhesCuidador) {
        Cuidador cuidador = cuidadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuidador não encontrado"));

        cuidador.setNome(detalhesCuidador.getNome());
        cuidador.setSobrenome(detalhesCuidador.getSobrenome());
        // Atualize os outros campos conforme necessário

        Cuidador updatedCuidador = cuidadorRepository.save(cuidador);
        return ResponseEntity.ok(updatedCuidador);
    }

    // Deletar cuidador
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarCuidador(@PathVariable Long id) {
        Cuidador cuidador = cuidadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuidador não encontrado"));

        cuidadorRepository.delete(cuidador);
        return ResponseEntity.noContent().build();
    }
}