package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ContatoProblemaDTO;
import br.com.citframework.service.CrudService;
/**
 * 
 * @author geber.costa
 *
 */
public interface ContatoProblemaService extends CrudService {
	public ContatoProblemaDTO restoreContatosById(Integer idContatoProblema) throws Exception;
	public ContatoProblemaDTO restoreContatosById(ContatoProblemaDTO obj) throws Exception;
}
