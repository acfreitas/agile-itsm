package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaRequisicaoLiberacaoDTO;
import br.com.citframework.service.CrudService;

public interface JustificativaRequisicaoLiberacaoService extends CrudService {
	
	/**
	 * Retorna uma lista de justificativa requisição mudança ativas.
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	
	public Collection<JustificativaRequisicaoLiberacaoDTO> listAtivasParaSuspensao() throws Exception;

}
