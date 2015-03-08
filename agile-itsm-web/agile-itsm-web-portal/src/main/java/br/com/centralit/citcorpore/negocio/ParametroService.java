package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ParametroDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface ParametroService extends CrudService {
	public ParametroDTO getValue(String moduloParm, String nomeParametroParm, Integer idEmpresaParm) throws ServiceException, LogicException;
	public void setValue(String moduloParm, String nomeParametroParm, Integer idEmpresaParm, String valorParm, String detalhamentoParm) throws ServiceException, LogicException;
}
