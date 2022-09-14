package util;

import java.util.Arrays;
import java.util.List;

import type.TokenType;
import vo.Token;

public class ExpressaoUtil {
	
	private static final List<String> DELIMITADORES_EXPRESSAO = Arrays.asList("(", "{", ")", "}", ";");

	public static boolean isDelimitadorExpressao(Token token) {
		return isDelimitadorExpressao(token.getValue());
	}
	
	public static boolean isDelimitadorExpressao(String str) {
		for (String delimitador : DELIMITADORES_EXPRESSAO) {
			if (delimitador.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean possuiDeclaracaoVariavel(List<Token> listaTokens) {
		for(Token token : listaTokens) {
			if(TokenUtil.isDeclaracaoVariavel(token.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean possuiComandoRepeticao(List<Token> listaTokens) {
		for(Token token : listaTokens) {
			if(TokenUtil.isComandoRepeticao(token.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean possuiComandoFluxoControle(List<Token> listaTokens) {
		for(Token token : listaTokens) {
			if(TokenUtil.isComandoFluxoControle(token.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean possuiAtribuicao(List<Token> listaTokens) {
		for(Token token : listaTokens) {
			if(token.getToken() == TokenType.OP_ATR) {
				return true;
			}
		}
		return false;
	}

	public static boolean possuiOperadorRelacional(List<Token> listaTokens) {
		for(Token token : listaTokens) {
			if(token.getToken() == TokenType.OP_REL) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean possuiOperacaoAritmetica(List<Token> listaTokens) {
		boolean possuiOperacaoAritmetica = false;
		for(Token token : listaTokens) {
			possuiOperacaoAritmetica |= token.getToken() == TokenType.OP_ADD;
			possuiOperacaoAritmetica |= token.getToken() == TokenType.OP_SUB;
			possuiOperacaoAritmetica |= token.getToken() == TokenType.OP_MUL;
			possuiOperacaoAritmetica |= token.getToken() == TokenType.OP_DIV;
			possuiOperacaoAritmetica |= token.getToken() == TokenType.OP_EXP;
			if(possuiOperacaoAritmetica) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean possuiParenteses(List<Token> listaTokens) {
		boolean possuiParenteses = false;
		for(Token token : listaTokens) {
			possuiParenteses |= token.getValue().contains("(");
			possuiParenteses |= token.getValue().contains("(");
			if(possuiParenteses) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean possuiColchetes(List<Token> listaTokens) {
		boolean possuiColchetes = false;
		for(Token token : listaTokens) {
			possuiColchetes |= token.getValue().contains("[");
			possuiColchetes |= token.getValue().contains("]");
			if(possuiColchetes) {
				return true;
			}	
		}
		return false;
	}
	
	
}
