package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface RequisicaoMudancaLiberacaoService extends CrudService {
	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception;
}
