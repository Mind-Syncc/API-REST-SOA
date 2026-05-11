package br.com.fiap3espv.challenge.controller;

import br.com.fiap3espv.challenge.dto.ordemservico.OrdemServicoCadastroDTO;
import br.com.fiap3espv.challenge.dto.ordemservico.OrdemServicoCadastroResponseDTO;
import br.com.fiap3espv.challenge.service.OrdemServicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/ordens-servicos")
public class OrdemServicoController {
    @Autowired
    private OrdemServicoService ordemServicoService;

    @PostMapping
    @Transactional
    public ResponseEntity<OrdemServicoCadastroResponseDTO> cadastrarOrdemDeServico(@RequestBody @Valid OrdemServicoCadastroDTO ordemServicoCadastroDTO,
                                                                                   UriComponentsBuilder uriBuilder) {
        OrdemServicoCadastroResponseDTO response = ordemServicoService.cadastrarOrdemDeServico(ordemServicoCadastroDTO);
        var uri = uriBuilder.path("/api/v1/ordens-servicos/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
