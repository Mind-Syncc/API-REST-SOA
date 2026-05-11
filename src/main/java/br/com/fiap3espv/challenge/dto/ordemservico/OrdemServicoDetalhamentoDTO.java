package br.com.fiap3espv.challenge.dto.ordemservico;

import br.com.fiap3espv.challenge.dto.veiculo.VeiculoDTO;
import br.com.fiap3espv.challenge.model.OrdemServico;
import br.com.fiap3espv.challenge.model.Veiculo;
import br.com.fiap3espv.challenge.model.enums.CategoriaServico;
import br.com.fiap3espv.challenge.model.enums.TipoFalha;

import java.time.LocalDate;

public record OrdemServicoDetalhamentoDTO (
        Long id,
        Long clienteId,
        String concessionariaId,
        LocalDate dataServico,
        String tipoServico,
        VeiculoDTO veiculo,
        CategoriaServico categoriaServico,
        TipoFalha tipoFalha,
        String descricaoProblema,
        Double valorTotal,
        boolean ativo) {
    public OrdemServicoDetalhamentoDTO(OrdemServico ordemServico) {
        this(ordemServico.getId(),
                ordemServico.getCliente().getId(),
                ordemServico.getConcessionariaId(),
                ordemServico.getDataServico(),
                ordemServico.getTipoServico(),
                new VeiculoDTO(ordemServico.getVeiculo()),
                ordemServico.getCategoriaServico(),
                ordemServico.getTipoFalha(),
                ordemServico.getDescricaoProblema(),
                ordemServico.getValorTotal(),
                ordemServico.isAtivo());

    }
}
