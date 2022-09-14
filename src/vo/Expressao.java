package vo;

import java.util.List;

import type.ExpressaoType;
import util.ExpressaoUtil;

public class Expressao {

	private List<Token> listaTokens;
	private ExpressaoType expressaoType;
	
	public Expressao(List<Token> listaTokens) {
		this.listaTokens = listaTokens;
		identificarExpressao();
	}

	private void identificarExpressao() {
		boolean possuiOperacaoAritmetica = ExpressaoUtil.possuiOperacaoAritmetica(listaTokens);
		boolean possuiParenteses = ExpressaoUtil.possuiParenteses(listaTokens);
		boolean possuiColchetes = ExpressaoUtil.possuiColchetes(listaTokens);
		boolean possuiOperadorRelacional = ExpressaoUtil.possuiOperadorRelacional(listaTokens);
		boolean possuiAtribuicao = ExpressaoUtil.possuiAtribuicao(listaTokens);
		boolean possuiDeclaracaoVariavel = ExpressaoUtil.possuiDeclaracaoVariavel(listaTokens);
		boolean possuiComandoRepeticao = ExpressaoUtil.possuiComandoRepeticao(listaTokens);
		boolean possuiComandoFluxoControle = ExpressaoUtil.possuiComandoFluxoControle(listaTokens);
		
		if(possuiComandoRepeticao) {
			expressaoType = ExpressaoType.CMD_REPETICAO;
			return;
		}
		
		if(possuiComandoFluxoControle) {
			expressaoType = ExpressaoType.CMD_FLUXO_CONTROLE;
			return;
		}
		
		
		if(possuiOperadorRelacional) {
			expressaoType = ExpressaoType.EXP_ARIT_RELACIONAIS;
			return;
		}

		if(possuiDeclaracaoVariavel) {
			expressaoType = ExpressaoType.DEC_VARIAVEL;
			return;
		}
		
		if(possuiOperacaoAritmetica) {
			if(possuiColchetes) {
				expressaoType = ExpressaoType.EXP_ARIT_C_VETORES;
				return;
			}else if(possuiParenteses) {
				expressaoType = ExpressaoType.EXP_ARIT_C_PARENTESES;
				return;
			}else {
				expressaoType = ExpressaoType.EXP_ARIT_BINARIA;
				return;
			}
		}
		
		if(possuiAtribuicao) {
			expressaoType = ExpressaoType.ATR_SIMPLES;
			return;
		}
		
		expressaoType = ExpressaoType.EXP_INVALIDA;
		
	}
	
	public List<Token> getListaTokens() {
		return listaTokens;
	}

	public void setListaTokens(List<Token> listaTokens) {
		this.listaTokens = listaTokens;
	}

	public ExpressaoType getExpressaoType() {
		return expressaoType;
	}

	public void setExpressaoType(ExpressaoType expressaoType) {
		this.expressaoType = expressaoType;
	}
	
}
