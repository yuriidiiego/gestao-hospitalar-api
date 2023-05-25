package br.com.five.gestaohospitalar.domain.dadopessoal;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DadoPessoal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  @Schema(description = "ID do dado pessoal", example = "1")
  private Long id;

  @Column(name = "nome", nullable = false, length = 50)
  @Schema(description = "Nome do indivíduo", example = "João da Silva")
  private String nome;

  @Column(name = "cpf", nullable = false, length = 11)
  @Schema(description = "CPF do indivíduo", example = "12345678900")
  private String cpf;

  @Column(name = "data_nascimento")
  @Schema(
    description = "Data de nascimento do indivíduo",
    example = "2000-01-01"
  )
  private LocalDate dataNascimento;

  @Column(name = "sexo", nullable = false)
  @Enumerated(EnumType.STRING)
  @Schema(description = "Sexo do indivíduo", example = "MASCULINO")
  private Sexo sexo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public LocalDate getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(LocalDate dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public Sexo getSexo() {
    return sexo;
  }

  public void setSexo(Sexo sexo) {
    this.sexo = sexo;
  }
}
