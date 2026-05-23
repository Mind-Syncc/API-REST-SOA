package br.com.fiap3espv.challenge.service;

import br.com.fiap3espv.challenge.dto.usuario.UsuarioAtualizacaoDTO;
import br.com.fiap3espv.challenge.dto.usuario.UsuarioCadastroDTO;
import br.com.fiap3espv.challenge.dto.usuario.UsuarioCadastroResponseDTO;
import br.com.fiap3espv.challenge.dto.usuario.UsuarioListagemDTO;
import br.com.fiap3espv.challenge.exceptions.RecursoNaoEncontradoException;
import br.com.fiap3espv.challenge.model.Usuario;
import br.com.fiap3espv.challenge.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioCadastroResponseDTO cadastrarUsuario(UsuarioCadastroDTO usuarioCadastroDTO) {
        Usuario usuario = new Usuario(usuarioCadastroDTO);
        usuarioRepository.save(usuario);
        log.info("Usuário cadastrado - Nome: {}, Role: {}, Id: ({})", usuario.getNomeUsuario(), usuario.getRole(), usuario.getId());

        return new UsuarioCadastroResponseDTO(usuario);
    }

    public Page<UsuarioListagemDTO> listarUsuarios(Pageable pageable) {
        log.info("Tabela de usuários acessada!");
        return usuarioRepository.findAll(pageable).map(UsuarioListagemDTO::new);
    }

    public void atualizarUsuario(UsuarioAtualizacaoDTO usuarioAtualizacaoDTO, Long id) {
        Usuario usuario = procurarUsuarioPorId(id);
        usuario.atualizarDados(usuarioAtualizacaoDTO);
        log.info("Usuário atualizado - Nome: {}, Role: {}, Id: ({})", usuario.getNomeUsuario(), usuario.getRole(), usuario.getId());
    }

    public void excluirUsuario(Long id) {
        Usuario usuario = procurarUsuarioPorId(id);
        log.info("Usuário excluido - Nome: {}, Role: {}, Id: ({})", usuario.getNomeUsuario(), usuario.getRole(), usuario.getId());
        usuarioRepository.delete(usuario);
    }

    private Usuario procurarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Usuario não encontrado"));
    }

    public Optional<Usuario> procurarUsuarioPorNomeDeUsuario(String nomeUsuario) {
        return usuarioRepository.findByNomeUsuario(nomeUsuario);
    }
}
