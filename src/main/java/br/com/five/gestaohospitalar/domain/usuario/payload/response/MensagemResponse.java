package br.com.five.gestaohospitalar.domain.usuario.payload.response;

public class MensagemResponse {

  private String mensagem;

  public MensagemResponse(String mensagem) {
    this.mensagem = mensagem;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }
}
