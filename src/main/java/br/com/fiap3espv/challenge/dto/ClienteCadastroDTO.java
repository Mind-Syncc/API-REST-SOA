package br.com.fiap3espv.challenge.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClienteCadastroDTO (
         @NotBlank (message = "O campo nome é obrigatório")
         String nome,
         @NotBlank
         @Pattern (
                 regexp = "^\\d{11}$",
                 message = "CPF inválido"
         )
         String cpf,
         String telefone,
         @NotBlank
         @Email
         String email,
         @NotBlank
         String cidade,
         @NotBlank
         String estado) {
}
