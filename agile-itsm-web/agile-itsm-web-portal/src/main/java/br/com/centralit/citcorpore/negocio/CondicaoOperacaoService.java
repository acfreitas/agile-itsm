package br.com.centralit.citcorpore.negocio;

import br.com.citframework.service.CrudService;

/**
 * @author ygor.magalhaes
 *
 */
public interface CondicaoOperacaoService extends CrudService {
	public boolean jaExisteCondicaoComMesmoNome(String nome);
}
