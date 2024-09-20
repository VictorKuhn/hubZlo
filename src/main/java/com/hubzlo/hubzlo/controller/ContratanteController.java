package com.hubzlo.hubzlo.controller;

import com.hubzlo.hubzlo.model.Contratante;
import com.hubzlo.hubzlo.repository.ContratanteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratantes")
public class ContratanteController {

    private final ContratanteRepository contratanteRepository;

    public ContratanteController(ContratanteRepository contratanteRepository) {
        this.contratanteRepository = contratanteRepository;
    }

    // Criar contratante
    @PostMapping("/create")
    public ResponseEntity<Contratante> criarContratante(@RequestBody Contratante contratante) {
        Contratante savedContratante = contratanteRepository.save(contratante);
        return ResponseEntity.ok(savedContratante);
    }

    // Listar todos os contratantes
    @GetMapping("/all")
    public List<Contratante> listarContratantes() {
        return contratanteRepository.findAll();
    }

    // Obter contratante por ID
    @GetMapping("/{id}")
    public ResponseEntity<Contratante> obterContratante(@PathVariable Long id) {
        Contratante contratante = contratanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contratante não encontrado"));
        return ResponseEntity.ok(contratante);
    }

    // Atualizar contratante
    @PutMapping("/update/{id}")
    public ResponseEntity<Contratante> atualizarContratante(@PathVariable Long id, @RequestBody Contratante detalhesContratante) {
        Contratante contratante = contratanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contratante não encontrado"));

        contratante.setNome(detalhesContratante.getNome());
        contratante.setSobrenome(detalhesContratante.getSobrenome());
        // Atualize outros campos conforme necessário

        Contratante updatedContratante = contratanteRepository.save(contratante);
        return ResponseEntity.ok(updatedContratante);
    }

    // Deletar contratante
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarContratante(@PathVariable Long id) {
        Contratante contratante = contratanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contratante não encontrado"));

        contratanteRepository.delete(contratante);
        return ResponseEntity.noContent().build();
    }
}
