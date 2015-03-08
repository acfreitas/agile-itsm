package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

public class PerfilContratoDTO implements IDto {
	private Integer idPerfilContrato;
	private Integer idContrato;
	private String nomePerfilContrato;
	private Double custoHora;
	private Double tempoAlocMinutosTotal;
	private Double custoTotal;
	private Double custoTotalPerfil;
	private String deleted;

	public Integer getIdPerfilContrato(){
		return this.idPerfilContrato;
	}
	public void setIdPerfilContrato(Integer parm){
		this.idPerfilContrato = parm;
	}

	public Integer getIdContrato(){
		return this.idContrato;
	}
	public void setIdContrato(Integer parm){
		this.idContrato = parm;
	}

	public String getNomePerfilContrato(){
		return this.nomePerfilContrato;
	}
	public String getNomePerfilContratoHTMLEncoded(){
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(this.nomePerfilContrato));
	}
	public void setNomePerfilContrato(String parm){
		this.nomePerfilContrato = parm;
	}

	public Double getCustoHora(){
		return this.custoHora;
	}
	public void setCustoHora(Double parm){
		this.custoHora = parm;
	}
	public Double getTempoAlocMinutosTotal() {
		if (tempoAlocMinutosTotal == null){
			return new Double(0);
		}
		return tempoAlocMinutosTotal;
	}
	public String getTempoAlocHorasTotalStr() {
		if (tempoAlocMinutosTotal == null){
			return "";
		}
		double x = tempoAlocMinutosTotal.doubleValue() / 60;
		return UtilFormatacao.formatDouble(x, 2);
	}	
	public String getTempoAlocHorasTotalStrHTMLEncoded() {
		if (tempoAlocMinutosTotal == null){
			return "";
		}
		double x = tempoAlocMinutosTotal.doubleValue() / 60;
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(UtilFormatacao.formatDouble(x, 2)));
	}	
	public void setTempoAlocMinutosTotal(Double tempoAlocMinutosTotal) {
		this.tempoAlocMinutosTotal = tempoAlocMinutosTotal;
	}
	public Double getCustoTotal() {
		return custoTotal;
	}
	public void setCustoTotal(Double custoTotal) {
		this.custoTotal = custoTotal;
	}
	public Double getCustoTotalPerfil() {
		if (custoTotalPerfil == null){
			return new Double(0);
		}
		return custoTotalPerfil;
	}
	public String getCustoTotalPerfilStr() {
		if (custoTotalPerfil == null){
			return "";
		}
		return UtilFormatacao.formatDouble(custoTotalPerfil,2);
	}	
	public String getCustoTotalPerfilStrHTMLEncoded() {
		if (custoTotalPerfil == null){
			return "";
		}
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(UtilFormatacao.formatDouble(custoTotalPerfil,2)));
	}
	public void setCustoTotalPerfil(Double custoTotalPerfil) {
		this.custoTotalPerfil = custoTotalPerfil;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}
