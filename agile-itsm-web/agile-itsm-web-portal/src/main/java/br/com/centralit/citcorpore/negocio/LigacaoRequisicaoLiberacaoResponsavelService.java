package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface LigacaoRequisicaoLiberacaoResponsavelService extends CrudService {
	public Collection findByIdLiberacao(Integer parm) throws Exception;
}
