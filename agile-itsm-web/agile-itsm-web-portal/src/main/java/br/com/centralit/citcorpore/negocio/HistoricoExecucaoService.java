package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface HistoricoExecucaoService extends CrudService {
	public Collection findByDemanda(Integer idDemanda) throws LogicException, ServiceException;
}
