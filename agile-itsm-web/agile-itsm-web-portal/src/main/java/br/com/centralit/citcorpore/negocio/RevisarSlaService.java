package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

/**
 * @since 14/06/2013
 * @author rodrigo.oliveira
 *
 */
public interface RevisarSlaService extends CrudService {
	/**
	 * M�todo para retornar as revis�es por SLA.
	 * @param idAcordoNivelServico
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception;
	
	/**
	 * M�todo para deletar as revis�es por SLA.
	 * @param idAcordoNivelServico
	 * @throws Exception
	 */
	public void deleteByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception;
}
