package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;

import br.com.citframework.service.CrudService;

public interface RequisitoSLAService extends CrudService {
	public Collection findByIdEmpregado(Integer parm) throws Exception;
	public void deleteByIdEmpregado(Integer parm) throws Exception;
	public Collection findById(Integer idRequisitoSla) throws Exception;
	String verificaIdSolicitante(HashMap mapFields) throws Exception;
}
