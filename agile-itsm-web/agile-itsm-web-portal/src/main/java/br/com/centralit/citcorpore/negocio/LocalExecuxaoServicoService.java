package br.com.centralit.citcorpore.negocio;

import java.util.HashMap;

import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface LocalExecuxaoServicoService extends CrudService {

	public boolean verificarSeLocalExecucaoServicoPossuiServico(HashMap mapFields) throws PersistenceException, ServiceException;

	public String verificaDescricaoDuplicadaAoCriar(HashMap mapFields) throws Exception;

	public String verificaDescricaoDuplicadaAoAlterar(HashMap mapFields) throws Exception;

}
