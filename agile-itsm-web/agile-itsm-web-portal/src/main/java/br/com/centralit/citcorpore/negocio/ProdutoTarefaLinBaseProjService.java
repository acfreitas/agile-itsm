package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface ProdutoTarefaLinBaseProjService extends CrudService {
	public Collection findByIdTarefaLinhaBaseProjeto(Integer parm) throws Exception;
	public void deleteByIdTarefaLinhaBaseProjeto(Integer parm) throws Exception;
	public Collection findByIdProdutoContrato(Integer parm) throws Exception;
	public void deleteByIdProdutoContrato(Integer parm) throws Exception;
}
