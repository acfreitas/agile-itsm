package br.com.citframework.dto;

public class ReflexaoCopyProperty implements IDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5025697250195959285L;
	private String namePropertySource;
	private String namePropertyDest;
	
	public ReflexaoCopyProperty(String namePropSource, String namePropDest){
		this.namePropertySource = namePropSource;
		this.namePropertyDest = namePropDest;
	}

	public String getNamePropertyDest() {
		return namePropertyDest;
	}

	public void setNamePropertyDest(String namePropertyDest) {
		this.namePropertyDest = namePropertyDest;
	}

	public String getNamePropertySource() {
		return namePropertySource;
	}

	public void setNamePropertySource(String namePropertySource) {
		this.namePropertySource = namePropertySource;
	}
}
