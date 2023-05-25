package br.com.five.gestaohospitalar.domain.usuario;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
  Optional<Perfil> findByNome(TipoPerfil nome);
}
