package br.com.centralit.citcorpore.metainfo.bean;

import br.com.citframework.dto.IDto;

public class ExternalClassDTO implements IDto {
	private String nameJar;
	private String nameJarOriginal;
	private String nameClass;
	public String getNameJar() {
		return nameJar;
	}
	public void setNameJar(String nameJar) {
		this.nameJar = nameJar;
	}
	public String getNameClass() {
		return nameClass;
	}
	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}
	public String getNameJarOriginal() {
		return nameJarOriginal;
	}
	public void setNameJarOriginal(String nameJarOriginal) {
		this.nameJarOriginal = nameJarOriginal;
	}
}
