package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface CriterioAvaliacaoService extends CrudService {
	public Collection findByAplicavelCotacao() throws Exception;

	public Collection findByAplicavelAvaliacaoSolicitante() throws Exception;

	public Collection findByAplicavelAvaliacaoComprador() throws Exception;
	
	/**
	 * Retorna true ou false em relação ao criterio basado
	 * 
	 * @param criterioAvaliacaoDto
	 * @return boolean
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarSeCriterioExiste(CriterioAvaliacaoDTO criterioAvaliacaoDto) throws Exception;

}
