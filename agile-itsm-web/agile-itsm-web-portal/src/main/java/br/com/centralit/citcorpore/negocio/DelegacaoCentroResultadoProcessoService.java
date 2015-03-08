package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;
public interface DelegacaoCentroResultadoProcessoService extends CrudService {
	public Collection findByIdDelegacaoCentroResultado(Integer parm) throws ServiceException, LogicException;
	public void deleteByIdDelegacaoCentroResultado(Integer parm) throws ServiceException, LogicException;
}
