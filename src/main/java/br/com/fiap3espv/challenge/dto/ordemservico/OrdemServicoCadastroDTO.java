package br.com.fiap3espv.challenge.dto.ordemservico;

import br.com.fiap3espv.challenge.dto.veiculo.VeiculoDTO;
import br.com.fiap3espv.challenge.model.enums.CategoriaServico;
import br.com.fiap3espv.challenge.model.enums.TipoFalha;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OrdemServicoCadastroDTO (@NotNull(message = "O campo clienteId precisa ser preenchido") Long clienteId,
                                       @NotNull(message = "O campo concessionariaId precisa ser preenchido")
                                       String concessionariaId,
                                       LocalDate dataServico,
                                       String tipoServico,
                                       @NotNull(message = "O campo veiculo precisa ser preenchido")
                                       @Valid VeiculoDTO veiculo,
                                       CategoriaServico categoriaServico,
                                       TipoFalha tipoFalha,
                                       String descricaoProblema,
                                       Double valorTotal) {
}
