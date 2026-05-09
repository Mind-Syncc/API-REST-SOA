package br.com.fiap3espv.challenge.controller;

import br.com.fiap3espv.challenge.dto.*;
import br.com.fiap3espv.challenge.model.Cliente;
import br.com.fiap3espv.challenge.service.ClienteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @Transactional
    public ResponseEntity<ClienteCadastroResponseDTO> adicionarCliente(@RequestBody @Valid ClienteCadastroDTO clienteCadastroDTO,
                                                                       UriComponentsBuilder uriBuilder) {
        Cliente cliente = clienteService.cadastrarCliente(clienteCadastroDTO);
        ClienteCadastroResponseDTO responseDTO = new ClienteCadastroResponseDTO(cliente);
        var uri = uriBuilder.path("/api/v1/clientes").buildAndExpand(responseDTO.id()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteListagemDTO>> listarClientesAtivos(@PageableDefault(sort = "nome") Pageable pageable) {
        Page<ClienteListagemDTO> page = clienteService.listarClientesAtivos(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/desativados")
    public ResponseEntity<Page<ClienteListagemDTO>> listarClientesNaoAtivos(Pageable pageable) {
        Page<ClienteListagemDTO> page = clienteService.listarClientesNaoAtivos(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDetalhesDTO> exibirDetalhesCliente(@PathVariable Long id) {
        ClienteDetalhesDTO clienteDetalhesDTO = clienteService.exibirDetalhesCliente(id);
        return ResponseEntity.ok().body(clienteDetalhesDTO);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> atualizarDadosClientes(@RequestBody @Valid ClienteAtualizacaoDTO clienteAtualizacaoDTO, @PathVariable Long id) {
        clienteService.atualizarDadosCliente(clienteAtualizacaoDTO, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/ativacao/{id}")
    @Transactional
    public ResponseEntity<Void> ativarCliente(@PathVariable Long id) {
        clienteService.ativarCliente(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> removerCliente(@PathVariable Long id) {
        clienteService.removerCliente(id);
        return ResponseEntity.noContent().build();
    }
}
