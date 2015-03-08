package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;
public interface ResponsavelCentroResultadoProcessoService extends CrudService {
	public Collection findByIdCentroResultadoAndIdResponsavel(Integer idCentroResultado, Integer idResponsavel) throws Exception;
	public void deleteByIdCentroResultado(Integer parm) throws ServiceException, LogicException;
}
