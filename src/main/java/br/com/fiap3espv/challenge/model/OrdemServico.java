package br.com.fiap3espv.challenge.model;

import br.com.fiap3espv.challenge.model.enums.CategoriaServico;
import br.com.fiap3espv.challenge.model.enums.TipoFalha;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "Ordens_de_servico")
@Getter
public class OrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;
    private String concessionariaId;
    private LocalDate dataServico;
    private String tipoServico;

    @Embedded
    private Veiculo veiculo;

    @Enumerated(EnumType.STRING)
    private CategoriaServico categoriaServico;
    @Enumerated(EnumType.STRING)
    private TipoFalha tipoFalha;

    private String descricaoProblema;
    private Double valortotal;

    public OrdemServico() {

    }
}
