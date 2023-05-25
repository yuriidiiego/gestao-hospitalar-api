package br.com.five.gestaohospitalar.domain.usuario;

import br.com.five.gestaohospitalar.config.security.jwt.JwtService;
import br.com.five.gestaohospitalar.domain.usuario.payload.request.UsuarioCadastroRequest;
import br.com.five.gestaohospitalar.domain.usuario.payload.request.UsuarioLoginRequest;
import br.com.five.gestaohospitalar.domain.usuario.payload.response.MensagemResponse;
import br.com.five.gestaohospitalar.domain.usuario.payload.response.UsuarioInfoResponse;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

  private static final String PERFIL_NOT_FOUND = "Perfil não encontrado";

  private final AuthenticationManager authenticationManager;
  private final UsuarioRepository usuarioRepository;
  private final PerfilRepository perfilRepository;
  private final JwtService jwtService;
  private final PasswordEncoder encoder;

  public UsuarioServiceImpl(
    AuthenticationManager authenticationManager,
    UsuarioRepository usuarioRepository,
    PerfilRepository perfilRepository,
    JwtService jwtService,
    PasswordEncoder encoder
  ) {
    this.authenticationManager = authenticationManager;
    this.usuarioRepository = usuarioRepository;
    this.perfilRepository = perfilRepository;
    this.jwtService = jwtService;
    this.encoder = encoder;
  }

  @Override
  public ResponseEntity<UsuarioInfoResponse> login(
    UsuarioLoginRequest loginRequest
  ) {
    Authentication authentication = autenticarUsuario(loginRequest);
    UsuarioDetailsImpl usuarioDetails = obterDetalhesDoUsuario(authentication);
    ResponseCookie jwtCookie = criarCookieJwt(usuarioDetails);
    UsuarioInfoResponse userInfoResponse = criarRespostaDeInformacoesDoUsuario(
      usuarioDetails,
      jwtCookie.getValue()
    );

    return ResponseEntity
      .ok()
      .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
      .body(userInfoResponse);
  }

  @Override
  public ResponseEntity<MensagemResponse> cadastro(
    UsuarioCadastroRequest cadastroRequest
  ) {
    Usuario usuario = createUsuarioFromCadastroRequest(cadastroRequest);
    Set<Perfil> perfis = getPerfisFromCadastroRequest(cadastroRequest);
    usuario.setPerfis(perfis);
    usuarioRepository.save(usuario);

    return ResponseEntity
      .created(URI.create("/usuarios/" + usuario.getId()))
      .body(new MensagemResponse("Usuário cadastrado com sucesso!"));
  }

  @Override
  public ResponseEntity<MensagemResponse> logout() {
    ResponseEntity<ResponseCookie> jwtCookie = jwtService.limparCookieDoToken();
    HttpHeaders headers = new HttpHeaders();
    headers.add(
      HttpHeaders.SET_COOKIE,
      jwtCookie.getHeaders().getFirst(HttpHeaders.SET_COOKIE)
    );

    return ResponseEntity
      .ok()
      .headers(headers)
      .body(new MensagemResponse("Usuário deslogado com sucesso!"));
  }

  private Authentication autenticarUsuario(UsuarioLoginRequest loginRequest) {
    return authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginRequest.getNomeUsuario(),
        loginRequest.getSenha()
      )
    );
  }

  private UsuarioDetailsImpl obterDetalhesDoUsuario(
    Authentication authentication
  ) {
    return (UsuarioDetailsImpl) authentication.getPrincipal();
  }

  private ResponseCookie criarCookieJwt(UsuarioDetailsImpl usuarioDetails) {
    return jwtService.criarCookieComToken(usuarioDetails);
  }

  private UsuarioInfoResponse criarRespostaDeInformacoesDoUsuario(
    UsuarioDetailsImpl usuarioDetails,
    String jwtCookieValue
  ) {
    List<String> perfis = getPerfisAsString(usuarioDetails);
    return new UsuarioInfoResponse(
      usuarioDetails.getId(),
      usuarioDetails.getUsername(),
      usuarioDetails.getEmail(),
      jwtCookieValue,
      perfis
    );
  }

  private List<String> getPerfisAsString(UsuarioDetailsImpl usuarioDetails) {
    return usuarioDetails
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toList());
  }

  private Usuario createUsuarioFromCadastroRequest(
    UsuarioCadastroRequest cadastroRequest
  ) {
    String senhaCriptografada = encoder.encode(cadastroRequest.getSenha());
    return new Usuario(
      cadastroRequest.getNomeUsuario(),
      cadastroRequest.getEmail(),
      senhaCriptografada
    );
  }

  private Set<Perfil> getPerfisFromCadastroRequest(
    UsuarioCadastroRequest cadastroRequest
  ) {
    Set<String> strPerfis = cadastroRequest.getPerfil();
    Set<Perfil> perfis = new HashSet<>();

    if (strPerfis == null) {
      Perfil usuarioPerfil = getPerfilByNome(TipoPerfil.ROLE_USER);
      perfis.add(usuarioPerfil);
    } else {
      strPerfis.forEach(perfil -> {
        switch (perfil) {
          case "admin":
            Perfil adminPerfil = getPerfilByNome(TipoPerfil.ROLE_ADMIN);
            perfis.add(adminPerfil);
            break;
          case "mod":
            Perfil modPerfil = getPerfilByNome(TipoPerfil.ROLE_MODERATOR);
            perfis.add(modPerfil);
            break;
          default:
            Perfil usuarioPerfil = getPerfilByNome(TipoPerfil.ROLE_USER);
            perfis.add(usuarioPerfil);
        }
      });
    }

    return perfis;
  }

  private Perfil getPerfilByNome(TipoPerfil tipoPerfil) {
    return perfilRepository
      .findByNome(tipoPerfil)
      .orElseThrow(() -> new RuntimeException(PERFIL_NOT_FOUND));
  }
}
