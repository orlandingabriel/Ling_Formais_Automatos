package type;

public enum TokenType {

	ID("ID"), 
	NUM("N�mero"), 
	OP_ADD("Opera��o de adi��o"), 
	OP_SUB("Opera��o de subtra��o"), 
	OP_MUL("Opera��o de multiplica��o"), 
	OP_DIV("Opera��o de divis�o"), 
	OP_EXP("Opera��o de exponencia��o"), 
	OP_REL("Operador relacional"), 
	OP_ATR("Operador de atribui��o"), 
	DELIM("Delimitador"),
	RESERV("Palavra Reservada");

	private final String descricao;

	TokenType(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
