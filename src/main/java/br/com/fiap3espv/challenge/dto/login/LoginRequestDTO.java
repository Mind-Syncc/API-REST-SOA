package br.com.fiap3espv.challenge.dto.login;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(@NotBlank (message = "O campo nomeUsuario precisa ser preenchido") String nomeUsuario,
                              @NotBlank(message = "O campo senha precisa ser preenchido") String senha) {
}
