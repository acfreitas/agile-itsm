package br.com.centralit.citcorpore.negocio;
import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.citframework.service.CrudService;
public interface ItemRequisicaoProdutoService extends CrudService {
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception;
	public void deleteByIdSolicitacaoServico(Integer parm) throws Exception;
	public Collection findByIdProduto(Integer parm) throws Exception;
	public Collection findByIdSolicitacaoAndSituacaoAndTipoAtendimento(Integer parm,  SituacaoItemRequisicaoProduto[] situacao, String tipoAtendimento) throws Exception;
	public Collection findByIdSolicitacaoServicoAndSituacao(Integer parm, SituacaoItemRequisicaoProduto[] situacao) throws Exception;
    public Collection<ItemRequisicaoProdutoDTO> recuperaItensParaCotacao(Date dataInicio, Date dataFim, Integer idCentroCusto,
            Integer idProjeto, Integer idEnderecoEntrega, Integer idSolicitacaoServico) throws Exception;
    public Collection findByIdItemCotacao(Integer parm) throws Exception;
}
