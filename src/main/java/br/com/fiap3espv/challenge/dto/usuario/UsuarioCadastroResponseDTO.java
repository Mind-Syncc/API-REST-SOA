package br.com.fiap3espv.challenge.dto.usuario;

import br.com.fiap3espv.challenge.model.Usuario;
import br.com.fiap3espv.challenge.model.enums.Role;

public record UsuarioCadastroResponseDTO (
        Long id,
        String nomeUsuario,
        Role role) {

    public UsuarioCadastroResponseDTO (Usuario usuario) {
        this(usuario.getId(), usuario.getNomeUsuario(), usuario.getRole());
    }
}
