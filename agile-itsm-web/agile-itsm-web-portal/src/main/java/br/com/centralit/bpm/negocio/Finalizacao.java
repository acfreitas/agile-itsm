package br.com.centralit.bpm.negocio;

import java.util.List;

import br.com.centralit.bpm.util.Enumerados.TipoFinalizacao;

public class Finalizacao extends ItemTrabalho {

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
		if (!bTodosExecutados) 
			return null;
			
		if (elementoFluxoDto.getSubTipo() != null && elementoFluxoDto.getSubTipo().equals(TipoFinalizacao.Encadeada.name())) {
			if (elementoFluxoDto.getNomeFluxoEncadeado() != null && elementoFluxoDto.getNomeFluxoEncadeado().trim().length() > 0) {
				ExecucaoFluxo novoFluxo = ExecucaoFluxo.getInstancia(instanciaFluxo.getExecucaoFluxo());
				novoFluxo.inicia(elementoFluxoDto.getNomeFluxoEncadeado(), null);
			}
		}
	    instanciaFluxo.verificaSLA(this);
		instanciaFluxo.getExecucaoFluxo().encerra();
		return null;
	}
	
}
