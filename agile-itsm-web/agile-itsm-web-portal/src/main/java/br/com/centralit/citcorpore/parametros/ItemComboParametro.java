package br.com.centralit.citcorpore.parametros;

import java.io.Serializable;

public class ItemComboParametro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3529600423181240014L;
	private String value;
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
