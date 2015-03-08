package br.com.centralit.citgerencial.bean;

import java.util.Collection;
import java.util.HashMap;

public abstract class GerencialGenerateServiceBuffer {
	/**
	 * O retorno deste metodo deve ser uma String onde represente o conteudo do relatorio.
	 * 		Exemplo: Object[] row
	 * @param parametersValues
	 * @param paramtersDefinition
	 * @return
	 */
	public abstract String execute(HashMap parametersValues, Collection paramtersDefinition) throws Exception;
}
