package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;
public interface ProcessoNivelAutoridadeService extends CrudService {
	public Collection findByIdProcessoNegocio(Integer parm) throws ServiceException, LogicException;
	public void deleteByIdProcessoNegocio(Integer parm) throws ServiceException, LogicException;
}
