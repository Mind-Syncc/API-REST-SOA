package br.com.fiap3espv.challenge.dto.cliente;

import br.com.fiap3espv.challenge.model.Cliente;

public record ClienteDetalhesDTO (Long id,
                                  String nome,
                                  String cpf,
                                  String telefone,
                                  String email,
                                  String cidade,
                                  String estado,
                                  boolean ativo) {

    public ClienteDetalhesDTO(Cliente cliente) {
        this(cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getCidade(),
                cliente.getEstado(),
                cliente.isAtivo());
    }
}
