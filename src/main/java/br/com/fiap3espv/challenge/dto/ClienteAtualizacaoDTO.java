package br.com.fiap3espv.challenge.dto;

import jakarta.validation.constraints.Email;

public record ClienteAtualizacaoDTO (
         String nome,
         String cpf,
         String telefone,
         @Email
         String email,
         String cidade,
         String estado) {
}
