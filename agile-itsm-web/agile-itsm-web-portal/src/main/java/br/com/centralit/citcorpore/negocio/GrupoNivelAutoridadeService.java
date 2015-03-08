package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;
public interface GrupoNivelAutoridadeService extends CrudService {
	public Collection findByIdNivelAutoridade(Integer parm) throws ServiceException, LogicException;
	public void deleteByIdNivelAutoridade(Integer parm) throws ServiceException, LogicException;
}
