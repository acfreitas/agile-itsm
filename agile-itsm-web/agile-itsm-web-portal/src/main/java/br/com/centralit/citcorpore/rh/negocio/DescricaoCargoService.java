package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.negocio.ComplemInfSolicitacaoServicoService;
import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;

public interface DescricaoCargoService extends ComplemInfSolicitacaoServicoService {
	public DescricaoCargoDTO findByIdSolicitacaoServico(Integer parm) throws Exception;
}
