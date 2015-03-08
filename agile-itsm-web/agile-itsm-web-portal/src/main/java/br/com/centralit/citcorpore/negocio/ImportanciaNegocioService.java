package br.com.centralit.citcorpore.negocio;

import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface ImportanciaNegocioService extends CrudService {
	boolean existeRegistro(String nome);
	
	/**
	 * Metodo reponsavel por verificar se existe um vinculo entre Inmportancia Negocio e Cadastro de Serviço
	 * 
	 * @param idImportanciaNegocio
	 * @author Ezequiel
	 * @date 2014-11-25
	 */
	void existeVinculoCadastroServico(final Integer idImportanciaNegocio) throws PersistenceException, ServiceException, Exception;
}
