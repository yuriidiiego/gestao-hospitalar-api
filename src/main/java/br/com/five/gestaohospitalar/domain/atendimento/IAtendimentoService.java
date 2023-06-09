package br.com.five.gestaohospitalar.domain.atendimento;

import java.time.LocalDate;
import java.util.List;

import br.com.five.gestaohospitalar.domain.atendimento.payload.request.AtendimentoPostRequest;
import br.com.five.gestaohospitalar.domain.atendimento.payload.request.AtendimentoPutRequest;
import br.com.five.gestaohospitalar.domain.atendimento.payload.response.AtendimentoResponse;

public interface IAtendimentoService {
  AtendimentoResponse salvar(AtendimentoPostRequest atendimentoPostRequest);

  AtendimentoResponse atualizar(
    Long id,
    AtendimentoPutRequest atendimentoPutRequest
  );

  List<AtendimentoResponse> buscarPorPeriodo(
    LocalDate dataInicio,
    LocalDate dataFim
  );
}
