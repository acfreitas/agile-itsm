package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoProblemaDTO;
import br.com.citframework.service.CrudService;
@SuppressWarnings("rawtypes")
public interface SolicitacaoServicoProblemaService  extends CrudService {
	
	
	public Collection findByIdProblema(Integer parm) throws Exception;
	
	public SolicitacaoServicoProblemaDTO restoreByIdProblema(Integer idProblema) throws Exception;

}
