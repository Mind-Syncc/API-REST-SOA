package br.com.fiap3espv.challenge.dto.veiculo;

import br.com.fiap3espv.challenge.model.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VeiculoDTO (@NotBlank(message = "O campo veiculoModelo precisa ser preenchido") String veiculoModelo,
                          @NotNull(message = "O campo veiculoAno precisa ser preenchido") Integer veiculoAno,
                          @NotNull(message = "O campo veiculoKm precisa ser preenchido") Integer veiculoKm) {
    public VeiculoDTO (Veiculo veiculo) {
        this(veiculo.getVeiculoModelo(),
                veiculo.getVeiculoAno(),
                veiculo.getVeiculoKm());
    }
}
