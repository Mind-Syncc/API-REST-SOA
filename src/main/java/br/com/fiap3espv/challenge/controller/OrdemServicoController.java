package br.com.fiap3espv.challenge.controller;

import br.com.fiap3espv.challenge.dto.ordemservico.*;
import br.com.fiap3espv.challenge.service.OrdemServicoService;
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

    @GetMapping
    public ResponseEntity<Page<OrdemServicoListagemDTO>> listarOrdensDeServico(@PageableDefault(sort = "dataServico") Pageable pageable) {
        Page<OrdemServicoListagemDTO> page = ordemServicoService.listarOrdensDeServico(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoDetalhamentoDTO> exibirOrdemDeServicoPorId(@PathVariable Long id) {
        OrdemServicoDetalhamentoDTO ordemServicoDetalhamento = ordemServicoService.exibirOrdemDeServico(id);
        return ResponseEntity.ok().body(ordemServicoDetalhamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> alterarOrdemServico(@RequestBody @Valid OrdemServicoAtualizacaoDTO ordemServicoAtualizacaoDTO,
                                                    @PathVariable Long id) {
        ordemServicoService.atualizarOrdemDeServico(ordemServicoAtualizacaoDTO, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/ativacao/{id}")
    public ResponseEntity<Void> ativarOrdemDeServico(@PathVariable Long id) {
        ordemServicoService.ativarOrdemDeServico(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUmaOrdemDeServico(@PathVariable Long id) {
        ordemServicoService.removerOrdemDeServico(id);
        return ResponseEntity.noContent().build();
    }
}
