package br.com.five.gestaohospitalar.config.validacao.usuario;

import br.com.five.gestaohospitalar.domain.usuario.UsuarioRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueNomeUsuarioValidator
  implements ConstraintValidator<UniqueNomeUsuario, String> {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public boolean isValid(
    String nomeUsuario,
    ConstraintValidatorContext context
  ) {
    return !usuarioRepository.existsByNomeUsuario(nomeUsuario);
  }
}
