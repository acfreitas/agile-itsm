package br.com.centralit.citgerencial.bean;

import br.com.citframework.dto.IDto;

public class GerencialOptionDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6723645025163314269L;
	private String value;
	private String text;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
