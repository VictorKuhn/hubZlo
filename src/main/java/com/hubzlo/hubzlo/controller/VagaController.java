package com.hubzlo.hubzlo.controller;

import com.hubzlo.hubzlo.model.Vaga;
import com.hubzlo.hubzlo.service.VagaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {

    private final VagaService vagaService;

    public VagaController(VagaService vagaService) {
        this.vagaService = vagaService;
    }

    // Criar uma nova vaga (apenas para Contratante e Admin)
    @PreAuthorize("hasAuthority('CONTRATANTE') or hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Vaga> criarVaga(@RequestParam Long contratanteId,
                                          @RequestParam Long dependenteId,
                                          @RequestParam LocalDateTime dataHoraContrato,
                                          @RequestParam Double valorHora,
                                          @RequestParam String observacoes) {

        Vaga vaga = vagaService.criarVaga(contratanteId, dependenteId, dataHoraContrato, valorHora, observacoes);
        return ResponseEntity.ok(vaga);
    }

    // Listar todas as vagas (público)
    @GetMapping("/all")
    public ResponseEntity<List<Vaga>> listarVagas() {
        List<Vaga> vagas = vagaService.listarVagas();
        return ResponseEntity.ok(vagas);
    }
}
