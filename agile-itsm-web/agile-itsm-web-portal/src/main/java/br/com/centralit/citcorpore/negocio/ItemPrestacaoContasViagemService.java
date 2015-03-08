package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemPrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.citframework.service.CrudService;

/**
 * @author ronnie.lopes
 *
 */
public interface ItemPrestacaoContasViagemService extends CrudService {
	
	public Collection<ItemPrestacaoContasViagemDTO> recuperaItensPrestacao(PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception;
	
}
