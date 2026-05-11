package br.com.fiap3espv.challenge.dto.ordemservico;

import br.com.fiap3espv.challenge.dto.veiculo.VeiculoDTO;
import br.com.fiap3espv.challenge.model.enums.CategoriaServico;
import br.com.fiap3espv.challenge.model.enums.TipoFalha;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OrdemServicoCadastroDTO (@NotNull Long clienteId,
                                       @NotNull String concessionariaId,
                                       LocalDate dataServico,
                                       String tipoServico,
                                       @NotNull @Valid VeiculoDTO veiculo,
                                       CategoriaServico categoriaServico,
                                       TipoFalha tipoFalha,
                                       String descricaoProblema,
                                       Double valorTotal) {
}
