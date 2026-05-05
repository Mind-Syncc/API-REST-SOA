package br.com.fiap3espv.challenge.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Clientes")
@Getter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String cidade;
    private String estado;
    private boolean ativo;

    public Cliente () {

    }
}
