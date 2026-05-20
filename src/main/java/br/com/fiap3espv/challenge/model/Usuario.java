package br.com.fiap3espv.challenge.model;

import br.com.fiap3espv.challenge.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeUsuario;
    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Usuario () {

    }
}
