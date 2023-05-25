package br.com.five.gestaohospitalar.domain.atendimento;

import br.com.five.gestaohospitalar.domain.atendimento.payload.request.AtendimentoPostRequest;
import br.com.five.gestaohospitalar.domain.atendimento.payload.request.AtendimentoPutRequest;
import br.com.five.gestaohospitalar.domain.atendimento.payload.response.AtendimentoResponse;
import br.com.five.gestaohospitalar.domain.medico.Medico;
import br.com.five.gestaohospitalar.domain.paciente.Paciente;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AtendimentoMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "statusAtendimento", ignore = true)
  Atendimento mapPostRequestToAtendimento(
    AtendimentoPostRequest atendimentoPostRequest,
    Medico medico,
    Paciente paciente
  );

  @Mapping(target = "nomeMedico", source = "medico.nome")
  @Mapping(target = "nomePaciente", source = "paciente.nome")
  AtendimentoResponse mapAtendimentoToResponse(Atendimento atendimento);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "statusAtendimento", ignore = true)
  @Mapping(target = "medico", ignore = true)
  @Mapping(target = "paciente", ignore = true)
  Atendimento mapPutRequestToAtendimento(
    AtendimentoPutRequest atendimentoPutRequest,
    @MappingTarget Atendimento atendimento
  );

  List<AtendimentoResponse> mapAtendimentoListToResponseList(
    List<Atendimento> atendimentos
  );
}
