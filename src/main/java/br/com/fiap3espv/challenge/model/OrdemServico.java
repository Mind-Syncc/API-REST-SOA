package br.com.fiap3espv.challenge.model;

import br.com.fiap3espv.challenge.dto.ordemservico.OrdemServicoCadastroDTO;
import br.com.fiap3espv.challenge.model.enums.CategoriaServico;
import br.com.fiap3espv.challenge.model.enums.TipoFalha;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Ordens_de_servico")
@Getter
public class OrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Setter
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
    private Double valorTotal;

    private boolean ativo = true;

    public OrdemServico() {

    }

    public OrdemServico(OrdemServicoCadastroDTO ordemServicoCadastroDTO) {
        this.concessionariaId = ordemServicoCadastroDTO.concessionariaId();
        this.dataServico = ordemServicoCadastroDTO.dataServico();
        this.tipoServico = ordemServicoCadastroDTO.tipoServico();
        this.veiculo = new Veiculo(ordemServicoCadastroDTO.veiculo());
        this.categoriaServico = ordemServicoCadastroDTO.categoriaServico();
        this.tipoFalha = ordemServicoCadastroDTO.tipoFalha();
        this.descricaoProblema = ordemServicoCadastroDTO.descricaoProblema();
        this.valorTotal = ordemServicoCadastroDTO.valorTotal();
    }

}
