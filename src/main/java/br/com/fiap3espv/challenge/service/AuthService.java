package br.com.fiap3espv.challenge.service;

import br.com.fiap3espv.challenge.dto.login.LoginRequestDTO;
import br.com.fiap3espv.challenge.dto.login.LoginResponseDTO;
import br.com.fiap3espv.challenge.exceptions.RecursoNaoEncontradoException;
import br.com.fiap3espv.challenge.model.Usuario;
import br.com.fiap3espv.challenge.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Optional<Usuario> usuarioOptional = usuarioService.procurarUsuarioPorNomeDeUsuario(loginRequestDTO.nomeUsuario());

        if (usuarioOptional.isEmpty()) {
            throw new RecursoNaoEncontradoException("Usuario não encontrado");
        }

        Usuario usuario = usuarioOptional.get();
        String token = jwtService.gerarToken(usuario.getNomeUsuario(), usuario.getRole());

        return new LoginResponseDTO(token);
    }
}
