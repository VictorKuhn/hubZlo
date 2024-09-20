package com.hubzlo.hubzlo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "dependenteHubZlo")
public class Dependente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;

    private String tipoSanguineo;
    private String doencaDiagnosticada;
    private String rotinaMedicacao;

    private Boolean dependenteLocomocao;

    @ManyToOne
    @JoinColumn(name = "contratante_id")
    private Contratante contratante;
}
