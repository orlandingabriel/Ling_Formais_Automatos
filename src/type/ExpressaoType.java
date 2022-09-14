package type;

public enum ExpressaoType {
	
	EXP_ARIT_BINARIA("Expressão Aritmética Binária"),
	EXP_ARIT_C_PARENTESES("Expressão Aritmética com parênteses"),
	EXP_ARIT_C_VETORES("Expressão Aritmética com vetores"),
	EXP_ARIT_RELACIONAIS("Expressão relacional"),
	DEC_VARIAVEL("Declaração de variável"),
	ATR_SIMPLES("Atribuição simples"),
	CMD_REPETICAO("Comando de repetição"),
	CMD_FLUXO_CONTROLE("Comando de fluxo de controle"),
	EXP_INVALIDA("Expressão inválida");
	
	private final String descricao;

	ExpressaoType(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
