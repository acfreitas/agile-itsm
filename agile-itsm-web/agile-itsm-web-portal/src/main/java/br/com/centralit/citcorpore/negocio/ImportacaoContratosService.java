package br.com.centralit.citcorpore.negocio;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.ImportacaoContratosDTO;
import br.com.citframework.excecao.ConnectionException;
import br.com.citframework.excecao.TransactionOperationException;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface ImportacaoContratosService extends CrudService {
	public ImportacaoContratosDTO persisteDados(HttpServletRequest request, Integer idContrato, String xml) throws TransactionOperationException, ConnectionException, Exception;
}
