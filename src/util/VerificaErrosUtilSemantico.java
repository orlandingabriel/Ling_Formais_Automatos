package util;

import java.util.ArrayList;
import java.util.List;

import type.TokenType;
import vo.Expressao;
import vo.Token;
import vo.VerificaErroVo;

public class VerificaErrosUtilSemantico {
	public static int index = 0;
	public static int Temporario = 0;
	public static int Label = 0;
	
	public static VerificaErroVo verificarErros(List<Token> listaTokens) {
		VerificaErroVo erroVo = new VerificaErroVo();

		for (int i=0;i<listaTokens.size();i++) {
			erroVo = verificarDelimitadores(listaTokens);
			
			if(erroVo.isContainError()) {
				return erroVo;
			}
			
			erroVo = verificarDeclaracaoVariavel(listaTokens);
			
			if(erroVo.isContainError()) {
				return erroVo;
			}
			
		}
			
//			erroVo = verificarAtrib(listaTokens);
//	
//			if(erroVo.isContainError()) {
//				return erroVo;
//			}
//					
//			erroVo = verificarExpressaoFluxoControle(listaTokens);
//			
//			if(erroVo.isContainError()) {
//				return erroVo;
//			}
//			
//			erroVo = verificarComandoRepeticao(listaTokens);
//	
//			if(erroVo.isContainError()) {
//				return erroVo;
//			}
			
		//		while(index < listaTokens.size()-1) {
			erroVo = verificarComandos(listaTokens);
			if(erroVo.isContainError()) {
				return erroVo;
			}
			//		}
//		}
		return erroVo;

	}
	
//	public static VerificaErroVo verificarErros(Expressao expressao) {
//		VerificaErroVo erroVo = new VerificaErroVo();
//
//		erroVo = verificarErros(expressao.getListaTokens());
//		
//		if(erroVo.isContainError()) {
//			return erroVo;
//		}
//		
//		erroVo = verificarExpressaoFluxoControle(expressao.getListaTokens());
//		
//		if(erroVo.isContainError()) {
//			return erroVo;
//		}
//		
//		erroVo = verificarComandoRepeticao(expressao.getListaTokens());
//
//		if(erroVo.isContainError()) {
//			return erroVo;
//		}
//		
//		erroVo = verificarDeclaracaoVariavel(expressao.getListaTokens());
//		
//		return erroVo;
//	}
	
	public static String CriaTemp() {
	
		return "T"+String.valueOf(Temporario++);
		
	}
	
	public static String CriaLabel() {
		
		return "L"+String.valueOf(Label++);
		
	}
	
	public static VerificaErroVo verificarDelimitadores(List<Token> listaTokens) {
		
		VerificaErroVo erroVo = new VerificaErroVo();
		
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

	public static VerificaErroVo verificarComandos(List<Token> listaTokens) {
		VerificaErroVo erroVo = new VerificaErroVo();

//		for (int i=index;i<listaTokens.size();i++) {
		while(index < listaTokens.size()-1) {
			erroVo = verificarAtrib(listaTokens);
			if(erroVo.isContainError()) {
				erroVo = verificarFluxodeControle(listaTokens);
				if(erroVo.isContainError()) {
					erroVo = verificarComandoDeRepeticao(listaTokens);
					if(erroVo.isContainError()) {
						return erroVo;
					}
				}
			}
//		}
		}
		return erroVo;
	}

	public static VerificaErroVo verificarExpressaoArit(List<Token> listaTokens) {
		// E -> T + E | T
		VerificaErroVo erroVo = new VerificaErroVo();
		VerificaErroVo erroVoE = new VerificaErroVo();
		int indice=index;
		Token token = listaTokens.get(index);

		erroVo = verificarTermo(listaTokens);
		if (erroVo.isNotContainError()) {
			token = listaTokens.get(index);
			String tlocal = erroVo.getLocal();
			if ("+".equals(token.getValue()) || "-".equals(token.getValue())) {
//			if (TokenUtil.isOperadorAritmetico(token.getValue())) {
				String operArit = token.getValue();
				index++;
				erroVo = verificarExpressaoArit(listaTokens);
				if (erroVo.isNotContainError()) {
				String e1local = erroVo.getLocal();
					String elocal=CriaTemp();
					System.out.println( elocal + "=" + tlocal + operArit + e1local);
					erroVo.setLocal(elocal);
					return erroVo;
				}
			}
		} else {
			index=indice;
			erroVoE = verificarTermo(listaTokens);
			if (erroVoE.isContainError()) {
				erroVoE.setMensagem("Verifique express�o" + token.getValue() + " na linha " + token.getLinha());
			}
			erroVo.setLocal(erroVoE.getLocal());
		}
		return erroVo;
	}
	
	
	public static VerificaErroVo verificarTermo(List<Token> listaTokens) {
		// T -> F * T | F
		
		VerificaErroVo erroVo = new VerificaErroVo();
		VerificaErroVo erroVoF = new VerificaErroVo();
		int indice=index;
		Token token = listaTokens.get(index);

		erroVo = verificarFator(listaTokens);
		if (erroVo.isNotContainError()) {
			token = listaTokens.get(index);
			String flocal = erroVo.getLocal();
			if ("*".equals(token.getValue()) || "/".equals(token.getValue())) {
//			if (TokenUtil.isOperadorAritmetico(token.getValue())) {
				String operArit = token.getValue();
				index++;
				erroVo = verificarTermo(listaTokens);
				if (erroVo.isNotContainError()) {
					String t1local = erroVo.getLocal();
					String tlocal= CriaTemp();
					System.out.println( tlocal + "=" + flocal + operArit + t1local);
					erroVo.setLocal(tlocal);
					return erroVo;
				}
			}
		} else {
			index=indice;
			erroVoF = verificarFator(listaTokens);
			if (erroVoF.isContainError()) {
				erroVoF.setMensagem("Verifique express�o" + token.getValue() + " na linha " + token.getLinha());
			}
			erroVo.setLocal(erroVoF.getLocal());
		}
		return erroVo;
	}
	
	public static VerificaErroVo verificarFator(List<Token> listaTokens) {
		// F -> NUM | ( E )
		
		VerificaErroVo erroVo = new VerificaErroVo();
		Token token = listaTokens.get(index);
		if(TokenUtil.isNumero(token.getValue()) || TokenUtil.isID(token.getValue())) {
			erroVo.setLocal(token.getValue());
			index++;
		}
		else {
			if("(".equals(token.getValue())) {
				index++;
				erroVo = verificarExpressaoArit(listaTokens);
				token = listaTokens.get(index);
				if(")".equals(token.getValue())) {
					index++;
				}
			}
			else {
				erroVo.setMensagem("Erro, esperado um (" + token.getValue() + " na linha " + token.getLinha());
			}
		}
		if(erroVo.isContainError()) {
			erroVo.setMensagem("Erro, é esperado um número" + token.getValue() + " na linha " + token.getLinha());
		}
		return erroVo;
	}

	public static VerificaErroVo verificarAtrib(List<Token> listaTokens) {
		// ATRIB -> ID '=' E ';'
		
		VerificaErroVo erroVo = new VerificaErroVo();
		Token token = listaTokens.get(index);

		if(TokenUtil.isID(token.getValue()) && !TokenUtil.isComandoFluxoControle(token.getValue()) && !TokenUtil.isComandoRepeticao(token.getValue())) { // ID
			String llocal = token.getValue();
			index++;
			token = listaTokens.get(index);
			if("=".equals(token.getValue())) {
				index++;
				erroVo = verificarExpressaoArit(listaTokens);
				token = listaTokens.get(index);
				if(";".equals(token.getValue())) {
					System.out.println( llocal + "=" + erroVo.getLocal());
					index++;
				}
				else {
				erroVo.setMensagem("Erro, é esperado um ;" + token.getValue() + " na linha " + token.getLinha());	
				}
			}
			else
			{
			erroVo.setMensagem("Erro, é esperado uma igualdade" + token.getValue() + " na linha " + token.getLinha());		
			}
		}
		else {
			erroVo.setMensagem("Erro, é esperado uma atribui��o" + token.getValue() + " na linha " + token.getLinha());	
		}
		
		if (erroVo.isContainError()) {
			erroVo.setMensagem("Erro, é esperado uma atribui��o" + token.getValue() + " na linha " + token.getLinha());
		}
		return erroVo;
	}
	
	public static VerificaErroVo verificarER(List<Token> listaTokens) {
		// ER -> exp_arit RELOP exp_Arit ';'
		
		VerificaErroVo erroVo = new VerificaErroVo();
		Token token = listaTokens.get(index);
		erroVo = verificarExpressaoArit(listaTokens);
		if(erroVo.isNotContainError()) {
			if((listaTokens.get(index).getToken()) == TokenType.OP_REL) {
					String oprel = listaTokens.get(index).getValue();
					index++;
					String eresq = erroVo.getLocal();
					erroVo = verificarExpressaoArit(listaTokens);
					System.out.print(eresq + oprel + erroVo.getLocal());
			}
			else
			{
			erroVo.setMensagem("Erro, é esperado um operador relacional" + token.getValue() + " na linha " + token.getLinha());	
			}
		}
		return erroVo;
	}
	
	public static VerificaErroVo verificarFluxodeControle(List<Token> listaTokens) {
		// If (id<id) {};
		VerificaErroVo erroVo = new VerificaErroVo();
		String efalse = "";
		
		Token token = listaTokens.get(index);
		if(TokenUtil.isComandoFluxoControle(token.getValue())) {
			String sif = token.getValue();		// guarda comando
			index++;
			token = listaTokens.get(index);
			if("(".equals(token.getValue())) {
				index++;
				System.out.print(sif + ' ');	// iniciou geracao comando
				verificarER(listaTokens);		// gerou relacional
				token = listaTokens.get(index);
				if(")".equals(token.getValue())) {
					index++;
					efalse = CriaLabel();		// CRIA_LABEL
					System.out.println(" GOTO " + efalse);  // terminou geracao IF
					token = listaTokens.get(index);
				}
				else
				{
					erroVo.setMensagem("Falta fechar parenteses" + token.getValue() + " na linha " + token.getLinha());
				}
			}
			else
			{
				erroVo.setMensagem("Falta abrir parenteses" + token.getValue() + " na linha " + token.getLinha());
			}
			if("{".equals(token.getValue())) {
				index++;
				verificarComandos(listaTokens);
				token = listaTokens.get(index);
				if("}".equals(token.getValue())) {
					index++;
					System.out.println(efalse + ":");	// gera a saida do IF
				}
				else
				{
					erroVo.setMensagem("Falta fechar chaves" + token.getValue() + " na linha " + token.getLinha());	
				}
			}
			else
			{
				erroVo.setMensagem("Falta abrir chaves" + token.getValue() + " na linha " + token.getLinha());
			}
		}
		else {
			erroVo.setMensagem("Erro, é esperado um comando IF" + token.getValue() + " na linha " + token.getLinha());	
		}
		return erroVo;
	}
	
	public static VerificaErroVo verificarComandoDeRepeticao(List<Token> listaTokens) {
		// while (id<id) {};
		VerificaErroVo erroVo = new VerificaErroVo();
		String efalse = "";
		//String etrue = "";
		String sbegin = "";
		//String snext = "";
		String swhile = "If";
		//String snext = "";
		//efalse = snext;
		//s1next = sbegin
		Token token = listaTokens.get(index);
		if(TokenUtil.isComandoRepeticao(token.getValue())) {
			sbegin = CriaLabel();
			System.out.println(sbegin+":");
			index++;
			token = listaTokens.get(index);
			if("(".equals(token.getValue())) {
				index++;
				System.out.print(swhile + ' ');	// iniciou geracao comando
				verificarER(listaTokens);
				token = listaTokens.get(index);
				if(")".equals(token.getValue())) {
					index++;
					efalse = CriaLabel();
					System.out.println(" GOTO " + efalse);
					token = listaTokens.get(index);
					
				}
				else {
					erroVo.setMensagem("Falta fechar parenteses" + token.getValue() + " na linha " + token.getLinha());
					}
				if("{".equals(token.getValue())) {
					index++;
					verificarComandos(listaTokens);
					token = listaTokens.get(index);
					if("}".equals(token.getValue())) {
						index++;
						System.out.println(" GOTO " + sbegin);
						System.out.println(efalse + ":");
					}
					else {
						erroVo.setMensagem("Falta fechar chaves" + token.getValue() + " na linha " + token.getLinha());
					}
				}
				else {
					erroVo.setMensagem("Falta abrir chaves" + token.getValue() + " na linha " + token.getLinha()); 
					}
			}
			else {
				erroVo.setMensagem("Falta abrir parenteses" + token.getValue() + " na linha " + token.getLinha());
				}
			}
		else {
			erroVo.setMensagem("Erro, é esperado um comando WHILE" + token.getValue() + " na linha " + token.getLinha());	
		}
	return erroVo;
	}

/*	
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
	
*/
	
//	
//	public static VerificaErroVo verificarExpressaoFluxoControle(List<Token> listaTokens) {
//		VerificaErroVo erroVo = new VerificaErroVo();
//		
//		List<Token> listaFiltrada = new ArrayList<Token>();
//		
//		for (Token token : listaTokens) {
//			if(!TokenUtil.isDelimitadorNaoOperacional(token.getValue())) {
//				listaFiltrada.add(token);
//			}
//		}
//		
//		int tamanhoLista = listaFiltrada.size();
//		
//		if(tamanhoLista == 0) {
//			return erroVo;
//		}
//		
//		if(!listaFiltrada.contains(new Token("if"))) {
//			return erroVo;
//		}
//		
//		int index = listaFiltrada.indexOf(new Token("if"));
//		
//		index++;
//		
//		if(index<tamanhoLista) {
//			if(!"(".equals(listaFiltrada.get(index).getValue())) {
//				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "("));
//				return erroVo;
//			}
//		}else {
//			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
//			return erroVo;
//		}
//		
//		index++;
//		
//		if(index<tamanhoLista) {
//			if(listaFiltrada.get(index).getToken() != TokenType.NUM && listaFiltrada.get(index).getToken() != TokenType.ID) {
//				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "uma variável ou um número"));
//				return erroVo;
//			}
//		}else {
//			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
//			return erroVo;
//		}
//		
//		index++;
//		
//		if(index<tamanhoLista) {
//			if(listaFiltrada.get(index).getToken() != TokenType.OP_REL) {
//				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "um operador relacional"));
//				return erroVo;
//			}
//		}else {
//			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
//			return erroVo;
//		}
//		
//		index++;
//		
//		if(index<tamanhoLista) {
//			if(listaFiltrada.get(index).getToken() != TokenType.NUM && listaFiltrada.get(index).getToken() != TokenType.ID) {
//				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "uma variável ou um número"));
//				return erroVo;
//			}
//		}else {
//			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
//			return erroVo;
//		}
//		
//		index++;
//		
//		if(index<tamanhoLista) {
//			if(!")".equals(listaFiltrada.get(index).getValue())) {
//				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), ")"));
//				return erroVo;
//			}
//		}else {
//			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
//			return erroVo;
//		}
//		
//		index++;
//		
//		if(index<tamanhoLista) {
//			if(!"{".equals(listaFiltrada.get(index).getValue())) {
//				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "{"));
//				return erroVo;
//			}
//		}else {
//			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
//			return erroVo;
//		}
//		
//		if(!listaFiltrada.contains(new Token("else"))) {
//			return erroVo;
//		}
//		
//		while(!"else".equals(listaFiltrada.get(index).getValue())) {
//			index++;
//		}
//		
//		index++;
//		
//		if(index<tamanhoLista) {
//			if(!"{".equals(listaFiltrada.get(index).getValue())) {
//				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "{"));
//				return erroVo;
//			}
//		}else {
//			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
//			return erroVo;
//		}
//		
//		return erroVo;
//	}
//	
//	public static VerificaErroVo verificarComandoRepeticao(List<Token> listaTokens) {
//		VerificaErroVo erroVo = new VerificaErroVo();
//		
//		List<Token> listaFiltrada = new ArrayList<Token>();
//		
//		for (Token token : listaTokens) {
//			if(!TokenUtil.isDelimitadorNaoOperacional(token.getValue())) {
//				listaFiltrada.add(token);
//			}
//		}
//		
//		int tamanhoLista = listaFiltrada.size();
//		
//		if(tamanhoLista == 0) {
//			return erroVo;
//		}
//		
//		if(!listaFiltrada.contains(new Token("while"))) {
//			return erroVo;
//		}
//		
//		int index = listaFiltrada.indexOf(new Token("while"));
//		
//		
//		index++;
//		
//		if(index<tamanhoLista) {
//			if(!"(".equals(listaFiltrada.get(index).getValue())) {
//				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "("));
//				return erroVo;
//			}
//		}else {
//			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
//			return erroVo;
//		}
//		
//
//		index++;
//		
//		if(index<tamanhoLista) {
//			if(listaFiltrada.get(index).getToken() != TokenType.NUM && listaFiltrada.get(index).getToken() != TokenType.ID) {
//				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "uma variável ou um número"));
//				return erroVo;
//			}
//		}else {
//			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
//			return erroVo;
//		}
//		
//		index++;
//		
//		if(index<tamanhoLista) {
//			if(listaFiltrada.get(index).getToken() != TokenType.OP_REL) {
//				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "um operador relacional"));
//				return erroVo;
//			}
//		}else {
//			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
//			return erroVo;
//		}
//		
//		index++;
//		
//		if(index<tamanhoLista) {
//			if(listaFiltrada.get(index).getToken() != TokenType.NUM && listaFiltrada.get(index).getToken() != TokenType.ID) {
//				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "uma variável ou um número"));
//				return erroVo;
//			}
//		}else {
//			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
//			return erroVo;
//		}
//		
//		index++;
//		
//		if(index<tamanhoLista) {
//			if(!")".equals(listaFiltrada.get(index).getValue())) {
//				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), ")"));
//				return erroVo;
//			}
//		}else {
//			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
//			return erroVo;
//		}
//		
//		index++;
//		
//		if(index<tamanhoLista) {
//			if(!"{".equals(listaFiltrada.get(index).getValue())) {
//				erroVo.setMensagem(getUnknownError(listaFiltrada.get(index), "{"));
//				return erroVo;
//			}
//		}else {
//			erroVo.setMensagem("Encontrado expressão incompleta na linha " + listaFiltrada.get(index-1).getLinha());
//			return erroVo;
//		}
//		
//		return erroVo;
//	}
	
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
			int indexVar = listaFiltrada.lastIndexOf(new Token("var"));
			if(indexVar>index) {
				erroVo.setMensagem("As variáveis devem ser declaradas após 'var'");
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