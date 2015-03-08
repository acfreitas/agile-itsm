package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;


@SuppressWarnings("rawtypes")
public interface ItemCfgSolicitacaoServService extends CrudService {
	
	public Collection findByIdItemConfiguracao(Integer parm) throws Exception;

	public void deleteByIdItemConfiguracao(Integer parm) throws Exception;
	
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception;
}
