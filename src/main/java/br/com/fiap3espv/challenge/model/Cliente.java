package br.com.fiap3espv.challenge.model;

import br.com.fiap3espv.challenge.dto.ClienteAtualizacaoDTO;
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

    public void atualizarDados(ClienteAtualizacaoDTO clienteAtualizacaoDTO) {

        if (!clienteAtualizacaoDTO.nome().isEmpty()) {
            this.nome = clienteAtualizacaoDTO.nome();
        }

        if (!clienteAtualizacaoDTO.cpf().isEmpty()) {
            this.cpf = clienteAtualizacaoDTO.cpf();
        }

        if (!clienteAtualizacaoDTO.telefone().isEmpty()) {
            this.telefone = clienteAtualizacaoDTO.telefone();
        }

        if (!clienteAtualizacaoDTO.email().isEmpty()) {
            this.email = clienteAtualizacaoDTO.email();
        }

        if (!clienteAtualizacaoDTO.cidade().isEmpty()) {
            this.cidade = clienteAtualizacaoDTO.cidade();
        }

        if (!clienteAtualizacaoDTO.estado().isEmpty()) {
            this.estado = clienteAtualizacaoDTO.estado();
        }
    }
}
