package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.PrioridadeServicoUsuarioDTO;
import br.com.citframework.service.CrudService;

/**
 * @since 14/06/2013
 * @author rodrigo.oliveira
 *
 */

public interface PrioridadeServicoUsuarioService extends CrudService {
	/**
	 * @see Método utilizado para restaurar prioridades do usuários de acordo com o id do Acordo de Nível de serviço
	 * @param idAcordoNivelServico
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception;
	
	/**
	 * 
	 * @param idAcordoNivelServico
	 * @param idUsuario
	 * @return
	 * @throws Exception
	 */
    public PrioridadeServicoUsuarioDTO findByIdAcordoNivelServicoAndIdUsuario(Integer idAcordoNivelServico, Integer idUsuario) throws Exception;
    
    /**
     * @see Método utilizado para recuperar a Prioridade de um determinado usuário.
     * @param idUsuario
     * @return
     * @throws Exception
     */
    public Integer recuperaPrioridade(Integer idAcordoNivelServico, Integer idUsuario) throws Exception;
    
    /**
     * 
     * @param idAcordoNivelServico
     * @throws Exception
     */
    public void deleteByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception;
}
