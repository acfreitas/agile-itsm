package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.citframework.service.CrudService;

public interface IntegranteViagemService extends CrudService{
	
	public IntegranteViagemDTO findById(Integer idIntegranteViagem) throws Exception;
	
	public void atualizarIdItemTrabalho(Integer idTarefa, Integer idSolicitacaoServico);
	public void atualizarRemarcacaoDoIntegrante (Integer idIntegrante);
	
	public Collection<IntegranteViagemDTO> findAllRemarcacaoByIdSolicitacao(Integer idSolicitacaoServico);
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public IntegranteViagemDTO recuperaIntegranteFuncionario(Integer idsolicitacaoServico,Integer idEmpregado) throws Exception;
	public IntegranteViagemDTO recuperaIntegranteNaoFuncionario(Integer idsolicitacaoServico, String nomeNaoFuncionario) throws Exception;
	public Collection<IntegranteViagemDTO> recuperaIntegrantesRemarcacao(IntegranteViagemDTO integranteViagemDTO, String eOu) throws Exception;
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection<IntegranteViagemDTO> recuperaIntegrantesViagemByCompras(Integer idSolicitacaoServico) throws Exception;
	public Collection<IntegranteViagemDTO> recuperaIntegrantesViagemByIdSolicitacao(Integer idSolicitacaoServico) throws Exception;
	public Collection<IntegranteViagemDTO> recuperaIntegrantesViagemByIdSolicitacaoEstado(Integer idSolicitacao, String estado) throws Exception;
	public IntegranteViagemDTO getIntegranteByIdSolicitacaoAndTarefa(Integer idsolicitacaoServico, Integer idTarefa) throws Exception;
}