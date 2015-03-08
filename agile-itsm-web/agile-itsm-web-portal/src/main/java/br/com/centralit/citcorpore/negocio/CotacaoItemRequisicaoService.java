package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface CotacaoItemRequisicaoService extends CrudService {
    public Collection findByIdCotacao(Integer parm) throws Exception;
	public Collection findByIdColetaPreco(Integer parm) throws Exception;
	public void deleteByIdColetaPreco(Integer parm) throws Exception;
	public Collection findByIdItemRequisicaoProduto(Integer parm) throws Exception;
	public void deleteByIdItemRequisicaoProduto(Integer parm) throws Exception;
	public Collection findByIdRequisicaoProduto(Integer parm) throws Exception;
	public Collection findByIdItemTrabalho(Integer parm) throws Exception;
	public Collection findPendentesByIdCotacao(Integer idCotacao) throws Exception;
}
