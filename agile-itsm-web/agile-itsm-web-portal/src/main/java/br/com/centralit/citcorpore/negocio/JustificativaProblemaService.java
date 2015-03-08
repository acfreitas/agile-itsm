package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaProblemaDTO;
import br.com.citframework.service.CrudService;

public interface JustificativaProblemaService extends CrudService {
	
	/**
	 * Retorna uma lista de justificativa problema ativas.
	 * @return
	 * @throws Exception
	 * @author david.junior
	 */
	
	public Collection<JustificativaProblemaDTO> listAtivasParaSuspensao() throws Exception;

}
