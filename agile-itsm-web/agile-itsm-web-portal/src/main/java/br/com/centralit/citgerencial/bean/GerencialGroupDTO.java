package br.com.centralit.citgerencial.bean;

import br.com.citframework.dto.IDto;

public class GerencialGroupDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4933718908367971403L;
	private GerencialFieldDTO gerencialField;
	private String spacingLeft;
	private String fieldName;
	public GerencialFieldDTO getGerencialField() {
		return gerencialField;
	}
	public void setGerencialField(GerencialFieldDTO gerencialField) {
		this.gerencialField = gerencialField;
	}
	public String getSpacingLeft() {
		return spacingLeft;
	}
	public void setSpacingLeft(String spacingLeft) {
		this.spacingLeft = spacingLeft;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
