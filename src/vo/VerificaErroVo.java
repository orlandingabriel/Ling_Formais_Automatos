package vo;

public class VerificaErroVo {
	
	private String mensagem;
	private String local;
	
	public VerificaErroVo() {
		mensagem = "";
	}

	public boolean isContainError() {
		return !mensagem.isEmpty();
	}
	
	public boolean isNotContainError() {
		return !isContainError();
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
}
