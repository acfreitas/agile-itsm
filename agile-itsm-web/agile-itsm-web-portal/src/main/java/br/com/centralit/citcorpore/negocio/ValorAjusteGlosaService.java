/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

/**
 * @author rodrigo.oliveira
 *
 */
public interface ValorAjusteGlosaService extends CrudService {
	
	public Collection findByIdServicoContrato(Integer parm) throws Exception;
	
}
