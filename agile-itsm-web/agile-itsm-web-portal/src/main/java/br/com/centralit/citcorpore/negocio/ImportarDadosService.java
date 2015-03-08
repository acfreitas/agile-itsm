package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ImportarDadosDTO;
import br.com.citframework.service.CrudService;

public interface ImportarDadosService extends CrudService {

	/**
	 * Retorna lista com os registros da tabela ImportarDados relacionados ao idExternalConnection 
	 */
	public Collection<ImportarDadosDTO> consultarImportarDadosRelacionados(Integer idExternalConnection) throws Exception;
	
}