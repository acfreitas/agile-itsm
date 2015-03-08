package br.com.centralit.bpm.negocio;

import java.util.List;

public class Inicio extends ItemTrabalho {

	public List<ItemTrabalho> resolve() throws Exception {
		SequenciaFluxo sequenciaFluxo = new SequenciaFluxo(instanciaFluxo);
		return sequenciaFluxo.getDestinos(this);
	}
	
}
