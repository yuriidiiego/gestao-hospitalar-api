package br.com.five.gestaohospitalar.config.validacao.crm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CRMValidator implements ConstraintValidator<CRM, String> {

  private static final String CRM_PATTERN = "^[\\d]{6}\\/[A-Z]{2}$";

  @Override
  public boolean isValid(String crm, ConstraintValidatorContext context) {
    return crm == null || validate(crm);
  }

  private boolean validate(String crm) {
    Pattern pattern = Pattern.compile(CRM_PATTERN);
    Matcher matcher = pattern.matcher(crm);
    return matcher.matches();
  }
}
