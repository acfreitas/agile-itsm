package br.com.centralit.citcorpore.metainfo.bean;

import br.com.citframework.dto.IDto;

public class ReturnLookupDTO implements IDto {
	private String label;
	private String value;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
