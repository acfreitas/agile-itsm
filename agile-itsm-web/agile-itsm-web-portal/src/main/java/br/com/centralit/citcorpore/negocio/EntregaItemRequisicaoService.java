package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.EntregaItemRequisicaoDTO;
import br.com.citframework.service.CrudService;
public interface EntregaItemRequisicaoService extends CrudService {
	public Collection findByIdPedido(Integer parm) throws Exception;
	public void deleteByIdPedido(Integer parm) throws Exception;
	public Collection findByIdColetaPreco(Integer parm) throws Exception;
	public void deleteByIdColetaPreco(Integer parm) throws Exception;
	public Collection findByIdItemRequisicaoProduto(Integer parm) throws Exception;
	public void deleteByIdItemRequisicaoProduto(Integer parm) throws Exception;
	public Collection findByIdItemTrabalho(Integer parm) throws Exception;
    public void atualizaInspecao(EntregaItemRequisicaoDTO entregaItemRequisicaoDto) throws Exception;
    public Collection<EntregaItemRequisicaoDTO> findNaoAprovadasByIdSolicitacaoServico(Integer idSolicitacaoServico) throws Exception;
}
