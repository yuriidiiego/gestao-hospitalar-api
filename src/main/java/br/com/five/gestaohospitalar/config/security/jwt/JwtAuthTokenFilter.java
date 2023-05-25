package br.com.five.gestaohospitalar.config.security.jwt;

import br.com.five.gestaohospitalar.domain.usuario.UsuarioDetailsServiceImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthTokenFilter extends OncePerRequestFilter {

  private final Logger logger = LoggerFactory.getLogger(
    JwtAuthTokenFilter.class
  );

  @Autowired
  private JwtService jwtService;

  @Autowired
  private UsuarioDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    try {
      String jwt = extrairTokenJwtDaRequisicao(request);
      if (jwt != null && jwtService.validarToken(jwt)) {
        String username = jwtService.obterNomeDeUsuarioDoToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(
          username
        );

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );

        authenticationToken.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder
          .getContext()
          .setAuthentication(authenticationToken);
      }
    } catch (Exception e) {
      logger.error(
        "Falha ao definir o usuário autenticado: {}",
        e.getMessage()
      );
    }

    filterChain.doFilter(request, response);
  }

  private String extrairTokenJwtDaRequisicao(HttpServletRequest request) {
    return jwtService.obterTokenDoCookie(request);
  }
}
