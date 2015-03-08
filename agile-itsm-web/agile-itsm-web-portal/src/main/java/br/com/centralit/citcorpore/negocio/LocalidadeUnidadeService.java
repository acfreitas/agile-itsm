package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.citframework.service.CrudService;

public interface LocalidadeUnidadeService extends CrudService {
	/**
	 * Retonar lista de idlocalidade
	 * 
	 * @param idUnidade
	 * @return list
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public Collection<LocalidadeUnidadeDTO> listaIdLocalidades(Integer idUnidade) throws Exception;

	/**
	 * Retonar verdadeiro caso a localidade esteja associado a uma unidade ou falso caso a localidade não esteja associado a uma unidade
	 * 
	 * @param idLocalidade
	 * @return true- false
	 * @throws Exception
	 */
	public boolean verificarExistenciaDeLocalidadeEmUnidade(Integer idLocalidade) throws Exception;
	
	public void deleteByIdUnidade(Integer idUnidade) throws Exception;
	
}
