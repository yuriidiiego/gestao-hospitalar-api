package br.com.five.gestaohospitalar.domain.atendimento;

import br.com.five.gestaohospitalar.domain.medico.Medico;
import br.com.five.gestaohospitalar.domain.paciente.Paciente;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "atendimentos")
@Schema(description = "Representa um atendimento")
public class Atendimento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "ID do atendimento")
  private Long id;

  @Column(name = "data_atendimento")
  @Schema(description = "Data do atendimento", example = "2023-05-24")
  private LocalDate dataAtendimento;

  @ManyToOne
  @Schema(description = "Médico responsável pelo atendimento")
  private Medico medico;

  @ManyToOne
  @Schema(description = "Paciente atendido")
  private Paciente paciente;

  @Column(name = "observacao", nullable = false, length = 100)
  @Schema(
    description = "Observações do atendimento",
    example = "Paciente apresentando febre e dor de garganta."
  )
  private String observacao;

  @Column(name = "status_atendimento", nullable = false)
  @Enumerated(EnumType.STRING)
  @Schema(description = "Status do atendimento", example = "ATIVO")
  private StatusAtendimento statusAtendimento;

  public Atendimento(
    LocalDate dataAtendimento,
    Medico medico,
    String observacao,
    Paciente paciente
  ) {
    this.dataAtendimento = dataAtendimento;
    this.medico = medico;
    this.observacao = observacao;
    this.paciente = paciente;
    this.statusAtendimento = StatusAtendimento.ATIVO;
  }

  public Atendimento() {
    this.statusAtendimento = StatusAtendimento.ATIVO;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDataAtendimento() {
    return dataAtendimento;
  }

  public void setDataAtendimento(LocalDate dataAtendimento) {
    this.dataAtendimento = dataAtendimento;
  }

  public Medico getMedico() {
    return medico;
  }

  public void setMedico(Medico medico) {
    this.medico = medico;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }

  public Paciente getPaciente() {
    return paciente;
  }

  public void setPaciente(Paciente paciente) {
    this.paciente = paciente;
  }

  public StatusAtendimento getStatusAtendimento() {
    return statusAtendimento;
  }

  public void setStatusAtendimento(StatusAtendimento statusAtendimento) {
    this.statusAtendimento = statusAtendimento;
  }
}
