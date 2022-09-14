package vo;
import type.OperadorType;
import type.TokenType;
import util.TokenUtil;

public class Token {

	private TokenType token;
	private String value;
	private int linha;

	public Token(String value) {
		this.value = value;
		identificarToken();
	}
	
	public Token(String value, int linha) {
		this.linha = linha;
		this.value = value;
		identificarToken();
	}

	private void identificarToken() {
		if (TokenUtil.isDelimitador(this.value)) {
			this.token = TokenType.DELIM;
		} else if (TokenUtil.isOperadorRelacional(this.value)) {
			this.token = TokenType.OP_REL;
		} else if (TokenUtil.isOperadorAtribuicao(this.value)) {
			this.token = TokenType.OP_ATR;
		} else if (TokenUtil.isOperadorAritmetico(this.value)) {
			this.token = OperadorType.getOperadorTypeFromValue(this.value).getTokenType();
		} else if (TokenUtil.isNumero(this.value)) {
			this.token = TokenType.NUM;
		} else if (TokenUtil.isPalavraReservada(this.value)) {
			this.token = TokenType.RESERV;
		} else {
			this.token = TokenType.ID;
		}
	}

	public TokenType getToken() {
		return token;
	}

	public String getValue() {
		return value;
	}
	
	public String getValueForPrint() {
		if(TokenUtil.isDelimitadorNaoOperacional(value)) {
			switch (value) {
			case "\n":
				return "";
			case "\t":
				return "";
			case "\r":
				return "";
			}
		}
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	

}
