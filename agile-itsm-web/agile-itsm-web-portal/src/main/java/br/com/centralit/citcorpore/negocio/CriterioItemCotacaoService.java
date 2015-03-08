package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface CriterioItemCotacaoService extends CrudService {
	public Collection findByIdItemCotacao(Integer parm) throws Exception;
	public void deleteByIdItemCotacao(Integer parm) throws Exception;
}
