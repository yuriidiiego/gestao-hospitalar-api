package br.com.five.gestaohospitalar.domain.medico;

import br.com.five.gestaohospitalar.domain.atendimento.AtendimentoRepository;
import br.com.five.gestaohospitalar.domain.medico.payload.request.MedicoPatchRequest;
import br.com.five.gestaohospitalar.domain.medico.payload.request.MedicoPostRequest;
import br.com.five.gestaohospitalar.domain.medico.payload.request.MedicoPutRequest;
import br.com.five.gestaohospitalar.domain.medico.payload.response.MedicoResponse;
import br.com.five.gestaohospitalar.domain.paciente.PacienteRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MedicoServiceImpl implements IMedicoService {

  @Autowired
  private MedicoMapper medicoMapper;

  @Autowired
  private MedicoRepository medicoRepository;

  @Autowired
  private PacienteRepository pacienteRepository;

  @Autowired
  private AtendimentoRepository atendimentoRepository;

  @Override
  public MedicoResponse salvar(MedicoPostRequest medicoPostDTO) {
    return medicoMapper.mapMedicoToResponse(
      medicoRepository.save(medicoMapper.mapPostRequestToMedico(medicoPostDTO))
    );
  }

  @Override
  public MedicoResponse buscarPorId(Long id) {
    return medicoMapper.mapMedicoToResponse(buscaMedicoPorIdOrElseThrow(id));
  }

  @Override
  public List<MedicoResponse> buscarTodos() {
    return medicoMapper.mapMedicoListToResponseList(medicoRepository.findAll());
  }

  @Override
  public void deletar(Long id) {
    if (verificaSeMedicoPossuiAtendimento(id)) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Médico não pode ser deletado pois está vinculado a um atendimento"
      );
    }
    medicoRepository.delete(buscaMedicoPorIdOrElseThrow(id));
  }

  @Override
  public MedicoResponse atualizar(Long id, MedicoPutRequest medicoPutDTO) {
    Medico medico = buscaMedicoPorIdOrElseThrow(id);
    medicoMapper.mapPutRequestToMedico(medicoPutDTO, medico);
    return medicoMapper.mapMedicoToResponse(medicoRepository.save(medico));
  }

  @Override
  public MedicoResponse atualizarParcialmente(
    Long id,
    MedicoPatchRequest medicoPatchDTO
  ) {
    Medico medico = buscaMedicoPorIdOrElseThrow(id);
    medicoMapper.mapPatchRequestToMedico(medicoPatchDTO, medico);
    return medicoMapper.mapMedicoToResponse(medicoRepository.save(medico));
  }

  @Override
  public List<MedicoResponse> listaMedicosPorPeriodo(
    LocalDate dataInicio,
    LocalDate dataFim
  ) {
    List<Medico> medicos = medicoRepository.buscaMedicosPorPeriodoAtendimento(
      dataInicio,
      dataFim
    );
    return medicoMapper.mapMedicoListToResponseList(medicos);
  }

  @Override
  public List<MedicoResponse> listaMedicosPorPacienteID(Long id) {
    List<Medico> medicos = buscaPacienteNoBancoOrElseThrow(id);
    return medicoMapper.mapMedicoListToResponseList(medicos);
  }

  private List<Medico> buscaPacienteNoBancoOrElseThrow(Long id) {
    return medicoRepository.buscaMedicosDoPaciente(
      pacienteRepository
        .findById(id)
        .orElseThrow(() ->
          new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Paciente não encontrado"
          )
        )
    );
  }

  private Medico buscaMedicoPorIdOrElseThrow(Long id) {
    return medicoRepository
      .findById(id)
      .orElseThrow(() ->
        new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Médico não encontrado"
        )
      );
  }

  private boolean verificaSeMedicoPossuiAtendimento(Long id) {
    return atendimentoRepository.existsByMedicoId(id);
  }
}
