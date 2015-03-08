package br.com.centralit.bpm.negocio;

import java.util.Map;

import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;

public class NegocioBpm {
	
	private TransactionControler transacao;
	
	public TransactionControler getTransacao() {
		return transacao;
	}

	public void setTransacao(TransactionControler transacao) {
		this.transacao = transacao;
	}

	protected void setTransacaoDao(CrudDAO dao) throws Exception {
		if (this.transacao != null) 
			dao.setTransactionControler(this.transacao);
	}
	
	protected void adicionaObjeto(String chave, Object objeto, Map<String, Object> mapObjetos) throws Exception {
		if (mapObjetos.get(chave) != null)
			mapObjetos.remove(chave);
		mapObjetos.put(chave, objeto);
	}
}
