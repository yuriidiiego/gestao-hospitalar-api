package br.com.five.gestaohospitalar.domain.medico;

import br.com.five.gestaohospitalar.domain.medico.payload.request.MedicoPatchRequest;
import br.com.five.gestaohospitalar.domain.medico.payload.request.MedicoPostRequest;
import br.com.five.gestaohospitalar.domain.medico.payload.request.MedicoPutRequest;
import br.com.five.gestaohospitalar.domain.medico.payload.response.MedicoResponse;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MedicoMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "atendimentos", ignore = true)
  Medico mapPostRequestToMedico(MedicoPostRequest medicoPostRequest);

  MedicoResponse mapMedicoToResponse(Medico medico);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "atendimentos", ignore = true)
  Medico mapPutRequestToMedico(MedicoPutRequest medicoPutRequest);

  @BeanMapping(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
  )
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "atendimentos", ignore = true)
  Medico mapPatchRequestToMedico(
    MedicoPatchRequest medicoPatchRequest,
    @MappingTarget Medico medico
  );

  List<MedicoResponse> mapMedicoListToResponseList(List<Medico> medicos);

  @BeanMapping(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
  )
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "atendimentos", ignore = true)
  Medico mapPutRequestToMedico(
    MedicoPutRequest medicoPutRequest,
    @MappingTarget Medico medico
  );
}
