package br.com.centralit.citquestionario.bean;

import br.com.citframework.dto.IDto;

public class RespostaItemAuxiliarDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1130094089892704974L;
	private String fieldName;
	private String fieldValue;
	private boolean multiple;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public boolean isMultiple() {
		return multiple;
	}
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
}
