package br.com.five.gestaohospitalar.domain.paciente;

import br.com.five.gestaohospitalar.domain.paciente.payload.request.PacientePatchRequest;
import br.com.five.gestaohospitalar.domain.paciente.payload.request.PacientePostRequest;
import br.com.five.gestaohospitalar.domain.paciente.payload.request.PacientePutRequest;
import br.com.five.gestaohospitalar.domain.paciente.payload.response.PacienteResponse;
import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface IPacienteService {
  PacienteResponse salvar(PacientePostRequest pacientePostDTO);

  PacienteResponse buscarPorId(Long id);

  List<PacienteResponse> buscarTodos();

  void deletar(Long id);

  PacienteResponse atualizar(Long id, PacientePutRequest pacientePutDTO);

  PacienteResponse atualizarParcialmente(
    Long id,
    PacientePatchRequest pacientePatchDTO
  );

  List<PacienteResponse> listaPacientePorMedicoID(Long id);

  ResponseEntity<InputStreamResource> gerarPdfPacientes();
}
