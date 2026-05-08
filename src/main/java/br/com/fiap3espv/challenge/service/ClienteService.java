package br.com.fiap3espv.challenge.service;

import br.com.fiap3espv.challenge.dto.ClienteCadastroDTO;
import br.com.fiap3espv.challenge.dto.ClienteDetalhesDTO;
import br.com.fiap3espv.challenge.dto.ClienteListagemDTO;
import br.com.fiap3espv.challenge.model.Cliente;
import br.com.fiap3espv.challenge.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(ClienteCadastroDTO clienteCadastroDTO) {
        Cliente cliente = new Cliente(clienteCadastroDTO);
        clienteRepository.save(cliente);
        return cliente;
    }

    public Page<ClienteListagemDTO> listarClientesAtivos(Pageable pageable) {
        return clienteRepository.findAllByAtivoTrue(pageable).map(ClienteListagemDTO::new);
    }

    public Page<ClienteListagemDTO> listarClientesNaoAtivos(Pageable pageable) {
        return clienteRepository.findAllByAtivoFalse(pageable).map(ClienteListagemDTO::new);
    }

    public ClienteDetalhesDTO exibirDetalhesCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isEmpty()) {
            throw new RuntimeException("Não foi possível encontar o recurso");
        }

        Cliente cliente = clienteOptional.get();
        return new ClienteDetalhesDTO(cliente);
    }
}
