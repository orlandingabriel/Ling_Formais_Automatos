package type;

public enum TokenType {

	ID("ID"), 
	NUM("Número"), 
	OP_ADD("Operação de adição"), 
	OP_SUB("Operação de subtração"), 
	OP_MUL("Operação de multiplicação"), 
	OP_DIV("Operação de divisão"), 
	OP_EXP("Operação de exponenciação"), 
	OP_REL("Operador relacional"), 
	OP_ATR("Operador de atribuição"), 
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
