package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.RegraEscalonamentoDTO;
import br.com.citframework.service.CrudService;

public interface EscalonamentoService extends CrudService {

	Collection findByRegraEscalonamento(RegraEscalonamentoDTO regraEscalonamentoDTO);
	
}
