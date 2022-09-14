package type;

public enum OperadorType {

	OP_ADD(TokenType.OP_ADD, "+"), 
	OP_SUB(TokenType.OP_SUB, "-"),
	OP_MUL(TokenType.OP_MUL, "*"), 
	OP_DIV(TokenType.OP_DIV, "/"), 
	OP_EXP(TokenType.OP_EXP, "^");

	private final TokenType tokenType;
	private final String value;

	OperadorType(TokenType tokenType, String value) {
		this.tokenType = tokenType;
		this.value = value;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public String getValue() {
		return value;
	}

	public static OperadorType getOperadorTypeFromValue(String str) {
		for (OperadorType operadorType : OperadorType.values()) {
			if (operadorType.getValue().equalsIgnoreCase(str)) {
				return operadorType;
			}
		}
		return null;
	}

}
