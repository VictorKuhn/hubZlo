package com.hubzlo.hubzlo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "contratanteHubZlo")
public class Contratante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;

    @Column(unique = true, nullable = false)
    private String cpf;

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

    // Especialidades desejadas para o cuidador
    private String especialidadesDesejadas;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @OneToMany(mappedBy = "contratante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dependente> dependentes;
}
