package br.com.centralit.citcorpore.negocio;

import br.com.citframework.service.CrudService;

public interface SituacaoServicoService extends CrudService {
	boolean jaExisteSituacaoComMesmoNome(String nome);
	public boolean existeServicoAssociado(Integer idSituacaoServico) throws Exception;
}
