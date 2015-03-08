package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface SolicitacaoServicoEvtMonService extends CrudService {
	public Collection findByIdRecursoAndSolicitacaoAberta(Integer idRecurso) throws Exception;
	public Collection findByIdSolicitacao(Integer idSolicitacaoServico) throws Exception;
}
