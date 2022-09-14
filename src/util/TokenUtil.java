package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vo.Token;

public class TokenUtil {

	private static final List<String> DELIMITADORES_OPERACIONAIS = Arrays.asList("(", ")", ".", "{", "}", "[", "]", ";", ",");
	private static final List<String> DELIMITADORES_NAO_OPERACIONAIS = Arrays.asList("\n", "\t", "\r");
	private static final List<String> OPERADORES_ARITMETICOS = Arrays.asList("+", "-", "*", "/", "^");
	private static final List<String> OPERADORES_RELACIONAIS = Arrays.asList(">", ">=", "<", "<=", "==", "<>");
	private static final List<String> OPERADORES_ATRIBUICAO = Arrays.asList("=");
	private static final List<String> DECLARACAO_VARIAVEIS = Arrays.asList("var", "int", "real"); 
	private static final List<String> COMANDOS_REPETICAO = Arrays.asList("while"); 
	private static final List<String> COMANDOS_FLUXO_CONTROLE = Arrays.asList("if", "else");

	public static boolean isTokenReconhecido(String str) {
		return isOperador(str) || isDelimitador(str);
	}
	
	public static boolean isPalavraReservada(String str) {
		boolean retorno = false;

		retorno |= isDeclaracaoVariavel(str);
		retorno |= isComandoRepeticao(str);
		retorno |= isComandoFluxoControle(str);

		return retorno;
	}
	
	public static boolean isDeclaracaoVariavel(String str) {
		for (String declaracaoVariavel : DECLARACAO_VARIAVEIS) {
			if (declaracaoVariavel.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isComandoRepeticao(String str) {
		for (String comandoRepeticao : COMANDOS_REPETICAO) {
			if (comandoRepeticao.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isComandoFluxoControle(String str) {
		for (String comandoFluxoControle : COMANDOS_FLUXO_CONTROLE) {
			if (comandoFluxoControle.equals(str.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOperador(String str) {
		boolean retorno = false;

		retorno |= isOperadorAritmetico(str);
		retorno |= isOperadorRelacional(str);
		retorno |= isOperadorAtribuicao(str);

		return retorno;
	}

	public static boolean isDelimitador(String str) {
		return isDelimitadorOperacional(str) || isDelimitadorNaoOperacional(str);
	}

	public static boolean isDelimitadorOperacional(String str) {
		for (String delimitador : DELIMITADORES_OPERACIONAIS) {
			if (delimitador.equals(str)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isDelimitadorNaoOperacional(String str) {
		for (String delimitador : DELIMITADORES_NAO_OPERACIONAIS) {
			if (delimitador.equals(str)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOperadorAritmetico(String str) {
		for (String operador : OPERADORES_ARITMETICOS) {
			if (operador.equals(str)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOperadorRelacional(String str) {
		for (String operador : OPERADORES_RELACIONAIS) {
			if (operador.equals(str)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOperadorAtribuicao(String str) {
		for (String operador : OPERADORES_ATRIBUICAO) {
			if (operador.equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isID(String str) {
		return str.matches("[A-Za-z]+");
	}

	public static boolean isNumero(String str) {
		return str.matches("^[-+]?\\d+(\\.\\d+)?$");
	}

	public static int getPosicaoPrimeiro(List<Token> listaTokens, String s) {
		for (int i=0;i<listaTokens.size();i++) {
			if(listaTokens.get(i).getValue().equals(s)) {
				return i;
			}
		}
		return -1;
	}
	
	public static int getPosicaoUltimo(List<Token> listaTokens, String s) {
		int posicao = -1;
		for (int i=0;i<listaTokens.size();i++) {
			if(listaTokens.get(i).getValue().equals(s)) {
				posicao = i;
			}
		}
		return posicao;
	}
	
	public static int contarOcorrencias(List<Token> listaTokens, String s) {
		int quantidade = 0;
		for (Token token : listaTokens) {
			if(token.getValue().equals(s)) {
				quantidade++;
			}
		}
		return quantidade;
	}
	
	public static List<Token> removerDelimitadoresNaoOperacionais(List<Token> listaTokens) {
		List<Token> resultado = new ArrayList<>();
		for (Token token : listaTokens) {
			if (!TokenUtil.isDelimitadorNaoOperacional(token.getValue())) {
				resultado.add(token);
			}
		}
		return resultado;
	}
	
}
