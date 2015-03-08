package br.com.centralit.citcorpore.util;

public class ImportInfoField {
	private String nameField;
	private boolean key;
	private boolean sequence;
	private boolean check;
	private boolean exclusion;
	private String type;
	private String valueField;
	
	public String getNameField() {
		return nameField;
	}
	public void setNameField(String nameField) {
		this.nameField = nameField;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	public boolean isKey() {
		return key;
	}
	public void setKey(boolean key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isSequence() {
		return sequence;
	}
	public void setSequence(boolean sequence) {
		this.sequence = sequence;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public boolean isExclusion() {
		return exclusion;
	}
	public void setExclusion(boolean exclusion) {
		this.exclusion = exclusion;
	}
	
}
