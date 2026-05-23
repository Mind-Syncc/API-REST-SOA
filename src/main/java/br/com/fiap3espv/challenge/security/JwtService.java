package br.com.fiap3espv.challenge.security;

import br.com.fiap3espv.challenge.model.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    private Key getChaveDeEntrada() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String gerarToken(String nomeUsuario, Role role) {
        return Jwts.builder()
                .setClaims(Map.of("role", role))
                .setSubject(nomeUsuario)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getChaveDeEntrada(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getChaveDeEntrada())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    public String extrairNomeUsuario(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    public boolean tokenExpirado(String token) {
        return extrairClaim(token, Claims::getExpiration).before(new Date());
    }

    public boolean validarToken(String token, String nomeUsuario) {
       final String subject = extrairNomeUsuario(token);
       return subject.equals(nomeUsuario) && !tokenExpirado(token);
    }

}
