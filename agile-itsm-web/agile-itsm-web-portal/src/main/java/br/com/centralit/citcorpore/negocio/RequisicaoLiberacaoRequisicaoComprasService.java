package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoRequisicaoComprasDTO;
import br.com.citframework.service.CrudService;
public interface RequisicaoLiberacaoRequisicaoComprasService extends CrudService {
	public Collection findByIdLiberacao(Integer parm) throws Exception;
	public Collection findByIdLiberacaoAndDataFim(Integer idRequisicaoLiberacao) throws Exception;
	public void deleteByIdLiberacao(Integer parm) throws Exception;
	
	public RequisicaoLiberacaoRequisicaoComprasDTO carregaItemRequisicaoComprasByidItemRequisicaProduto(Integer idItemrRequisicaoProduto) throws Exception;
	
	
}
	
