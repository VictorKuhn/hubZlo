package com.hubzlo.hubzlo.controller;

import com.hubzlo.hubzlo.model.Dependente;
import com.hubzlo.hubzlo.repository.DependenteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dependentes")
public class DependenteController {

    private final DependenteRepository dependenteRepository;

    public DependenteController(DependenteRepository dependenteRepository) {
        this.dependenteRepository = dependenteRepository;
    }

    // Criar dependente
    @PostMapping("/create")
    public ResponseEntity<Dependente> criarDependente(@RequestBody Dependente dependente) {
        Dependente savedDependente = dependenteRepository.save(dependente);
        return ResponseEntity.ok(savedDependente);
    }

    // Listar todos os dependentes
    @GetMapping("/all")
    public List<Dependente> listarDependentes() {
        return dependenteRepository.findAll();
    }

    // Obter dependente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Dependente> obterDependente(@PathVariable Long id) {
        Dependente dependente = dependenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dependente não encontrado"));
        return ResponseEntity.ok(dependente);
    }

    // Atualizar dependente
    @PutMapping("/update/{id}")
    public ResponseEntity<Dependente> atualizarDependente(@PathVariable Long id, @RequestBody Dependente detalhesDependente) {
        Dependente dependente = dependenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dependente não encontrado"));

        dependente.setNome(detalhesDependente.getNome());
        dependente.setSobrenome(detalhesDependente.getSobrenome());
        // Atualize os outros campos conforme necessário

        Dependente updatedDependente = dependenteRepository.save(dependente);
        return ResponseEntity.ok(updatedDependente);
    }

    // Deletar dependente
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarDependente(@PathVariable Long id) {
        Dependente dependente = dependenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dependente não encontrado"));

        dependenteRepository.delete(dependente);
        return ResponseEntity.noContent().build();
    }
}
