package br.com.centralit.bpm.negocio;

import br.com.centralit.bpm.util.UtilScript;

public class Condicao extends NegocioBpm{
	
	private InstanciaFluxo instanciaFluxo;
	private String script;
	
	public Condicao(InstanciaFluxo instanciaFluxo, String script, String nome) {
		this.instanciaFluxo = instanciaFluxo;
		setTransacao(instanciaFluxo.getTransacao());
		this.script = script;
	}
	
	public boolean executa() throws Exception {
		if (script == null || script.trim().length() == 0)
			return true;

        return (Boolean) UtilScript.executaScript("condição", script, this.getInstanciaFluxo().getObjetos());
	}

	public InstanciaFluxo getInstanciaFluxo() {
		return instanciaFluxo;
	}

}
