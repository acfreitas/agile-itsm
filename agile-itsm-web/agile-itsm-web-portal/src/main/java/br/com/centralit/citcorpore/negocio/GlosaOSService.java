package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.GlosaOSDTO;
import br.com.citframework.service.CrudService;

public interface GlosaOSService extends CrudService {
	public Collection findByIdOs(Integer parm) throws Exception;

	public void deleteByIdOs(Integer parm) throws Exception;

	/**
	 * Retorna a soma das glosas de atividades por id da OS.
	 * 
	 * @param idOs
	 * @return
	 * @throws Exception
	 */
	public Double retornarCustoGlosaOSByIdOs(Integer idOs) throws Exception;

	/**
	 * Retorna uma lista de glosas de acordo com o idOs passado.
	 * 
	 * @param idOs
	 * @return
	 * @throws Exception
	 */
	public Collection<GlosaOSDTO> listaDeGlosas(Integer idOs) throws Exception;
}
