package br.com.five.gestaohospitalar.domain.usuario;

import br.com.five.gestaohospitalar.domain.usuario.payload.request.UsuarioCadastroRequest;
import br.com.five.gestaohospitalar.domain.usuario.payload.request.UsuarioLoginRequest;
import br.com.five.gestaohospitalar.domain.usuario.payload.response.MensagemResponse;
import br.com.five.gestaohospitalar.domain.usuario.payload.response.UsuarioInfoResponse;
import org.springframework.http.ResponseEntity;

public interface IUsuarioService {
  ResponseEntity<UsuarioInfoResponse> login(
    UsuarioLoginRequest usuarioLoginRequest
  );

  ResponseEntity<MensagemResponse> cadastro(
    UsuarioCadastroRequest usuarioCadastroRequest
  );

  ResponseEntity<MensagemResponse> logout();
}
