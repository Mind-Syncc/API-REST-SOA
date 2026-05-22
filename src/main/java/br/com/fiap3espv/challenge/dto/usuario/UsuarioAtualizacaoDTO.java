package br.com.fiap3espv.challenge.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record UsuarioAtualizacaoDTO (@NotBlank(message = "O campo nomeUsuario precisa ser preenchido") String nomeUsuario,
                                     @NotBlank(message = "O campo senha precisa ser preenchido") String senha) {
}
