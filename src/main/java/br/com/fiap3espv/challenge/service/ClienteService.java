package br.com.fiap3espv.challenge.service;

import br.com.fiap3espv.challenge.dto.ClienteCadastroDTO;
import br.com.fiap3espv.challenge.model.Cliente;
import br.com.fiap3espv.challenge.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(ClienteCadastroDTO clienteCadastroDTO) {
        Cliente cliente = new Cliente(clienteCadastroDTO);
        clienteRepository.save(cliente);
        return cliente;
    }
}
