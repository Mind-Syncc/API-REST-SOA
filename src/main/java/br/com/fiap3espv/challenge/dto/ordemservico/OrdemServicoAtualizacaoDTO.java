package br.com.fiap3espv.challenge.dto.ordemservico;

import br.com.fiap3espv.challenge.dto.veiculo.VeiculoDTO;
import br.com.fiap3espv.challenge.model.enums.CategoriaServico;
import br.com.fiap3espv.challenge.model.enums.TipoFalha;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record OrdemServicoAtualizacaoDTO(String tipoServico,
                                         @NotNull(message = "O campo veiculo precisa ser preenchido")
                                         @Valid VeiculoDTO veiculo,
                                         @NotNull(message = "O campo categoriaServico precisa ser preenchido")
                                         CategoriaServico categoriaServico,
                                         @NotNull(message = "O campo tipoFalha precisa ser preenchido")
                                         TipoFalha tipoFalha,
                                         @NotBlank(message = "O campo descricaoProblema precisa ser preenchido")
                                         String descricaoProblema,
                                         Double valorTotal) {
}
