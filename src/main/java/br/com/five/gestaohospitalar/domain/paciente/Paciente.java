package br.com.five.gestaohospitalar.domain.paciente;

import br.com.five.gestaohospitalar.domain.atendimento.Atendimento;
import br.com.five.gestaohospitalar.domain.dadopessoal.DadoPessoal;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pacientes")
@Schema(description = "Representa um paciente")
public class Paciente extends DadoPessoal {

  @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY)
  @Schema(description = "Lista de atendimentos do paciente")
  private List<Atendimento> atendimentos = new ArrayList<>();

  public List<Atendimento> getAtendimentos() {
    return atendimentos;
  }

  public void setAtendimentos(List<Atendimento> atendimentos) {
    this.atendimentos = atendimentos;
  }
}
