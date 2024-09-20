package com.hubzlo.hubzlo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vagaHubZlo")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHoraContrato;  // Data e hora do contrato

    @Column(nullable = false)
    private Double valorHora;  // Valor ofertado por hora

    @Column(nullable = false)
    private String cidade;  // Cidade do contratante (puxado da tabela contratante)

    @Column(nullable = false)
    private String nomeContratante;  // Nome do contratante (puxado da tabela contratante)

    @Column(nullable = false)
    private Integer idadeDependente;  // Idade do dependente (calculada a partir da data de nascimento)

    @Column(nullable = false)
    private String doencaDependente;  // Doença diagnosticada do dependente

    @Column(length = 1000)
    private String observacoes;  // Observações adicionais sobre a vaga

    @ManyToOne
    @JoinColumn(name = "contratante_id")
    private Contratante contratante;  // Relação com o contratante

    @ManyToOne
    @JoinColumn(name = "dependente_id")
    private Dependente dependente;  // Relação com o dependente
}
