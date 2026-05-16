package br.com.fiap3espv.challenge.service;

import br.com.fiap3espv.challenge.dto.cliente.*;
import br.com.fiap3espv.challenge.exceptions.CPFValidacaoException;
import br.com.fiap3espv.challenge.exceptions.RecursoNaoEncontradoException;
import br.com.fiap3espv.challenge.model.Cliente;
import br.com.fiap3espv.challenge.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteCadastroResponseDTO cadastrarCliente(ClienteCadastroDTO clienteCadastroDTO) {
        Cliente cliente = new Cliente(clienteCadastroDTO);
        clienteRepository.save(cliente);
        return new ClienteCadastroResponseDTO(cliente);
    }

    public Page<ClienteListagemDTO> listarClientesAtivos(Pageable pageable) {
        return clienteRepository.findAllByAtivoTrue(pageable).map(ClienteListagemDTO::new);
    }

    public Page<ClienteListagemDTO> listarClientesNaoAtivos(Pageable pageable) {
        return clienteRepository.findAllByAtivoFalse(pageable).map(ClienteListagemDTO::new);
    }

    public ClienteDetalhesDTO exibirDetalhesCliente(Long id) {
        Cliente cliente = procurarClientePorId(id);

        return new ClienteDetalhesDTO(cliente);
    }

    public void atualizarDadosCliente(ClienteAtualizacaoDTO clienteAtualizacaoDTO, Long id) {
        Cliente cliente = procurarClientePorId(id);

        String regex = "^\\d{11}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(clienteAtualizacaoDTO.cpf());

        if (!matcher.find()) {
            throw new CPFValidacaoException("CPF é inválido");
        }

        cliente.atualizarDados(clienteAtualizacaoDTO);
        clienteRepository.save(cliente);
    }

    public void ativarCliente(Long id) {
        Cliente cliente = procurarClientePorId(id);

        cliente.ativarCliente();
        clienteRepository.save(cliente);
    }

    public void removerCliente(Long id) {
        Cliente cliente = procurarClientePorId(id);

        cliente.desativarCliente();
        clienteRepository.save(cliente);
    }

    public Cliente procurarClientePorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
    }
}
