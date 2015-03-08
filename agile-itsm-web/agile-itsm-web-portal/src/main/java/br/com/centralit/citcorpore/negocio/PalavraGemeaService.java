/**
 * CentralIT - CITSMart
 */
package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.PalavraGemeaDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.service.CrudService;

public interface PalavraGemeaService extends CrudService {

	public boolean VerificaSeCadastrado(PalavraGemeaDTO palavraGemeaDto) throws PersistenceException;
	
	public boolean VerificaSePalavraCorrespondenteExiste(PalavraGemeaDTO palavraGemeaDTO) throws PersistenceException;

}
