package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface FornecedorCotacaoService extends CrudService {
	public Collection findByIdCotacao(Integer parm) throws Exception;
	public void deleteByIdCotacao(Integer parm) throws Exception;
	public Collection findByIdFornecedor(Integer parm) throws Exception;
}
