package br.com.five.gestaohospitalar.config.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ValidationErrorHandler extends ResponseEntityExceptionHandler {

  private final MessageSource messageSource;

  public ValidationErrorHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(
    BadCredentialsException ex,
    HttpServletRequest request
  ) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.UNAUTHORIZED.value(),
      "Usuário ou senha inválidos"
    );
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthenticationException(
    AuthenticationException ex,
    HttpServletRequest request
  ) {
    ErrorResponse errorResponse = new ErrorResponse(
      HttpStatus.UNAUTHORIZED.value(),
      "Você não tem permissão para acessar esse recurso"
    );
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
    TypeMismatchException ex,
    HttpHeaders headers,
    HttpStatus status,
    org.springframework.web.context.request.WebRequest request
  ) {
    int statusCode = HttpStatus.BAD_REQUEST.value();
    String message =
      "O parâmetro '" +
      ex.getPropertyName() +
      "' deve ser do tipo " +
      ex.getRequiredType().getSimpleName();
    ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex,
    HttpHeaders headers,
    HttpStatus status,
    org.springframework.web.context.request.WebRequest request
  ) {
    List<ErrorResponse> errorResponses = new ArrayList<>();
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    fieldErrors.forEach(fieldError -> {
      String message = messageSource.getMessage(
        fieldError,
        LocaleContextHolder.getLocale()
      );
      ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        message
      );
      errorResponses.add(errorResponse);
    });
    return ResponseEntity.badRequest().body(errorResponses);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
    HttpMessageNotReadableException ex,
    HttpHeaders headers,
    HttpStatus status,
    org.springframework.web.context.request.WebRequest request
  ) {
    if (
      ex.contains(InvalidFormatException.class) ||
      ex.contains(DateTimeParseException.class)
    ) {
      int statusCode = HttpStatus.BAD_REQUEST.value();
      String message = "Formato de data inválido. Formato aceito: dd/MM/yyyy";
      ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
      return ResponseEntity.badRequest().body(errorResponse);
    }
    int statusCode = HttpStatus.BAD_REQUEST.value();
    ErrorResponse errorResponse = new ErrorResponse(
      statusCode,
      ex.getMessage()
    );
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
    MissingServletRequestParameterException ex,
    HttpHeaders headers,
    HttpStatus status,
    org.springframework.web.context.request.WebRequest request
  ) {
    int statusCode = HttpStatus.BAD_REQUEST.value();
    String message =
      "O parâmetro '" + ex.getParameterName() + "' é obrigatório";
    ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
    DataIntegrityViolationException ex
  ) {
    int statusCode = HttpStatus.CONFLICT.value();
    String rootCause = ex.getRootCause().getMessage();
    String message = "Erro de integridade de dados: " + rootCause;
    ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponse> handleResponseStatusException(
    ResponseStatusException ex
  ) {
    int statusCode = ex.getStatus().value();
    String message = ex.getReason();
    ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
    return ResponseEntity.status(ex.getStatus()).body(errorResponse);
  }
}
