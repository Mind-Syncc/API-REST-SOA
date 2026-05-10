package br.com.fiap3espv.challenge.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Veiculo {
    private String veiculoModelo;
    private Integer veiculoAno;
    private Integer veiculoKm;
}
