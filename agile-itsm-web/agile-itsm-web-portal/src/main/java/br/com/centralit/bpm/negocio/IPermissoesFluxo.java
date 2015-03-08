package br.com.centralit.bpm.negocio;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;

public interface IPermissoesFluxo {
	
	public PermissoesFluxoDTO getPermissoesFluxo(GrupoBpmDTO grupoDto, FluxoDTO fluxoDto) throws Exception;
	
}
