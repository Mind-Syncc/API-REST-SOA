package br.com.fiap3espv.challenge.model;

import br.com.fiap3espv.challenge.dto.veiculo.VeiculoDTO;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Veiculo {
    private String veiculoModelo;
    private Integer veiculoAno;
    private Integer veiculoKm;

    public Veiculo() {

    }

    public Veiculo(VeiculoDTO veiculoDTO) {
        this.veiculoModelo = veiculoDTO.veiculoModelo();
        this.veiculoAno = veiculoDTO.veiculoAno();
        this.veiculoKm = veiculoDTO.veiculoKm();
    }
}
