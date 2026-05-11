package br.com.fiap3espv.challenge.dto.cliente;

import br.com.fiap3espv.challenge.model.Cliente;

public record ClienteListagemDTO (Long id,
                                  String nome,
                                  String cpf,
                                  String telefone,
                                  String email) {

    public ClienteListagemDTO(Cliente cliente) {
        this(cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail());
    }
}
