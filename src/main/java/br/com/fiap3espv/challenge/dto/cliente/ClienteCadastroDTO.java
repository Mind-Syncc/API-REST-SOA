package br.com.fiap3espv.challenge.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClienteCadastroDTO (
         @NotBlank(message = "O campo nome precisa ser preenchido")
         String nome,
         @NotBlank(message = "O campo CPF precisa ser preenchido")
         @Pattern (
                 regexp = "^\\d{11}$",
                 message = "O CPF precisa ser válido (possuir 11 digitos)"
         )
         String cpf,
         String telefone,
         @NotBlank(message = "O campo email precisa ser preenchido")
         @Email(message = "O campo email precisa ser válido")
         String email,
         @NotBlank(message = "O campo cidade precisa ser preenchido")
         String cidade,
         @NotBlank(message = "O campo estado precisa ser preenchido")
         String estado) {
}
