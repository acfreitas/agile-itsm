package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AcaoPlanoMelhoriaDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface AcaoPlanoMelhoriaService extends CrudService {
	public Collection findByIdPlanoMelhoria(Integer parm) throws Exception;

	public void deleteByIdPlanoMelhoria(Integer parm) throws Exception;

	
	public Collection findByIdObjetivoPlanoMelhoria(Integer parm) throws Exception;

	public void deleteByIdObjetivoPlanoMelhoria(Integer parm) throws Exception;

	/**
	 * Retorna uma lista de acao plano de melhoria de acordo com o plano de melhoria passado
	 * 
	 * @param acaoPlanoMelhoriaDto
	 * @return Collection
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<AcaoPlanoMelhoriaDTO> listAcaoPlanoMelhoria(AcaoPlanoMelhoriaDTO acaoPlanoMelhoriaDto) throws Exception;
}
