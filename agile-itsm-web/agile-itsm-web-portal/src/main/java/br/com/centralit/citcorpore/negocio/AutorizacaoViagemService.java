package br.com.centralit.citcorpore.negocio;

import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;

public interface AutorizacaoViagemService extends ComplemInfSolicitacaoServicoService {

	/**
	 * Retorna as informacoes da solicitacao (@param: solicitacaoDTO) que serao exibidas na tela do mobile 
	 * 
	 * @param solicitacaoDto
	 * @param itemTrabalho
	 * 
	 */
	public String getInformacoesComplementaresFmtTexto(SolicitacaoServicoDTO solicitacaoDto, ItemTrabalho itemTrabalho) throws Exception;
	
}