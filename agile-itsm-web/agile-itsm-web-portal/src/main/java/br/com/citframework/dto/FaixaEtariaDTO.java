package br.com.citframework.dto;

public class FaixaEtariaDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer inicio;
	private Integer fim;
	public Integer getInicio() {
		return inicio;
	}
	public void setInicio(Integer inicio) {
		this.inicio = inicio;
	}
	public Integer getFim() {
		return fim;
	}
	public void setFim(Integer fim) {
		this.fim = fim;
	}
	public String getDescricao() {
		if (this.getInicio() == null){
			this.setInicio(new Integer(0));
		}
		if (this.getFim() == null){
			this.setFim(new Integer(999));
		}
		String strRet = "";
		
		if (this.getInicio().intValue() <= 0){
			strRet += "Até ";
		}else{
			strRet += this.getInicio() + " a ";
		}
		if (this.getFim().intValue() >= 150){
			strRet += "+";
		}else{
			strRet += this.getFim() + "";
		}
		return strRet;
	}
}
