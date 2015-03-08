package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.RoteiroViagemDTO;


public interface RoteiroViagemService extends ComplemInfSolicitacaoServicoService {
	public RoteiroViagemDTO findByIdIntegrante(Integer idIntegrante) throws Exception;
	public Collection<RoteiroViagemDTO> findByIdIntegranteHistorico(Integer idIntegrante) throws Exception;
	public Collection<RoteiroViagemDTO> findByIdIntegranteOriginal(Integer idIntegrante) throws Exception;
	public Collection<RoteiroViagemDTO> findByIdIntegranteTodos(Integer idIntegrante) throws Exception;
}
