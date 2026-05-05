package br.com.fiap3espv.challenge.model;

import br.com.fiap3espv.challenge.dto.ClienteCadastroDTO;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Clientes")
@Getter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String cidade;
    private String estado;
    private boolean ativo = true;

    public Cliente () {

    }

    public Cliente(ClienteCadastroDTO clienteCadastroDTO) {
        this.nome = clienteCadastroDTO.nome();
        this.cpf = clienteCadastroDTO.cpf();
        this.telefone = clienteCadastroDTO.telefone();
        this.email = clienteCadastroDTO.email();
        this.cidade = clienteCadastroDTO.cidade();
        this.estado = clienteCadastroDTO.estado();
    }
}
