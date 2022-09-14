package controller;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import type.TokenType;
import util.ExpressaoUtil;
import util.TokenUtil;
import util.VerificaErrosUtil;
import util.VerificaErrosUtilSemantico;
import view.AnalisadorView;
import view.AnalisadorViewListener;
import vo.Expressao;
import vo.Token;
import vo.VerificaErroVo;

public class AnalisadorController implements AnalisadorViewListener {

	private AnalisadorView view;

	public AnalisadorController() {
		this.view = new AnalisadorView(this);
	}

	@Override
	public void buttonAnalisarLexicoClick() {
		view.clearOutput();

		String codigo = view.getInput().trim();
		
		List<Token> listaTokens = stringToTokenList(codigo);
		
		listaTokens = aplicarExcecoes(listaTokens);
		
		VerificaErroVo verificaErroVo = VerificaErrosUtil.verificarErros(listaTokens);
		
		if(verificaErroVo.isContainError()) {
			view.setErro(verificaErroVo.getMensagem());
		}else {
			imprimirTokens(listaTokens);
		}
		
	}
	
	@Override
	public void buttonAnalisarSintaticoClick() {
		view.clearOutput();
		
		String codigo = view.getInput().replaceAll("\\s", "");
		
		List<Token> listaTokens = stringToTokenList(codigo);
		
		listaTokens = aplicarExcecoes(listaTokens);
		
		VerificaErroVo verificaErroVo = VerificaErrosUtil.verificarErros(listaTokens);
		if(verificaErroVo.isContainError()) {
			view.setErro(verificaErroVo.getMensagem());
			return;
		}
		
		List<Expressao> listaExpressoes = tokenListToExpressaoList(listaTokens);
		
		imprimirExpressoes(listaExpressoes);
		
	}
	
	public void buttonAnalisarSemanticoClick() {
		view.clearOutput();
		
		String codigo = view.getInput().replaceAll("\\s", "");
		
		List<Token> listaTokens = stringToTokenList(codigo);
			
		listaTokens = aplicarExcecoes(listaTokens);
		
		VerificaErroVo verificaErroVo = VerificaErrosUtilSemantico.verificarErros(listaTokens);
		if(verificaErroVo.isContainError()) {
			view.setErro(verificaErroVo.getMensagem());
			return;
		}
		
		List<Expressao> listaExpressoes = tokenListToExpressaoList(listaTokens);
		
		
		imprimirExpressoesSemantico(listaExpressoes);
		
	}
	
	private void imprimirTokens(List<Token> listaTokens) {
		for (Token token : listaTokens) {
			view.appendOutput(token.getValueForPrint() + " -> " + token.getToken().getDescricao() + " - Linha " + token.getLinha());
		}
	}
	
	private void imprimirExpressoes(List<Expressao> listaExpressoes) {
		StringBuilder stringBuilder;
		for (Expressao expressao : listaExpressoes) {
			stringBuilder = new StringBuilder();
			if(expressao.getListaTokens().isEmpty()) {
				continue;
			}
			for (Token token : expressao.getListaTokens()) {
				stringBuilder.append(token.getValue());
			}
			stringBuilder.append(" -> ");
			stringBuilder.append(expressao.getExpressaoType().getDescricao());
			String texto = stringBuilder.toString();
			texto = texto.replaceAll("\n", "").trim();
			texto = texto.replaceAll("\r", "").trim();
			texto = texto.replaceAll("\t", "").trim();
			view.appendOutput(texto);
		}
	}
	private void imprimirExpressoesSemantico(List<Expressao> listaExpressoes) {
		StringBuilder stringBuilder;
		for (Expressao expressao : listaExpressoes) {
			stringBuilder = new StringBuilder();
			if(expressao.getListaTokens().isEmpty()) {
				continue;
			}
			for (Token token : expressao.getListaTokens()) {
				stringBuilder.append(token.getValue());
			}
			stringBuilder.append(" -> ");
			stringBuilder.append(expressao.getExpressaoType().getDescricao());
			String texto = stringBuilder.toString();
			texto = texto.replaceAll("\n", "").trim();
			texto = texto.replaceAll("\r", "").trim();
			texto = texto.replaceAll("\t", "").trim();
			view.appendOutput(texto);
		}
	}
	
	private List<Expressao> tokenListToExpressaoList(List<Token> listaTokens){
		
		List<Expressao> listaExpressoes = new ArrayList<>();
		List<Token> acumulado = new ArrayList<>();
		List<Token> listaFiltrada = new ArrayList<Token>();
		
		for (Token token : listaTokens) {
			if(" ".equals(token.getValue())) {
				listaFiltrada.add(token);
			}else if(!TokenUtil.isDelimitadorNaoOperacional(token.getValue())) {
				listaFiltrada.add(token);
			}
		}
		
		for(int cont=0;cont<listaFiltrada.size();cont++) {
			Token token = listaFiltrada.get(cont);
			if(ExpressaoUtil.isDelimitadorExpressao(token)) {
				listaExpressoes.add(new Expressao(acumulado));
				acumulado = new ArrayList<>();
			}else {
				acumulado.add(token);
				if (cont == listaFiltrada.size() - 1) {
					listaExpressoes.add(new Expressao(acumulado));
				}
			}
			
		}
		
		return listaExpressoes;
	}
	
	private List<Token> aplicarExcecoes(List<Token> listaTokens){
		List<Token> listaResultado = new ArrayList<>();
		
		listaResultado = aplicarExcessaoPontoNumero(listaTokens);
		listaResultado = aplicarExcessaoOperadoresRelacionais(listaResultado);

		return listaResultado;
	}
	
	private List<Token> aplicarExcessaoOperadoresRelacionais(List<Token> listaTokens){
		List<Token> listaResultado = new ArrayList<>();
	
		for(int cont=0;cont<listaTokens.size()-1;cont++) {
			
			Token token = listaTokens.get(cont);
			Token proximoToken = listaTokens.get(cont+1);
			
			if(token.getValue().equals("=") && proximoToken.getValue().equals("=")) {
				listaResultado.add(new Token("==", listaTokens.get(cont).getLinha()));
				cont++;
			}else if(token.getValue().equals(">") && proximoToken.getValue().equals("=")) {
				listaResultado.add(new Token(">=", listaTokens.get(cont).getLinha()));
				cont++;
			}else if(token.getValue().equals("<") && proximoToken.getValue().equals("=")) {
				listaResultado.add(new Token("<=", listaTokens.get(cont).getLinha()));
				cont++;
			}else if(token.getValue().equals("<") && proximoToken.getValue().equals(">")) {
				listaResultado.add(new Token("<>", listaTokens.get(cont).getLinha()));
				cont++;
			}else {
				listaResultado.add(token);
			}
			
			
		}
		
		if(listaTokens.size()-1 >= 0) {
			listaResultado.add(listaTokens.get(listaTokens.size()-1));
		}
		
		return listaResultado;
	}
	
	private List<Token> aplicarExcessaoPontoNumero(List<Token> listaTokens){
		List<Token> listaResultado = new ArrayList<>();
		
		for(int cont=0;cont<listaTokens.size();cont++) {
			Token token = listaTokens.get(cont);
						
			if(token.getValue().equals(".")) {
				if(cont > 0 && cont < listaTokens.size()-1) {
					if(listaTokens.get(cont-1).getToken() == TokenType.NUM && listaTokens.get(cont+1).getToken() == TokenType.NUM) {
						listaResultado.remove(cont-1);
						String num1 = listaTokens.get(cont-1).getValue();
						String num2 = listaTokens.get(cont+1).getValue();
						
						listaResultado.add(new Token(num1 + "." + num2, listaTokens.get(cont-1).getLinha()));
						cont++;
					}
				}else {
					listaResultado.add(token);
				}
			}else {
				listaResultado.add(token);
			}
		}
		return listaResultado;
	}
	
	private List<Token> stringToTokenList(String str){
		
		List<Token> resultado = new ArrayList<>();
		String acumulado = new String();
		int linha = 1; 

		for (int cont = 0; cont < str.length(); cont++) {
			char c = str.charAt(cont);
			String s = String.valueOf(c);

			if (TokenUtil.isTokenReconhecido(s)) {
				if (!acumulado.isEmpty()) {
					resultado.add(new Token(acumulado, linha));
				}
				resultado.add(new Token(s, linha));
				acumulado = "";
				if(s.equals("\n")) {
					linha++;
				}
			} else {
				acumulado += s;
				if (cont == str.length() - 1) {
					resultado.add(new Token(acumulado, linha));
				}
			}

		}

		return resultado;
	}
}
