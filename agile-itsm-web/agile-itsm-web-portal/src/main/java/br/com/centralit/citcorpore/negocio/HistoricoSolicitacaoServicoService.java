package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.HistoricoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.RelatorioCargaHorariaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.citframework.service.CrudService;

public interface HistoricoSolicitacaoServicoService extends CrudService {

			
	 public boolean findHistoricoSolicitacao(Integer idSolicitacaoServico) throws Exception;
	 
	 public Collection<HistoricoSolicitacaoServicoDTO> restoreHistoricoServico(Integer idSolicitacaoServico) throws Exception;
	 
	 public Collection<HistoricoSolicitacaoServicoDTO> findResponsavelAtual(Integer idSolicitacaoServico) throws Exception;
	 
	 public Collection<RelatorioCargaHorariaDTO> imprimirCargaHorariaUsuario(SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception;
	 
	 public Collection<RelatorioCargaHorariaDTO> imprimirCargaHorariaGrupo(SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception;
	 
	 public Collection<SolicitacaoServicoDTO> imprimirSolicitacaoEncaminhada(SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception;
	 
	 public Collection<SolicitacaoServicoDTO> imprimirSolicitacaoEncaminhadaFilhas(SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception;

}
