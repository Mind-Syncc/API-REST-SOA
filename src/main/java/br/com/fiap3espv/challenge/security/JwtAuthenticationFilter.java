package br.com.fiap3espv.challenge.security;

import br.com.fiap3espv.challenge.model.Usuario;
import br.com.fiap3espv.challenge.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UsuarioService usuarioService;

    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        final String nomeUsuario = jwtService.extrairNomeUsuario(token);

        if (nomeUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Usuario> usuarioOptional = usuarioService.procurarUsuarioPorNomeDeUsuario(nomeUsuario);
            if (usuarioOptional.isPresent() && jwtService.validarToken(token, nomeUsuario)) {
                Usuario usuario = usuarioOptional.get();
                var auth = new UsernamePasswordAuthenticationToken(usuario.getNomeUsuario(),
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole())));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
