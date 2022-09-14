package util;

import java.util.ArrayList;
import java.util.List;

import type.TokenType;
import vo.Expressao;
import vo.Token;
import vo.VerificaErroVo;

public class VerificaErrosUtil {
	
	public static VerificaErroVo verificarErros(List<Token> listaTokens) {
		VerificaErroVo erroVo;
		
		erroVo = verificarDelimitadores(listaTokens);
		
		if(erroVo.isContainError()) {
			return erroVo;
		}
		
		erroVo = verificarOperadoresAritmeticos(listaTokens);
		
		if(erroVo.isContainError()) {
			return erroVo;
		}
				
		erroVo = verificarExpressaoFluxoControle(listaTokens);
		
		if(erroVo.isContainError()) {
			return erroVo;
		}
		
		erroVo = verificarComandoRepeticao(listaTokens);

		if(erroVo.isContainError()) {
			return erroVo;
		}
		
		erroVo = verificarDeclaracaoVariavel(listaTokens);
		
		if(erroVo.isContainError()) {
			return erroVo;
		}

		erroVo = verificarOperadoresRelacionais(listaTokens);
		
		return erroVo;
	}
	
	public static VerificaErroVo verificarErros(Expressao expressao) {
		VerificaErroVo erroVo;

		erroVo = verificarErros(expressao.getListaTokens());
		
		if(erroVo.isContainError()) {
			return erroVo;
		}
		
		erroVo = verificarExpressaoFluxoControle(expressao.getListaTokens());
		
		if(erroVo.isContainError()) {
			return erroVo;
		}
		
		erroVo = verificarComandoRepeticao(expressao.getListaTokens());

		if(erroVo.isContainError()) {
			return erroVo;
		}
		
		erroVo = verificarDeclaracaoVariavel(expressao.getListaTokens());
		
		if(erroVo.isContainError()) {
			return erroVo;
		}

		erroVo = verificarOperadoresRelacionais(expressao.getListaTokens());
		
		return erroVo;
	}
	
	public static VerificaErroVo verificarDelimitadores(List<Token> listaTokens) {
		
		VerificaErroVo erroVo;
		
		erroVo = verificarParenteses(listaTokens);
		
		if(erroVo.isContainError()) {
			return erroVo;
		}
		
		erroVo = verificarColchetes(listaTokens);
		
		if(erroVo.isContainError()) {
			return erroVo;
		}
		
		erroVo = verificarChaves(listaTokens);
		
		return erroVo;
	}
	
	public static VerificaErroVo verificarParenteses(List<Token> listaTokens) {
		VerificaErroVo erroVo = new VerificaErroVo();
		
		int qtdParentesesAbre = TokenUtil.contarOcorrencias(listaTokens, "(");
		int qtdParentesesFecha = TokenUtil.contarOcorrencias(listaTokens, ")");
		int primeiraOcorrenciaParentesesAbre = TokenUtil.getPosicaoPrimeiro(listaTokens, "(");
		int primeiraOcorrenciaParentesesFecha = TokenUtil.getPosicaoPrimeiro(listaTokens, ")");
		int ultimaOcorrenciaParentesesAbre = TokenUtil.getPosicaoUltimo(listaTokens, "(");
		int ultimaOcorrenciaParentesesFecha = TokenUtil.getPosicaoUltimo(listaTokens, ")");
		
		if(qtdParentesesAbre != qtdParentesesFecha) {
			if(qtdParentesesAbre>qtdParentesesFecha) {
				erroVo.setMensagem("Erro, faltou fechar o parênteses da linha " + listaTokens.get(ultimaOcorrenciaParentesesAbre).getLinha());
			}else{
				erroVo.setMensagem("Erro, faltou abrir o parênteses da linha " + listaTokens.get(ultimaOcorrenciaParentesesFecha).getLinha());
			}
			return erroVo;
		}
		
		if(primeiraOcorrenciaParentesesAbre>primeiraOcorrenciaParentesesFecha) {
			erroVo.setMensagem("Erro, faltou abrir o parênteses da linha " + listaTokens.get(primeiraOcorrenciaParentesesFecha).getLinha());
			return erroVo;
		}
		
		if(ultimaOcorrenciaParentesesAbre>ultimaOcorrenciaParentesesFecha) {
			erroVo.setMensagem("Erro, faltou fechar o parênteses da linha " + listaTokens.get(ultimaOcorrenciaParentesesAbre).getLinha());
			return erroVo;
		}
		
		return erroVo;
	}
	
	public static VerificaErroVo verificarColchetes(List<Token> listaTokens) {
		VerificaErroVo erroVo = new VerificaErroVo();
		
		int qtdColchetesAbre = TokenUtil.contarOcorrencias(listaTokens, "[");
		int qtdColchetesFecha = TokenUtil.contarOcorrencias(listaTokens, "]");
		int primeiraOcorrenciaColchetesAbre = TokenUtil.getPosicaoPrimeiro(listaTokens, "[");
		int primeiraOcorrenciaColchetesFecha = TokenUtil.getPosicaoPrimeiro(listaTokens, "]");
		int ultimaOcorrenciaColchetesAbre = TokenUtil.getPosicaoUltimo(listaTokens, "[");
		int ultimaOcorrenciaColchetesFecha = TokenUtil.getPosicaoUltimo(listaTokens, "]");
		
		if(qtdColchetesAbre != qtdColchetesFecha) {
			if(qtdColchetesAbre>qtdColchetesFecha) {
				erroVo.setMensagem("Erro, faltou fechar o colchetes da linha " + listaTokens.get(ultimaOcorrenciaColchetesAbre).getLinha());
			}else{
				erroVo.setMensagem("Erro, faltou abrir o colchetes da linha " + listaTokens.get(ultimaOcorrenciaColchetesFecha).getLinha());
			}
			return erroVo;
		}
		
		if(primeiraOcorrenciaColchetesAbre>primeiraOcorrenciaColchetesFecha) {
			erroVo.setMensagem("Erro, faltou abrir o colchetes da linha " + listaTokens.get(primeiraOcorrenciaColchetesFecha).getLinha());
			return erroVo;
		}
		
		if(ultimaOcorrenciaColchetesAbre>ultimaOcorrenciaColchetesFecha) {
			erroVo.setMensagem("Erro, faltou fechar o colchetes da linha " + listaTokens.get(ultimaOcorrenciaColchetesAbre).getLinha());
			return erroVo;
		}
		
		return erroVo;
	}
	
	public static VerificaErroVo verificarChaves(List<Token> listaTokens) {
		VerificaErroVo erroVo = new VerificaErroVo();
		
		int qtdChavesAbre = TokenUtil.contarOcorrencias(listaTokens, "{");
		int qtdChavesFecha = TokenUtil.contarOcorrencias(listaTokens, "}");
		int primeiraOcorrenciaChaveAbre = TokenUtil.getPosicaoPrimeiro(listaTokens, "{");
		int primeiraOcorrenciaChaveFecha = TokenUtil.getPosicaoPrimeiro(listaTokens, "}");
		int ultimaOcorrenciaChavesAbre = TokenUtil.getPosicaoUltimo(listaTokens, "{");
		int ultimaOcorrenciaChavesFecha = TokenUtil.getPosicaoUltimo(listaTokens, "}");
	
		
		if(qtdChavesAbre != qtdChavesFecha) {
			if(qtdChavesAbre>qtdChavesFecha) {
				erroVo.setMensagem("Erro, faltou fechar a chave da linha " + listaTokens.get(ultimaOcorrenciaChavesAbre).getLinha());
			}else{
				erroVo.setMensagem("Erro, faltou abrir a chave da linha " + listaTokens.get(ultimaOcorrenciaChavesFecha).getLinha());
			}
			return erroVo;
		}
		
		if(primeiraOcorrenciaChaveAbre>primeiraOcorrenciaChaveFecha) {
			erroVo.setMensagem("Erro, faltou abrir a chave da linha " + listaTokens.get(primeiraOcorrenciaChaveFecha).getLinha());
			return erroVo;
		}
		
		if(ultimaOcorrenciaChavesAbre>ultimaOcorrenciaChavesFecha) {
			erroVo.setMensagem("Erro, faltou fechar a chave da linha " + listaTokens.get(ultimaOcorrenciaChavesAbre).getLinha());
			return erroVo;
		}
		
		return erroVo;
	}
	
	public static VerificaErroVo verificarOperadoresAritmeticos(List<Token> listaTokens) {
		VerificaErroVo erroVo = new VerificaErroVo();
		
		boolean atualTokenIsOperador = false;
		boolean ultimoTokenIsOperador = false;
		
		for (int i=0;i<listaTokens.size();i++) {
			Token token = listaTokens.get(i);
			
			atualTokenIsOperador = TokenUtil.isOperadorAritmetico(token.getValue());
			
			if(atualTokenIsOperador) {
				if(i == 0) {
					erroVo.setMensagem("Erro, é esperado um número à esquerda do operador " + token.getValue() + " na linha " + token.getLinha());
					return erroVo;
				}else if(ultimoTokenIsOperador) {
					erroVo.setMensagem("Erro, Esperado um número, encontrado um operador aritmético. Linha " + token.getLinha());
					return erroVo;
				}else if(i == listaTokens.size()-1) {
					erroVo.setMensagem("Erro, é esperado um número à direita do operador " + token.getValue() + " na linha " + token.getLinha());
					return erroVo;
				}
			}
			
			if(atualTokenIsOperador){
				ultimoTokenIsOperador = true;
			}else {
				ultimoTokenIsOperador = false;
			}
		}
		
		return erroVo;
	}
	
	public static VerificaErroVo verificarOperadoresRelacionais(List<Token> listaTokens) {
		VerificaErroVo erroVo = new VerificaErroVo();
				
		List<Token> listaFiltrada = new ArrayList<Token>();
		
		for (Token token : listaTokens) {
			if(!TokenUtil.isDelimitadorNaoOperacional(token.getValue())) {
				listaFiltrada.add(token);
			}
		}
		
		int tamanhoLista = listaFiltrada.size();
		
		if(tamanhoLista == 0) {
			return erroVo;
		}
		
		boolean contemOperadorRelacional = false;
		int index = 0;
		for (int cont=0;cont<listaFiltrada.size();cont++) {
			Token token = listaFiltrada.get(cont);
			if(token.getToken() == TokenType.OP_REL) {
				contemOperadorRelacional = true;
				index = cont;
				continue;
			}
		}

		if(contemOperadorRelacional) {
			if(!listaFiltrada.contains(new Token("if")) && !listaFiltrada.contains(new Token("while"))) {
				erroVo.setMensagem("É necessário utilizar if ou while antes de uma expressão relacional.");
				return erroVo;
			}
			
		}
		
		return erroVo;
	}
	
	public static VerificaErroVo verificarExpressaoFluxoControle(List<Token> listaTokens) {
		VerificaErroVo erroVo = new VerificaErroVo();
		
		List<Token> listaFiltrada = new ArrayList<Token>();
		
		for (Token token : listaTokens) {
			if(!TokenUtil.isDelimitadorNaoOperacional(token.getValue())) {
				listaFiltrada.add(token);
			}
		}
		
		int tamanhoLista = listaFiltrada.size();
		
		if(tamanhoLista == 0) {
			return erroVo;
		}
		
		if(!listaFiltrada.contains(new Token("if"))) {
			return erroVo;
		}
		
		int index = listaFiltrada.indexOf(new Token("if"));
		
		index++;
		
		if(index<tamanhoLista) {
			if(!"(".equals(listaFiltrada.get(index).getValue())) {
				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "("));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
			return erroVo;
		}
		
		index++;
		
		if(index<tamanhoLista) {
			if(listaFiltrada.get(index).getToken() != TokenType.NUM && listaFiltrada.get(index).getToken() != TokenType.ID) {
				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "uma variável ou um número"));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
			return erroVo;
		}
		
		index++;
		
		if(index<tamanhoLista) {
			if(listaFiltrada.get(index).getToken() != TokenType.OP_REL) {
				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "um operador relacional"));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
			return erroVo;
		}
		
		index++;
		
		if(index<tamanhoLista) {
			if(listaFiltrada.get(index).getToken() != TokenType.NUM && listaFiltrada.get(index).getToken() != TokenType.ID) {
				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "uma variável ou um número"));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
			return erroVo;
		}
		
		index++;
		
		if(index<tamanhoLista) {
			if(!")".equals(listaFiltrada.get(index).getValue())) {
				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), ")"));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
			return erroVo;
		}
		
		index++;
		
		if(index<tamanhoLista) {
			if(!"{".equals(listaFiltrada.get(index).getValue())) {
				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "{"));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
			return erroVo;
		}
		
		if(!listaFiltrada.contains(new Token("else"))) {
			return erroVo;
		}
		
		while(!"else".equals(listaFiltrada.get(index).getValue())) {
			index++;
		}
		
		index++;
		
		if(index<tamanhoLista) {
			if(!"{".equals(listaFiltrada.get(index).getValue())) {
				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "{"));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
			return erroVo;
		}
		
		return erroVo;
	}
	
	public static VerificaErroVo verificarComandoRepeticao(List<Token> listaTokens) {
		VerificaErroVo erroVo = new VerificaErroVo();
		
		List<Token> listaFiltrada = new ArrayList<Token>();
		
		for (Token token : listaTokens) {
			if(!TokenUtil.isDelimitadorNaoOperacional(token.getValue())) {
				listaFiltrada.add(token);
			}
		}
		
		int tamanhoLista = listaFiltrada.size();
		
		if(tamanhoLista == 0) {
			return erroVo;
		}
		
		if(!listaFiltrada.contains(new Token("while"))) {
			return erroVo;
		}
		
		int index = listaFiltrada.indexOf(new Token("while"));
		
		
		index++;
		
		if(index<tamanhoLista) {
			if(!"(".equals(listaFiltrada.get(index).getValue())) {
				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "("));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
			return erroVo;
		}
		

		index++;
		
		if(index<tamanhoLista) {
			if(listaFiltrada.get(index).getToken() != TokenType.NUM && listaFiltrada.get(index).getToken() != TokenType.ID) {
				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "uma variável ou um número"));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
			return erroVo;
		}
		
		index++;
		
		if(index<tamanhoLista) {
			if(listaFiltrada.get(index).getToken() != TokenType.OP_REL) {
				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "um operador relacional"));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
			return erroVo;
		}
		
		index++;
		
		if(index<tamanhoLista) {
			if(listaFiltrada.get(index).getToken() != TokenType.NUM && listaFiltrada.get(index).getToken() != TokenType.ID) {
				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "uma variável ou um número"));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
			return erroVo;
		}
		
		index++;
		
		if(index<tamanhoLista) {
			if(!")".equals(listaFiltrada.get(index).getValue())) {
				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), ")"));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
			return erroVo;
		}
		
		index++;
		
		if(index<tamanhoLista) {
			if(!"{".equals(listaFiltrada.get(index).getValue())) {
				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "{"));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
			return erroVo;
		}
		
		return erroVo;
	}
	
	public static VerificaErroVo verificarDeclaracaoVariavel(List<Token> listaTokens) {
		VerificaErroVo erroVo = new VerificaErroVo();
		
		List<Token> listaFiltrada = new ArrayList<Token>();
		
		for (Token token : listaTokens) {
			if(!TokenUtil.isDelimitadorNaoOperacional(token.getValue())) {
				listaFiltrada.add(token);
			}
		}
		
		int tamanhoLista = listaFiltrada.size();
		
		if(tamanhoLista == 0) {
			return erroVo;
		}
		
		int index;
		
		if(listaFiltrada.contains(new Token("int"))) {
			index = listaFiltrada.indexOf(new Token("int"));
		}else if(listaFiltrada.contains(new Token("real"))) {
			index = listaFiltrada.indexOf(new Token("real"));
		}else {
			return erroVo;
		}
		
		if(!listaFiltrada.contains(new Token("var"))) {
			erroVo.setMensagem("Não foi encontrado a declaração de variável 'var'");
			return erroVo;
		}else {
			if(listaFiltrada.lastIndexOf(new Token("var")) != 0) {
				erroVo.setMensagem("A declaração 'var' deve estar no inicio do programa");
				return erroVo;
			}
		}
		
		erroVo = verificarDeclaracaoVariavelSimples(listaFiltrada, ++index);
	
		if(erroVo.isContainError()) {
			return erroVo;
		}
		
		index++;
		
		return erroVo;
	}
	
	public static VerificaErroVo verificarDeclaracaoVariavelSimples(List<Token> listaTokens, int index) {
		VerificaErroVo erroVo = new VerificaErroVo();
		int tamanhoLista = listaTokens.size();
		
		if(index<tamanhoLista) {
			if(listaTokens.get(index).getToken() != TokenType.ID) {
				erroVo.setMensagem(getUnknownError(listaTokens.get(index), "uma variável"));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaTokens.get(index-1).getLinha());
			return erroVo;
		}

		index++;
		
		if(index<tamanhoLista && "[".equals(listaTokens.get(index).getValue())) {
			
			index++;
			if(index<tamanhoLista) {
				if(listaTokens.get(index).getToken() != TokenType.NUM) {
					erroVo.setMensagem(getUnknownError(listaTokens.get(index), "um número"));
					return erroVo;
				}
			}else {
				erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaTokens.get(index-1).getLinha());
				return erroVo;
			}
			
			index++;
			if(index<tamanhoLista) {
				if(!"]".equals(listaTokens.get(index).getValue())) {
					erroVo.setMensagem(getUnknownError(listaTokens.get(index), "]"));
					return erroVo;
				}
			}else {
				erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaTokens.get(index-1).getLinha());
				return erroVo;
			}
			
			index++;
			
		}
		
		if(index<tamanhoLista) {
			if(listaTokens.get(index).getToken() == TokenType.OP_ATR) {
				index++;
				
				if(index<tamanhoLista) {
					if(listaTokens.get(index).getToken() != TokenType.NUM) {
						erroVo.setMensagem(getUnknownError(listaTokens.get(index), "número"));
						return erroVo;
					}
				}else {
					erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaTokens.get(index-1).getLinha());
					return erroVo;
				}
				
				index++;
				
				if(index<tamanhoLista) {
					if(";".equals(listaTokens.get(index).getValue())) {
						return erroVo;
					}else{
						erroVo.setMensagem(getUnknownError(listaTokens.get(index), ";"));
						return erroVo;
					}
				}else {
					erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaTokens.get(index-1).getLinha());
					return erroVo;
				}
			}
		}
		
		if(index<tamanhoLista) {
			if(";".equals(listaTokens.get(index).getValue())) {
				return erroVo;
			}else if(",".equals(listaTokens.get(index).getValue())) {
				return verificarDeclaracaoVariavelSimples(listaTokens, ++index);
			}else {
				erroVo.setMensagem(getUnknownError(listaTokens.get(index), "';' ou ','"));
				return erroVo;
			}
		}else {
			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaTokens.get(index-1).getLinha());
			return erroVo;
		}

	}

	private static String getUnknownError(Token token, String esperado) {
		return "Erro na linha " + token.getLinha() + ". Esperado encontrar " + esperado + ". Encontrado " + token.getValue() + ".";
	}
	
}
