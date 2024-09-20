package com.hubzlo.hubzlo.service;

import com.hubzlo.hubzlo.model.Contratante;
import com.hubzlo.hubzlo.model.Dependente;
import com.hubzlo.hubzlo.model.Vaga;
import com.hubzlo.hubzlo.repository.ContratanteRepository;
import com.hubzlo.hubzlo.repository.DependenteRepository;
import com.hubzlo.hubzlo.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private ContratanteRepository contratanteRepository;

    @Autowired
    private DependenteRepository dependenteRepository;

    /**
     * Método para listar todas as vagas disponíveis.
     *
     * @return Lista de todas as vagas cadastradas.
     */
    public List<Vaga> listarVagas() {
        return vagaRepository.findAll();
    }

    /**
     * Método para criar uma nova vaga de contratação, vinculada a um contratante e a um dependente.
     *
     * @param contratanteId       ID do contratante que está criando a vaga.
     * @param dependenteId        ID do dependente vinculado à vaga.
     * @param dataHoraContrato    Data e hora do início do contrato.
     * @param valorHora           Valor por hora oferecido.
     * @param observacoes         Observações adicionais sobre a vaga.
     * @return Vaga criada e salva no banco de dados.
     */
    public Vaga criarVaga(Long contratanteId, Long dependenteId, LocalDateTime dataHoraContrato, Double valorHora, String observacoes) {
        // Buscar o contratante pelo ID
        Contratante contratante = contratanteRepository.findById(contratanteId)
                .orElseThrow(() -> new RuntimeException("Contratante não encontrado"));

        // Buscar o dependente pelo ID
        Dependente dependente = dependenteRepository.findById(dependenteId)
                .orElseThrow(() -> new RuntimeException("Dependente não encontrado"));

        // Calcular a idade do dependente com base na data de nascimento
        int idadeDependente = Period.between(dependente.getDataNascimento(), LocalDate.now()).getYears();

        // Criar e configurar a vaga
        Vaga vaga = new Vaga();
        vaga.setDataHoraContrato(dataHoraContrato);
        vaga.setValorHora(valorHora);
        vaga.setCidade(contratante.getCidade());
        vaga.setNomeContratante(contratante.getNome() + " " + contratante.getSobrenome());
        vaga.setIdadeDependente(idadeDependente);
        vaga.setDoencaDependente(dependente.getDoencaDiagnosticada());
        vaga.setObservacoes(observacoes);
        vaga.setContratante(contratante);
        vaga.setDependente(dependente);

        // Salvar a vaga no banco de dados e retornar
        return vagaRepository.save(vaga);
    }
}
