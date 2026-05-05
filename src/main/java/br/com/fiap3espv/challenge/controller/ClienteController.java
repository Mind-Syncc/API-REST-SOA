package br.com.fiap3espv.challenge.controller;

import br.com.fiap3espv.challenge.dto.ClienteCadastroDTO;
import br.com.fiap3espv.challenge.dto.ClienteCadastroResponseDTO;
import br.com.fiap3espv.challenge.model.Cliente;
import br.com.fiap3espv.challenge.service.ClienteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
}
