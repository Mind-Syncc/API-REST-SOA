package br.com.fiap3espv.challenge.controller;

import br.com.fiap3espv.challenge.dto.usuario.UsuarioAtualizacaoDTO;
import br.com.fiap3espv.challenge.dto.usuario.UsuarioCadastroDTO;
import br.com.fiap3espv.challenge.dto.usuario.UsuarioCadastroResponseDTO;
import br.com.fiap3espv.challenge.dto.usuario.UsuarioListagemDTO;
import br.com.fiap3espv.challenge.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioCadastroResponseDTO> cadastrarUsuario(@RequestBody @Valid UsuarioCadastroDTO usuarioCadastroDTO,
                                                                       UriComponentsBuilder uriBuilder) {
        UsuarioCadastroResponseDTO response = usuarioService.cadastrarUsuario(usuarioCadastroDTO);
        var uri = uriBuilder.path("/api/v1/usuarios/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioListagemDTO>> listarUsuarios(Pageable pageable) {
        Page<UsuarioListagemDTO> page = usuarioService.listarUsuarios(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> alterarUsuario(@RequestBody @Valid UsuarioAtualizacaoDTO usuarioAtualizacaoDTO, @PathVariable Long id) {
        usuarioService.atualizarUsuario(usuarioAtualizacaoDTO, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.ok().build();
    }

}
