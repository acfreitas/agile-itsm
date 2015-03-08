package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface TabFederacaoDadosService extends CrudService {
	public Collection findByNomeTabela(String parm) throws Exception;
	public void deleteByNomeTabela(String parm) throws Exception;
	public Collection findByChaveFinal(String parm) throws Exception;
	public void deleteByChaveFinal(String parm) throws Exception;
	public Collection findByChaveOriginal(String parm) throws Exception;
	public void deleteByChaveOriginal(String parm) throws Exception;
	public Collection findByOrigem(String parm) throws Exception;
	public void deleteByOrigem(String parm) throws Exception;
}
