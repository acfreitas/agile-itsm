package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface ReaberturaSolicitacaoService extends CrudService {
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception;
}
