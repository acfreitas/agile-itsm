package br.com.centralit.citcorpore.bpm;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.bpm.negocio.IPermissoesFluxo;
import br.com.centralit.citcorpore.integracao.PermissoesFluxoDao;

public class BPMPermissoesFluxo implements IPermissoesFluxo {

	@Override
	public PermissoesFluxoDTO getPermissoesFluxo(GrupoBpmDTO grupoDto, FluxoDTO fluxoDto) throws Exception {
		PermissoesFluxoDTO permissoesFluxoDto = new PermissoesFluxoDTO();
		permissoesFluxoDto.setIdTipoFluxo(fluxoDto.getIdTipoFluxo());
		permissoesFluxoDto.setIdGrupo(grupoDto.getIdGrupo());
		return (PermissoesFluxoDTO) new PermissoesFluxoDao().restore(permissoesFluxoDto);
	}
	
}
