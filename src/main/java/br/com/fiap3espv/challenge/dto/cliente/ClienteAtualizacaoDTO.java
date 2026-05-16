package br.com.fiap3espv.challenge.dto.cliente;

import jakarta.validation.constraints.Email;

public record ClienteAtualizacaoDTO (
         String nome,
         String cpf,
         String telefone,
         @Email(message = "O campo email precisa ser válido")
         String email,
         String cidade,
         String estado) {
}
