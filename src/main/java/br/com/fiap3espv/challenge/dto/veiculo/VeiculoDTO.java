package br.com.fiap3espv.challenge.dto.veiculo;

import br.com.fiap3espv.challenge.model.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VeiculoDTO (@NotBlank String veiculoModelo,
                          @NotNull Integer veiculoAno,
                          @NotNull Integer veiculoKm) {
    public VeiculoDTO (Veiculo veiculo) {
        this(veiculo.getVeiculoModelo(),
                veiculo.getVeiculoAno(),
                veiculo.getVeiculoKm());
    }
}
