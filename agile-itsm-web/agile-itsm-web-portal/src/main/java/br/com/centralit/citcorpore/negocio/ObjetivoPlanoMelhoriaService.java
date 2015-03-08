package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ObjetivoPlanoMelhoriaDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface ObjetivoPlanoMelhoriaService extends CrudService {

	
	public Collection findByIdPlanoMelhoria(Integer parm) throws Exception;

	public void deleteByIdPlanoMelhoria(Integer parm) throws Exception;

	/**
	 * Retorna uma lista de objetivos de acordo com o plano de medida passado
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<ObjetivoPlanoMelhoriaDTO> listObjetivosPlanoMelhoria(ObjetivoPlanoMelhoriaDTO obj) throws Exception;
}
