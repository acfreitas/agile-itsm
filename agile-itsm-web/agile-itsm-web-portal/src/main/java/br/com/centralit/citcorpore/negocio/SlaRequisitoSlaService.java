package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

/**
 * @since 14/06/2013
 * @author rodrigo.oliveira
 *
 */
public interface SlaRequisitoSlaService extends CrudService {
	/**
	 * M�todo para retornar todos os v�nculos de requisito com SLA.
	 * @param idArcodoNivelServico
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception;
	/**
	 * M�todo para deletar todos os v�nculos de requisito com SLA.
	 * @param idArcodoNivelServico
	 * @throws Exception
	 */
	public void deleteByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception;
}
