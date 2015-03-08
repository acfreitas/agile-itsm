package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;
public interface LimiteAprovacaoAutoridadeService extends CrudService {
	public Collection findByIdLimiteAprovacao(Integer parm) throws ServiceException, LogicException;
	public void deleteByIdLimiteAprovacao(Integer parm) throws ServiceException, LogicException;
}
