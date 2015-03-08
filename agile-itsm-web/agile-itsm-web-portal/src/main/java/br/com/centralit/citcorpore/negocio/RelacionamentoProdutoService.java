package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface RelacionamentoProdutoService extends CrudService {
	public Collection findByIdTipoProduto(Integer parm) throws Exception;
	public void deleteByIdTipoProduto(Integer parm) throws Exception;
}
