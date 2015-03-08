/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoUsuarioDTO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

/**
 * @author Vadoilo Damasceno
 * 
 */
public interface ImportanciaConhecimentoUsuarioService extends CrudService {

	public void deleteByIdConhecimento(Integer idBaseConhecimento, TransactionControler transactionControler) throws Exception;

	public void create(ImportanciaConhecimentoUsuarioDTO importanciaConhecimentoUsuario, TransactionControler transactionControler) throws Exception;

	/**
	 * Lista ImportanciaConhecimentoGrupo por idBaseConhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @return Collection<ImportanciaConhecimentoGrupoDTO>
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public Collection<ImportanciaConhecimentoUsuarioDTO> listByIdBaseConhecimento(Integer idBaseConhecimento) throws Exception;

}
