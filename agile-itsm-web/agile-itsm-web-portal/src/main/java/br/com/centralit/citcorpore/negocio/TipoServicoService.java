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
	 * Verifica se tipo serviço.
	 * 
	 * @param tipoServicoDTO
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 */
	public boolean verificarSeTipoServicoExiste(TipoServicoDTO tipoServicoDto) throws PersistenceException, br.com.citframework.excecao.ServiceException;

	/**
	 * Metodo responsavel por verificar se existe vinculo entre o tipo de serviço e cadastro de serviço
	 * 
	 * @param idTipoServico
	 * @author Ezequiel
	 * @data  25/11/2014
	 */
	public boolean existeVinculadoCadastroServico(final Integer idTipoServico) throws PersistenceException, Exception;

}
