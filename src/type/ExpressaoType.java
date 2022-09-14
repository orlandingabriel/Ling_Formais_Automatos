package type;

public enum ExpressaoType {
	
	EXP_ARIT_BINARIA("Express�o Aritm�tica Bin�ria"),
	EXP_ARIT_C_PARENTESES("Express�o Aritm�tica com par�nteses"),
	EXP_ARIT_C_VETORES("Express�o Aritm�tica com vetores"),
	EXP_ARIT_RELACIONAIS("Express�o relacional"),
	DEC_VARIAVEL("Declara��o de vari�vel"),
	ATR_SIMPLES("Atribui��o simples"),
	CMD_REPETICAO("Comando de repeti��o"),
	CMD_FLUXO_CONTROLE("Comando de fluxo de controle"),
	EXP_INVALIDA("Express�o inv�lida");
	
	private final String descricao;

	ExpressaoType(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
