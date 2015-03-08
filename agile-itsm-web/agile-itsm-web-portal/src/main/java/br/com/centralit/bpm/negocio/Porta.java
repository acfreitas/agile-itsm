package br.com.centralit.bpm.negocio;

import java.util.List;

public class Porta extends ItemTrabalho{

	public List<ItemTrabalho> resolve() throws Exception {
		SequenciaFluxo sequenciaFluxo = new SequenciaFluxo(instanciaFluxo);
		boolean bTodosExecutados = true;
		List<ItemTrabalho> origens = sequenciaFluxo.getOrigens(this);
		if (origens != null) {
			for (ItemTrabalho itemOrigem : origens) { 
				if (!itemOrigem.finalizado()) {
					bTodosExecutados = false;
					break;
				}
			}
		}
		if (bTodosExecutados) 
			return sequenciaFluxo.getDestinos(this);
		else
			return null;
	}
	
}
