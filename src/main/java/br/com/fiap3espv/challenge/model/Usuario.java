package br.com.fiap3espv.challenge.model;

import br.com.fiap3espv.challenge.dto.usuario.UsuarioAtualizacaoDTO;
import br.com.fiap3espv.challenge.dto.usuario.UsuarioCadastroDTO;
import br.com.fiap3espv.challenge.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "usuarios")
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

    public Usuario(UsuarioCadastroDTO usuarioCadastroDTO) {
        this.nomeUsuario = usuarioCadastroDTO.nomeUsuario();
        this.senha = usuarioCadastroDTO.senha();
        this.role = usuarioCadastroDTO.role();
    }

    public void atualizarDados(UsuarioAtualizacaoDTO usuarioAtualizacaoDTO) {

        if (!usuarioAtualizacaoDTO.nomeUsuario().isEmpty()) {
            this.nomeUsuario = usuarioAtualizacaoDTO.nomeUsuario();
        }

        if (!usuarioAtualizacaoDTO.senha().isEmpty()) {
            this.senha = usuarioAtualizacaoDTO.senha();
        }
    }
}
