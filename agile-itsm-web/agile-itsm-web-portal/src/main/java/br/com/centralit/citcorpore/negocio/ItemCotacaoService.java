package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemCotacaoDTO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;
public interface ItemCotacaoService extends CrudService {
	public Collection findByIdCotacao(Integer parm) throws Exception;
	public void deleteByIdCotacao(Integer parm) throws Exception;
	public Collection findByIdItemRequisicaoProduto(Integer parm) throws Exception;
	public void deleteByIdItemRequisicaoProduto(Integer parm) throws Exception;
	public void valida(TransactionControler tc, ItemCotacaoDTO itemCotacaoDto) throws Exception;
}
