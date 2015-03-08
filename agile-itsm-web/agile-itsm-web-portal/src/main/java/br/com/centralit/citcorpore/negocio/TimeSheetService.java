package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface TimeSheetService extends CrudService {
	public Collection findByPessoaAndPeriodo(Integer idEmpregado, Date dataInicio, Date dataFim) throws LogicException, ServiceException;
	public Collection findByDemanda(Integer idDemanda) throws LogicException, ServiceException;
}
