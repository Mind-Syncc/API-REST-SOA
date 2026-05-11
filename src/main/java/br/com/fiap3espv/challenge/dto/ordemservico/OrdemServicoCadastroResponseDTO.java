package br.com.fiap3espv.challenge.dto.ordemservico;

import br.com.fiap3espv.challenge.dto.veiculo.VeiculoDTO;
import br.com.fiap3espv.challenge.model.OrdemServico;
import br.com.fiap3espv.challenge.model.Veiculo;

import java.time.LocalDate;

public record  OrdemServicoCadastroResponseDTO (
        Long id,
        String nomeCliente,
        String concessionariaId,
        LocalDate dataServico,
        VeiculoDTO veiculo,
        String descricaoProblema) {
    public OrdemServicoCadastroResponseDTO(OrdemServico ordemServico) {
        this(ordemServico.getId(),
                ordemServico.getCliente().getNome(),
                ordemServico.getConcessionariaId(),
                ordemServico.getDataServico(),
                new VeiculoDTO(ordemServico.getVeiculo()),
                ordemServico.getDescricaoProblema());
    }
}
