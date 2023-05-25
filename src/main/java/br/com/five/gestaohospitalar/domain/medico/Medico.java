package br.com.five.gestaohospitalar.domain.medico;

import br.com.five.gestaohospitalar.domain.atendimento.Atendimento;
import br.com.five.gestaohospitalar.domain.dadopessoal.DadoPessoal;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "medicos")
@Schema(description = "Representa um médico")
public class Medico extends DadoPessoal {

  @Column(name = "crm", nullable = false)
  @Schema(description = "CRM do médico", example = "12345/SP")
  private String crm;

  @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY)
  @Schema(description = "Lista de atendimentos realizados pelo médico")
  private List<Atendimento> atendimentos = new ArrayList<>();

  public String getCrm() {
    return crm;
  }

  public void setCrm(String crm) {
    this.crm = crm;
  }

  public List<Atendimento> getAtendimentos() {
    return atendimentos;
  }

  public void setAtendimentos(List<Atendimento> atendimentos) {
    this.atendimentos = atendimentos;
  }
}
