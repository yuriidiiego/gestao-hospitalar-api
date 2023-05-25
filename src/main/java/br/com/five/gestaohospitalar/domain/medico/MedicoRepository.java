package br.com.five.gestaohospitalar.domain.medico;

import br.com.five.gestaohospitalar.domain.dadopessoal.DadoPessoalRepository;
import br.com.five.gestaohospitalar.domain.paciente.Paciente;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicoRepository extends DadoPessoalRepository<Medico> {
  boolean existsByCrm(String crm);

  @Query(
    "SELECT m FROM Medico m JOIN m.atendimentos a WHERE a.dataAtendimento BETWEEN :dataInicio AND :dataFim GROUP BY m.id"
  )
  List<Medico> buscaMedicosPorPeriodoAtendimento(
    @Param("dataInicio") LocalDate dataInicio,
    @Param("dataFim") LocalDate dataFim
  );

  @Query(
    "SELECT m FROM Medico m JOIN m.atendimentos a WHERE a.paciente = :paciente"
  )
  List<Medico> buscaMedicosDoPaciente(@Param("paciente") Paciente paciente);
}
