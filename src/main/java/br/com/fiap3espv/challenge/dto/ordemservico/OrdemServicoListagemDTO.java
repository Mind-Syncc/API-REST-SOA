package br.com.fiap3espv.challenge.dto.ordemservico;

import br.com.fiap3espv.challenge.dto.veiculo.VeiculoDTO;
import br.com.fiap3espv.challenge.model.OrdemServico;

import java.time.LocalDate;

public record OrdemServicoListagemDTO(Long idCliente,
                                      String concessionariaId,
                                      LocalDate dataServico,
                                      VeiculoDTO veiculo,
                                      String descricaoProblema,
                                      Double valorTotal) {
    public OrdemServicoListagemDTO(OrdemServico ordemServico) {
        this(ordemServico.getId(),
                ordemServico.getConcessionariaId(),
                ordemServico.getDataServico(),
                new VeiculoDTO(ordemServico.getVeiculo()),
                ordemServico.getDescricaoProblema(),
                ordemServico.getValorTotal());
    }
}
