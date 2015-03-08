package br.com.centralit.citcorpore.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class UtilCalculo {
	/**
	 * Retorna o total da Expressão passada.
	 * Por exemplo: ao passar, (10*10*(5/2)), ele retorna 250.
	 * 
	 * @param expressao
	 * @return Double
	 * @author renato.jesus
	 */
	public static Double calculaExpressao(String expressao) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");

		Double resultado = 0.0;
		
		if(expressao.isEmpty() || expressao == null) {
			return resultado;
		}
		
		try {
			resultado = (Double) engine.eval(expressao);
		} catch (ScriptException e) {
			e.printStackTrace();
		}

		return resultado;
	}
}
