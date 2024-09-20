package com.hubzlo.hubzlo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "cuidadorHubZlo")
public class Cuidador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    private String endereco;
    private String bairro;
    private Integer numeroEndereco;
    private String complemento;
    private String cidade;
    private String estado;
    private String pais;

    private String telefone1;
    private String telefone2;

    // Referências trabalhistas
    private String referencia1Nome;
    private String referencia1Telefone;
    private String referencia2Nome;
    private String referencia2Telefone;
    private String referencia3Nome;
    private String referencia3Telefone;

    // Especialidades
    private String especialidades;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
}
