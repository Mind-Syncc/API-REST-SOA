package br.com.fiap3espv.challenge.dto.usuario;

import br.com.fiap3espv.challenge.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioCadastroDTO(@NotBlank(message = "O campo nomeUsuario precisa ser preenchido") String nomeUsuario,
                                 @NotBlank(message = "O campo senha precisa ser preenchido") String senha,
                                 @NotNull(message = "O campo role precisa ser preenchido") Role role) {
}
