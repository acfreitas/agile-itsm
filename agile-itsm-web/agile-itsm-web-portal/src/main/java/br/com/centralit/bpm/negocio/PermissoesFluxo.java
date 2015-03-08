package br.com.centralit.bpm.negocio;

import br.com.centralit.bpm.config.Config;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;


public class PermissoesFluxo implements IPermissoesFluxo {
	
	private IPermissoesFluxo singleton;

	private IPermissoesFluxo getInstance() throws Exception {
		if (singleton == null) {
			String classe = Config.getPropriedade("classe.permissoes.fluxo");
			try {
				singleton = (IPermissoesFluxo) Class.forName(classe).newInstance();
			} catch (Exception e) {
				throw new Exception("Classe de configuração de permissões de fluxo encontrada");
			}
		}
		return singleton;
	}

	@Override
	public PermissoesFluxoDTO getPermissoesFluxo(GrupoBpmDTO grupoDto, FluxoDTO fluxoDto) throws Exception {
		return getInstance().getPermissoesFluxo(grupoDto, fluxoDto);
	}
	
}
