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
		String[] nivelStr = new String[]{"","N�o tem","Intermedi�ria","Boa","Avan�ada"};
		return nivelStr[nivel];		
	}
	public String getDetalhamentoNivel() {
		String strNivel = "<table width='100%'><tr><td class='celulaGrid'><b>Escrita: </b>" + getNivelStr(this.idNivelEscrita) + "</td></tr>";
		strNivel += "<tr><td class='celulaGrid'><b>Leitura: </b>" + getNivelStr(this.idNivelLeitura) + "</td></tr>";
		strNivel += "<tr><td class='celulaGrid'><b>Conversa��o: </b>" + getNivelStr(this.idNivelConversa) + "</td></tr>";
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
			descIdNivelLeitura = "N�o tem";
			if(this.idNivelLeitura.intValue() == 2){
				descIdNivelLeitura = "Intermedi�ria";
			}
			}if(this.idNivelLeitura.intValue() == 3){
				descIdNivelLeitura = "Boa";
			}
			if(this.idNivelLeitura.intValue() == 4){
				descIdNivelLeitura ="Avan�ada";
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
			descIdNivelConversa = "N�o tem";
			if(this.idNivelConversa.intValue() == 2){
				descIdNivelConversa = "Intermedi�ria";
			}
			}if(this.idNivelConversa.intValue() == 3){
				descIdNivelConversa = "Boa";
			}
			if(this.idNivelConversa.intValue() == 4){
				descIdNivelConversa ="Avan�ada";
			}
	}
	public Integer getIdNivelEscrita() {
		return idNivelEscrita;
	}
	public void setIdNivelEscrita(Integer idNivelEscrita) {
		this.idNivelEscrita = idNivelEscrita;
		if (this.idNivelEscrita.intValue() == 1){
			descIdNivelEscrita = "N�o tem";
			if(this.idNivelEscrita.intValue() == 2){
				descIdNivelEscrita = "Intermedi�ria";
			}
			}if(this.idNivelEscrita.intValue() == 3){
				descIdNivelEscrita = "Boa";
			}
			if(this.idNivelEscrita.intValue() == 4){
				descIdNivelEscrita ="Avan�ada";
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