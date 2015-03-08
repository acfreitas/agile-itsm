package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.citframework.excecao.ServiceException;


/**
 * @author ronnie.lopes
 *
 */
public interface PrestacaoContasViagemService extends ComplemInfSolicitacaoServicoService {
	Integer recuperaIdPrestacaoSeExistir(Integer idSolicitacaoServico, Integer idEmpregado) throws ServiceException, Exception;
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	Integer recuperaIdPrestacaoSeExistir(Integer idSolicitacaoServico, String nomeNaoFunc) throws ServiceException, Exception;
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public PrestacaoContasViagemDTO recuperaCorrecao(PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception;
	public Collection<IntegranteViagemDTO> restoreByIntegranteSolicitacao(IntegranteViagemDTO integrante) throws Exception;
}
