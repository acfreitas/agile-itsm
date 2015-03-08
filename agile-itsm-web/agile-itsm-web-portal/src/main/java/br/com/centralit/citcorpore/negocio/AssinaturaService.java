package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.AssinaturaDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * @author euler.ramos
 *
 */
public interface AssinaturaService extends CrudService {
    public boolean violaIndiceUnico(AssinaturaDTO assinaturaDTO)
	    throws ServiceException;
}
