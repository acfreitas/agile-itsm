package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
@SuppressWarnings("rawtypes")
public interface CausaIncidenteService extends CrudService {
	
	public Collection findByIdCausaIncidentePai(Integer parm) throws Exception;
	public void deleteByIdCausaIncidentePai(Integer parm) throws Exception;
	public Collection listHierarquia() throws Exception;
	public Collection getCollectionHierarquia(Integer idCausa, Integer nivel) throws Exception;
	
	/**
	 * Retorna uma lista de causa de acordo com o parametro passado
	 * @param descricaoCausa
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection listaCausaByDescricaoCausa(String descricaoCausa) throws Exception;
	public Collection listaCausasAtivas() throws Exception;
}
