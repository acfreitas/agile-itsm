package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ContatoRequisicaoLiberacaoDTO;
import br.com.citframework.service.CrudService;

public interface ContatoRequisicaoLiberacaoService extends CrudService {
	public ContatoRequisicaoLiberacaoDTO restoreContatosById(Integer idContatoRequisicaoLiberacao);
}

