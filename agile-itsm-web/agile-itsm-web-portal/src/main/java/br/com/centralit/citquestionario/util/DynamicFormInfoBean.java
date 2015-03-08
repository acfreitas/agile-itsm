package br.com.centralit.citquestionario.util;

import java.util.Collection;

public class DynamicFormInfoBean {
	private String name;
	private String type;
	private String clazz;
	private String description;
	
	private Class classeForm;
	
	private Collection colElements;
	private Collection colJavaScripts;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection getColElements() {
		return colElements;
	}

	public void setColElements(Collection colElements) {
		this.colElements = colElements;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection getColJavaScripts() {
		return colJavaScripts;
	}

	public void setColJavaScripts(Collection colJavaScripts) {
		this.colJavaScripts = colJavaScripts;
	}

	public Class getClasseForm() {
		return classeForm;
	}

	public void setClasseForm(Class classeForm) {
		this.classeForm = classeForm;
	}
}
