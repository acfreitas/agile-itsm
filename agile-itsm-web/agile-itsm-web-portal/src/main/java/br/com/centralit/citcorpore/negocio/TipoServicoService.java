package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.TipoServicoDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.service.CrudService;

/**
 * @author leandro.viana
 * 
 */
public interface TipoServicoService extends CrudService {

	/**
	 * Verifica se tipo servi�o.
	 * 
	 * @param tipoServicoDTO
	 * @return true - existe; false - n�o existe;
	 * @throws PersistenceException
	 */
	public boolean verificarSeTipoServicoExiste(TipoServicoDTO tipoServicoDto) throws PersistenceException, br.com.citframework.excecao.ServiceException;

	/**
	 * Metodo responsavel por verificar se existe vinculo entre o tipo de servi�o e cadastro de servi�o
	 * 
	 * @param idTipoServico
	 * @author Ezequiel
	 * @data  25/11/2014
	 */
	public boolean existeVinculadoCadastroServico(final Integer idTipoServico) throws PersistenceException, Exception;

}
