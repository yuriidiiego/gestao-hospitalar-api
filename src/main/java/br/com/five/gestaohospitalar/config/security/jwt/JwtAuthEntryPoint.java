package br.com.five.gestaohospitalar.config.security.jwt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component("jwtAuthEntryPoint")
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  private final HandlerExceptionResolver handlerExceptionResolver;

  public JwtAuthEntryPoint(
    @Qualifier(
      "handlerExceptionResolver"
    ) HandlerExceptionResolver handlerExceptionResolver
  ) {
    this.handlerExceptionResolver = handlerExceptionResolver;
  }

  @Override
  public void commence(
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException authException
  ) throws IOException, ServletException {
    handlerExceptionResolver.resolveException(
      request,
      response,
      null,
      authException
    );
  }
}
