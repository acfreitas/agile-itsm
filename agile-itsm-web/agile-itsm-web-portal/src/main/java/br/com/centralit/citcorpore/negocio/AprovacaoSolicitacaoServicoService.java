package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AprovacaoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;

@SuppressWarnings("rawtypes")
public interface AprovacaoSolicitacaoServicoService extends ComplemInfSolicitacaoServicoService {

	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception;

	public void deleteByIdSolicitacaoServico(Integer parm) throws Exception;

	/**
	 * Retorna AprovacaoSolicitacaoServicoNaoAprovada se existir para a Solicitação de Serviço informada.
	 * 
	 * @param solicitacaoServicoDto
	 * @return AprovacaoSolicitacaoServicoDTO
	 * @author valdoilo.damasceno
	 */
	public AprovacaoSolicitacaoServicoDTO findNaoAprovadaBySolicitacaoServico(SolicitacaoServicoDTO solicitacaoServicoDto);
}
