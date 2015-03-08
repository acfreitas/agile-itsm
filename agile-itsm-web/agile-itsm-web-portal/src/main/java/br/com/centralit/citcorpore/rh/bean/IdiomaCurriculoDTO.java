package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;


public class IdiomaCurriculoDTO implements IDto {
	private Integer idIdioma;
	private Integer idCurriculo;
	private Integer idNivelConversa;
	private Integer idNivelEscrita;
	private Integer idNivelLeitura;
	private String  descIdNivelLeitura;
	private String  descIdNivelEscrita;
	private String  descIdNivelConversa;
	
	private String descricaoIdioma;
	
	public String getDescricaoIdioma() {
		return descricaoIdioma;
	}
	public void setDescricaoIdioma(String descricaoIdioma) {
		this.descricaoIdioma = descricaoIdioma;
	}
	private String getNivelStr(Integer nivel) {
		if (nivel == null)
			return "";
		String[] nivelStr = new String[]{"","Não tem","Intermediária","Boa","Avançada"};
		return nivelStr[nivel];		
	}
	public String getDetalhamentoNivel() {
		String strNivel = "<table width='100%'><tr><td class='celulaGrid'><b>Escrita: </b>" + getNivelStr(this.idNivelEscrita) + "</td></tr>";
		strNivel += "<tr><td class='celulaGrid'><b>Leitura: </b>" + getNivelStr(this.idNivelLeitura) + "</td></tr>";
		strNivel += "<tr><td class='celulaGrid'><b>Conversação: </b>" + getNivelStr(this.idNivelConversa) + "</td></tr>";
		strNivel += "</table>";
		return strNivel;
	}
	public Integer getIdIdioma() {
		return idIdioma;
	}
	public void setIdIdioma(Integer idIdioma) {
		this.idIdioma = idIdioma;
	}
	public Integer getIdNivelLeitura() {
		return idNivelLeitura;
	}
	public void setIdNivelLeitura(Integer idNivelLeitura) {
		this.idNivelLeitura = idNivelLeitura;
		if (this.idNivelLeitura.intValue() == 1){
			descIdNivelLeitura = "Não tem";
			if(this.idNivelLeitura.intValue() == 2){
				descIdNivelLeitura = "Intermediária";
			}
			}if(this.idNivelLeitura.intValue() == 3){
				descIdNivelLeitura = "Boa";
			}
			if(this.idNivelLeitura.intValue() == 4){
				descIdNivelLeitura ="Avançada";
			}
	}
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	public Integer getIdNivelConversa() {
		return idNivelConversa;
	}
	public void setIdNivelConversa(Integer idNivelConversa) {
		this.idNivelConversa = idNivelConversa;
		if (this.idNivelConversa.intValue() == 1){
			descIdNivelConversa = "Não tem";
			if(this.idNivelConversa.intValue() == 2){
				descIdNivelConversa = "Intermediária";
			}
			}if(this.idNivelConversa.intValue() == 3){
				descIdNivelConversa = "Boa";
			}
			if(this.idNivelConversa.intValue() == 4){
				descIdNivelConversa ="Avançada";
			}
	}
	public Integer getIdNivelEscrita() {
		return idNivelEscrita;
	}
	public void setIdNivelEscrita(Integer idNivelEscrita) {
		this.idNivelEscrita = idNivelEscrita;
		if (this.idNivelEscrita.intValue() == 1){
			descIdNivelEscrita = "Não tem";
			if(this.idNivelEscrita.intValue() == 2){
				descIdNivelEscrita = "Intermediária";
			}
			}if(this.idNivelEscrita.intValue() == 3){
				descIdNivelEscrita = "Boa";
			}
			if(this.idNivelEscrita.intValue() == 4){
				descIdNivelEscrita ="Avançada";
			}
	}
	public String getDescIdNivelLeitura() {
		return descIdNivelLeitura;
	}
	public void setDescIdNivelLeitura(String descIdNivelLeitura) {
		this.descIdNivelLeitura = descIdNivelLeitura;
	}
	public String getDescIdNivelEscrita() {
		return descIdNivelEscrita;
	}
	public void setDescIdNivelEscrita(String descIdNivelEscrita) {
		this.descIdNivelEscrita = descIdNivelEscrita;
	}
	public String getDescIdNivelConversa() {
		return descIdNivelConversa;
	}
	public void setDescIdNivelConversa(String descIdNivelConversa) {
		this.descIdNivelConversa = descIdNivelConversa;
	}	
	
}