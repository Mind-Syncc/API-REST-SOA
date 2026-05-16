package br.com.fiap3espv.challenge.dto.ordemservico;

import br.com.fiap3espv.challenge.dto.veiculo.VeiculoDTO;
import br.com.fiap3espv.challenge.model.enums.CategoriaServico;
import br.com.fiap3espv.challenge.model.enums.TipoFalha;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record OrdemServicoAtualizacaoDTO(String tipoServico,
                                         @NotNull @Valid VeiculoDTO veiculo,
                                         @NotNull CategoriaServico categoriaServico,
                                         @NotNull TipoFalha tipoFalha,
                                         @NotBlank String descricaoProblema,
                                         Double valorTotal) {
}
