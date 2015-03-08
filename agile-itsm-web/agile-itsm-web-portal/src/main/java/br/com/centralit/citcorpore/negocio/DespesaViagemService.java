
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.DespesaViagemDTO;


public interface DespesaViagemService extends ComplemInfSolicitacaoServicoService {
	public Collection<DespesaViagemDTO> findDespesaViagemByIdRoteiro(Integer idRoteiro) throws Exception;
	public Double buscarDespesaTotalViagem(Integer idSolicitacao) throws Exception;
	public Double getTotalDespesaViagemPrestacaoContas(Integer idSolicitacao, Integer idEmpregado) throws Exception;
	public Collection<DespesaViagemDTO> findDespesaViagemByIdRoteiroAndPermiteAdiantamento(Integer idRoteiro, String permiteAdiantamento) throws Exception;
	public Collection<DespesaViagemDTO> findDespesasAtivasViagemByIdRoteiro(Integer idRoteiro) throws Exception;
	public Collection<DespesaViagemDTO> findTodasDespesasViagemByIdRoteiro(Integer idRoteiro) throws Exception;
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection<DespesaViagemDTO> findHitoricoDespesaViagemByIdRoteiro(Integer idRoteiro) throws Exception;
	public Double buscaValorTotalViagemAtivo(Integer idSolicitacao) throws Exception;
	public Double buscaValorTotalViagemInativo(Integer idSolicitacao) throws Exception;
	public Double buscaValorViagemHistorico(Integer idSolicitacao) throws Exception;
}
