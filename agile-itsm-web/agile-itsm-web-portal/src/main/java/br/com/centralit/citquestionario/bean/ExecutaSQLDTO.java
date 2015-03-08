package br.com.centralit.citquestionario.bean;

import br.com.citframework.dto.IDto;

public class ExecutaSQLDTO implements IDto {
	private Integer value;
	private String description;
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
