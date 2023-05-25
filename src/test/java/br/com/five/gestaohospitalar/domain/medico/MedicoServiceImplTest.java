package br.com.five.gestaohospitalar.domain.medico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.five.gestaohospitalar.domain.atendimento.AtendimentoRepository;
import br.com.five.gestaohospitalar.domain.medico.payload.request.MedicoPatchRequest;
import br.com.five.gestaohospitalar.domain.medico.payload.request.MedicoPostRequest;
import br.com.five.gestaohospitalar.domain.medico.payload.request.MedicoPutRequest;
import br.com.five.gestaohospitalar.domain.medico.payload.response.MedicoResponse;
import br.com.five.gestaohospitalar.domain.paciente.Paciente;
import br.com.five.gestaohospitalar.domain.paciente.PacienteRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
@DisplayName("MedicoServiceImpl Test")
class MedicoServiceImplTest {

  @Mock
  private MedicoMapper medicoMapper;

  @Mock
  private MedicoRepository medicoRepository;

  @Mock
  private PacienteRepository pacienteRepository;

  @Mock
  private AtendimentoRepository atendimentoRepository;

  @InjectMocks
  private MedicoServiceImpl medicoService;

  @Test
  @DisplayName("Salvar Médico")
  void testSalvarMedico() {
    // Crie os objetos de teste
    MedicoPostRequest medicoPostRequest = new MedicoPostRequest();
    Medico medico = new Medico();
    MedicoResponse medicoResponse = new MedicoResponse();

    // Defina os comportamentos esperados dos mocks
    when(medicoMapper.mapPostRequestToMedico(medicoPostRequest)).thenReturn(medico);
    when(medicoRepository.save(medico)).thenReturn(medico);
    when(medicoMapper.mapMedicoToResponse(medico)).thenReturn(medicoResponse);

    // Execute o método a ser testado
    MedicoResponse result = medicoService.salvar(medicoPostRequest);

    // Verifique os resultados
    assertEquals(medicoResponse, result);
    verify(medicoMapper).mapPostRequestToMedico(medicoPostRequest);
    verify(medicoRepository).save(medico);
    verify(medicoMapper).mapMedicoToResponse(medico);
  }

  @Test
  @DisplayName("Buscar Médico por ID - Médico encontrado")
  void testBuscarMedicoPorId_MedicoEncontrado() {
    // Crie os objetos de teste
    Long medicoId = 1L;
    Medico medico = new Medico();
    MedicoResponse medicoResponse = new MedicoResponse();

    // Defina os comportamentos esperados dos mocks
    when(medicoRepository.findById(medicoId)).thenReturn(Optional.of(medico));
    when(medicoMapper.mapMedicoToResponse(medico)).thenReturn(medicoResponse);

    // Execute o método a ser testado
    MedicoResponse result = medicoService.buscarPorId(medicoId);

    // Verifique os resultados
    assertEquals(medicoResponse, result);
    verify(medicoRepository).findById(medicoId);
    verify(medicoMapper).mapMedicoToResponse(medico);
  }

  @Test
  @DisplayName("Buscar Médico por ID - Médico não encontrado")
  void testBuscarMedicoPorId_MedicoNaoEncontrado() {
    // Crie os objetos de teste
    Long medicoId = 1L;

    // Defina os comportamentos esperados dos mocks
    when(medicoRepository.findById(medicoId)).thenReturn(Optional.empty());

    // Execute o método a ser testado e verifique se a exceção é lançada
    assertThrows(
      ResponseStatusException.class,
      () -> medicoService.buscarPorId(medicoId),
      "Médico não encontrado"
    );

    verify(medicoRepository).findById(medicoId);
  }

  @Test
  @DisplayName("Buscar Todos os Médicos")
  void testBuscarTodosMedicos() {
    // Crie os objetos de teste
    List<Medico> medicos = new ArrayList<>();
    medicos.add(new Medico());
    medicos.add(new Medico());

    List<MedicoResponse> medicoResponses = new ArrayList<>();
    medicoResponses.add(new MedicoResponse());
    medicoResponses.add(new MedicoResponse());

    // Defina os comportamentos esperados dos mocks
    when(medicoRepository.findAll()).thenReturn(medicos);
    when(medicoMapper.mapMedicoListToResponseList(medicos))
      .thenReturn(medicoResponses);

    // Execute o método a ser testado
    List<MedicoResponse> result = medicoService.buscarTodos();

    // Verifique os resultados
    assertEquals(medicoResponses, result);
    verify(medicoRepository).findAll();
    verify(medicoMapper).mapMedicoListToResponseList(medicos);
  }

  @Test
  @DisplayName("Deletar Médico - Médico não possui atendimento")
  void testDeletarMedico_MedicoSemAtendimento() {
    // Crie os objetos de teste
    Long medicoId = 1L;
    Medico medico = new Medico();

    // Defina os comportamentos esperados dos mocks
    when(medicoRepository.findById(medicoId)).thenReturn(Optional.of(medico));
    when(atendimentoRepository.existsByMedicoId(medicoId)).thenReturn(false);

    // Execute o método a ser testado
    medicoService.deletar(medicoId);

    // Verifique os resultados
    verify(medicoRepository).findById(medicoId);
    verify(atendimentoRepository).existsByMedicoId(medicoId);
    verify(medicoRepository).delete(medico);
  }

  @Test
  @DisplayName("Atualizar Médico")
  void testAtualizarMedico() {
    // Crie os objetos de teste
    Long medicoId = 1L;
    MedicoPutRequest medicoPutRequest = new MedicoPutRequest();
    Medico medico = new Medico();
    MedicoResponse medicoResponse = new MedicoResponse();

    // Defina os comportamentos esperados dos mocks
    when(medicoRepository.findById(medicoId)).thenReturn(Optional.of(medico));
    when(medicoMapper.mapPutRequestToMedico(medicoPutRequest, medico)).thenReturn(medico);
    when(medicoRepository.save(medico)).thenReturn(medico);
    when(medicoMapper.mapMedicoToResponse(medico)).thenReturn(medicoResponse);

    // Execute o método a ser testado
    MedicoResponse result = medicoService.atualizar(medicoId, medicoPutRequest);

    // Verifique os resultados
    assertEquals(medicoResponse, result);
    verify(medicoRepository).findById(medicoId);
    verify(medicoMapper).mapPutRequestToMedico(medicoPutRequest, medico);
    verify(medicoRepository).save(medico);
    verify(medicoMapper).mapMedicoToResponse(medico);
  }

  @Test
  @DisplayName("Atualizar Parcialmente Médico")
  void testAtualizarParcialmenteMedico() {
    // Crie os objetos de teste
    Long medicoId = 1L;
    MedicoPatchRequest medicoPatchRequest = new MedicoPatchRequest();
    Medico medico = new Medico();
    MedicoResponse medicoResponse = new MedicoResponse();

    // Defina os comportamentos esperados dos mocks
    when(medicoRepository.findById(medicoId)).thenReturn(Optional.of(medico));
    when(medicoMapper.mapPatchRequestToMedico(medicoPatchRequest, medico)).thenReturn(medico);
    when(medicoRepository.save(medico)).thenReturn(medico);
    when(medicoMapper.mapMedicoToResponse(medico)).thenReturn(medicoResponse);

    // Execute o método a ser testado
    MedicoResponse result = medicoService.atualizarParcialmente(
      medicoId,
      medicoPatchRequest
    );

    // Verifique os resultados
    assertEquals(medicoResponse, result);
    verify(medicoRepository).findById(medicoId);
    verify(medicoMapper).mapPatchRequestToMedico(medicoPatchRequest, medico);
    verify(medicoRepository).save(medico);
    verify(medicoMapper).mapMedicoToResponse(medico);
  }

  @Test
  @DisplayName("Lista Médicos por Período")
  void testListaMedicosPorPeriodo() {
    // Crie os objetos de teste
    LocalDate dataInicio = LocalDate.now();
    LocalDate dataFim = LocalDate.now().plusDays(7);
    List<Medico> medicos = new ArrayList<>();
    medicos.add(new Medico());
    medicos.add(new Medico());

    List<MedicoResponse> medicoResponses = new ArrayList<>();
    medicoResponses.add(new MedicoResponse());
    medicoResponses.add(new MedicoResponse());

    // Defina os comportamentos esperados dos mocks
    when(
      medicoRepository.buscaMedicosPorPeriodoAtendimento(dataInicio, dataFim)
    )
      .thenReturn(medicos);
    when(medicoMapper.mapMedicoListToResponseList(medicos))
      .thenReturn(medicoResponses);

    // Execute o método a ser testado
    List<MedicoResponse> result = medicoService.listaMedicosPorPeriodo(
      dataInicio,
      dataFim
    );

    // Verifique os resultados
    assertEquals(medicoResponses, result);
    verify(medicoRepository)
      .buscaMedicosPorPeriodoAtendimento(dataInicio, dataFim);
    verify(medicoMapper).mapMedicoListToResponseList(medicos);
  }

  @Test
  @DisplayName("Lista Médicos por ID do Paciente")
  void testListaMedicosPorPacienteID() {
    // Crie os objetos de teste
    Long pacienteId = 1L;
    List<Medico> medicos = new ArrayList<>();
    medicos.add(new Medico());
    medicos.add(new Medico());

    List<MedicoResponse> medicoResponses = new ArrayList<>();
    medicoResponses.add(new MedicoResponse());
    medicoResponses.add(new MedicoResponse());

    // Defina os comportamentos esperados dos mocks
    when(pacienteRepository.findById(pacienteId))
      .thenReturn(Optional.of(new Paciente()));
    when(medicoRepository.buscaMedicosDoPaciente(any())).thenReturn(medicos);
    when(medicoMapper.mapMedicoListToResponseList(medicos))
      .thenReturn(medicoResponses);

    // Execute o método a ser testado
    List<MedicoResponse> result = medicoService.listaMedicosPorPacienteID(
      pacienteId
    );

    // Verifique os resultados
    assertEquals(medicoResponses, result);
    verify(pacienteRepository).findById(pacienteId);
    verify(medicoRepository).buscaMedicosDoPaciente(any());
    verify(medicoMapper).mapMedicoListToResponseList(medicos);
  }

  @Test
  @DisplayName("Lista Médicos por ID do Paciente - Paciente não encontrado")
  void testListaMedicosPorPacienteID_PacienteNaoEncontrado() {
    // Crie os objetos de teste
    Long pacienteId = 1L;

    // Defina os comportamentos esperados dos mocks
    when(pacienteRepository.findById(pacienteId)).thenReturn(Optional.empty());

    // Execute o método a ser testado e verifique se a exceção é lançada
    assertThrows(
      ResponseStatusException.class,
      () -> medicoService.listaMedicosPorPacienteID(pacienteId),
      "Paciente não encontrado"
    );

    verify(pacienteRepository).findById(pacienteId);
  }
}
