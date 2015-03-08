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
	 * Método para retornar as revisões por SLA.
	 * @param idAcordoNivelServico
	 * @return Collection
	 * @throws Exception
	 */
	public Collection findByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception;
	
	/**
	 * Método para deletar as revisões por SLA.
	 * @param idAcordoNivelServico
	 * @throws Exception
	 */
	public void deleteByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception;
}
