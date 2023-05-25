package br.com.five.gestaohospitalar.config.security.jwt;

import br.com.five.gestaohospitalar.domain.usuario.UsuarioDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.Duration;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

  private static final Logger logger = LoggerFactory.getLogger(
    JwtService.class
  );

  @Value("${app.jwtSecret}")
  private String jwtSecret;

  @Value("${app.jwtExpirationMs}")
  private long jwtExpirationMs;

  @Value("${app.jwtCookieName}")
  private String jwtCookieName;

  public String obterTokenDoCookie(HttpServletRequest request) {
    Cookie cookie = obterCookie(request, jwtCookieName);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return null;
    }
  }

  public ResponseCookie criarCookieComToken(UsuarioDetailsImpl userDetails) {
    String jwt = gerarToken(userDetails.getUsername());
    return ResponseCookie
      .from(jwtCookieName, jwt)
      .path("/")
      .maxAge(Duration.ofMillis(jwtExpirationMs))
      .httpOnly(true)
      .build();
  }

  public ResponseEntity<ResponseCookie> limparCookieDoToken() {
    ResponseCookie responseCookie = ResponseCookie
      .from(jwtCookieName, "")
      .path("/")
      .maxAge(0)
      .httpOnly(true)
      .build();

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());

    return ResponseEntity.noContent().headers(headers).build();
  }

  public String obterNomeDeUsuarioDoToken(String token) {
    Jws<Claims> claims = Jwts
      .parser()
      .setSigningKey(jwtSecret)
      .parseClaimsJws(token);

    return claims.getBody().getSubject();
  }

  public boolean validarToken(String token) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    } catch (
      SignatureException
      | MalformedJwtException
      | ExpiredJwtException
      | UnsupportedJwtException
      | IllegalArgumentException e
    ) {
      logger.error("Token JWT inválido: {}", e.getMessage());
      return false;
    }
  }

  private Cookie obterCookie(HttpServletRequest request, String nome) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (nome.equals(cookie.getName())) {
          return cookie;
        }
      }
    }
    return null;
  }

  private String gerarToken(String nomeDeUsuario) {
    JwtBuilder builder = Jwts
      .builder()
      .setSubject(nomeDeUsuario)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
      .signWith(SignatureAlgorithm.HS512, jwtSecret);

    return builder.compact();
  }
}
