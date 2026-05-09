package br.com.fiap3espv.challenge.service;

import br.com.fiap3espv.challenge.dto.ClienteAtualizacaoDTO;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            throw new RuntimeException("Não foi possível encontrar o recurso");
        }

        Cliente cliente = clienteOptional.get();
        return new ClienteDetalhesDTO(cliente);
    }

    public void atualizarDadosCliente(ClienteAtualizacaoDTO clienteAtualizacaoDTO, Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        String regex = "^\\d{11}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(clienteAtualizacaoDTO.cpf());

        if (!matcher.find()) {
            throw new RuntimeException("CPF é inválido");
        }

        if (clienteOptional.isEmpty()) {
            throw new RuntimeException("Não foi possível encontrar o recurso");
        }

        Cliente cliente =  clienteOptional.get();
        cliente.atualizarDados(clienteAtualizacaoDTO);
        clienteRepository.save(cliente);
    }

    public void ativarCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isEmpty()) {
            throw new RuntimeException("Não foi possível encontrar o recurso");
        }

        Cliente cliente = clienteOptional.get();
        cliente.ativarCliente();
        clienteRepository.save(cliente);
    }

    public void removerCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isEmpty()) {
            throw new RuntimeException("Não foi possível encontrar o recurso");
        }

        Cliente cliente = clienteOptional.get();
        cliente.desativarCliente();
        clienteRepository.save(cliente);
    }
}
