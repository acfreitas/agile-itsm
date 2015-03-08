package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ControleContratoDTO;
import br.com.citframework.service.CrudService;

/**
 * @author Pedro
 *
 */
public interface ControleContratoTreinamentoService extends CrudService {
	
	public void deleteByIdControleContrato(ControleContratoDTO controleContrato) throws Exception;

}
