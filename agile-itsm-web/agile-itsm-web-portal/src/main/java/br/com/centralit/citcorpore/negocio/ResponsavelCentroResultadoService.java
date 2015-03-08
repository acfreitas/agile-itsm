package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;
public interface ResponsavelCentroResultadoService extends CrudService {
	public Collection findByIdCentroResultado(Integer parm) throws ServiceException, LogicException;
	public Collection findByIdResponsavel(Integer parm) throws ServiceException, LogicException;
	public void deleteByIdCentroResultado(Integer parm) throws ServiceException, LogicException;
}
