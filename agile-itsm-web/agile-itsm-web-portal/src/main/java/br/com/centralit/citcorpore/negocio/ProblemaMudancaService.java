package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface ProblemaMudancaService extends CrudService {
	public Collection findByIdProblemaMudanca(Integer parm) throws Exception;

	public void deleteByIdProblemaMudanca(Integer parm) throws Exception;

	public Collection findByIdProblema(Integer parm) throws Exception;

	public void deleteByIdProblema(Integer parm) throws Exception;

	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception;

	public void deleteByIdRequisicaoMudanca(Integer parm) throws Exception;
	
	public ProblemaMudancaDTO restoreByIdProblema(Integer idProblema) throws Exception;
}
