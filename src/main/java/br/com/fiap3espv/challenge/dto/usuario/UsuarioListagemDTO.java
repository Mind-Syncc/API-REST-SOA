package br.com.fiap3espv.challenge.dto.usuario;

import br.com.fiap3espv.challenge.model.Usuario;
import br.com.fiap3espv.challenge.model.enums.Role;

public record UsuarioListagemDTO (String nomeUsuario,
                                  Role role) {
    public UsuarioListagemDTO(Usuario usuario) {
        this(usuario.getNomeUsuario(), usuario.getRole());
    }
}
