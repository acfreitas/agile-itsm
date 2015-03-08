package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface RequisicaoMudancaResponsavelService extends CrudService {
	public Collection findByIdMudanca(Integer parm) throws Exception;
	public void deleteByIdMudanca(Integer parm) throws Exception;
	public Collection findByIdMudancaEDataFim(Integer parm) throws Exception;
}
