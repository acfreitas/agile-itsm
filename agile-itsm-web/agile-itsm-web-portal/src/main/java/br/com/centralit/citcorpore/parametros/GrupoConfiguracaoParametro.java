package br.com.centralit.citcorpore.parametros;

import java.io.Serializable;

public class GrupoConfiguracaoParametro implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2107298721007695168L;
	private String name;
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
