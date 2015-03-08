package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.AdiantamentoViagemDTO;

public interface AdiantamentoViagemService extends ComplemInfSolicitacaoServicoService{
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Integer recuperaIdAdiantamentoSeExistir(AdiantamentoViagemDTO adiantamentoViagemDto)throws Exception;
	
	/**
	 * Consultar entidade AdiantamentoViagemDTO pelos parametros
	 * 
	 * @param IdSolicitacaoServico
	 * @param idEmpregado
	 * 
	 * @return 
	 * @throws Exception 
	 */
	public AdiantamentoViagemDTO consultarAdiantamentoViagem(Integer IdSolicitacaoServico, Integer idEmpregado) throws Exception;
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public AdiantamentoViagemDTO consultarAdiantamentoViagem(Integer IdSolicitacaoServico, String nomeNaoFunc) throws Exception;
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public AdiantamentoViagemDTO consultarAdiantamentoViagemByIdSolicitacao(Integer IdSolicitacaoServico) throws Exception;
}
