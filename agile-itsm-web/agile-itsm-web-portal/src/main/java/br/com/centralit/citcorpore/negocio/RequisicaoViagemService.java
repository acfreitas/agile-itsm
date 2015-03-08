package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;


public interface RequisicaoViagemService extends ComplemInfSolicitacaoServicoService{
	public Collection<IntegranteViagemDTO> recuperaIntegrantesViagemBySolicitacao(Integer idSolicitacao) throws Exception;
	public RequisicaoViagemDTO recuperaRequisicaoPelaSolicitacao(Integer idSolicitacao) throws Exception;
	public void update( IDto model) throws ServiceException;
}
