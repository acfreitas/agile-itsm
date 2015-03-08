package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

public class MarcoPagamentoPrjDTO implements IDto {
	private Integer idMarcoPagamentoPrj;
	private Integer idProjeto;
	private String nomeMarcoPag;
	private java.sql.Date dataPrevisaoPag;
	private Double valorPagamento;
	private String situacao;
	private java.sql.Date dataUltAlteracao;
	private String horaUltAlteracao;
	private String usuarioUltAlteracao;
	
	private Collection colPerfisByMarcosFin;
	private Collection colProdutosByMarcosFin;
	
	private Double tempoAlocMinutosTotal;
	
	private Double custoPerfil;
	
	private String id;

	public Integer getIdMarcoPagamentoPrj(){
		return this.idMarcoPagamentoPrj;
	}
	public void setIdMarcoPagamentoPrj(Integer parm){
		this.idMarcoPagamentoPrj = parm;
	}

	public Integer getIdProjeto(){
		return this.idProjeto;
	}
	public void setIdProjeto(Integer parm){
		this.idProjeto = parm;
	}

	public String getNomeMarcoPag(){
		return this.nomeMarcoPag;
	}
	public String getNomeMarcoPagHTMLEncoded(){
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(this.nomeMarcoPag));
	}
	public void setNomeMarcoPag(String parm){
		this.nomeMarcoPag = parm;
	}

	public java.sql.Date getDataPrevisaoPag(){
		return this.dataPrevisaoPag;
	}
	public void setDataPrevisaoPag(java.sql.Date parm){
		this.dataPrevisaoPag = parm;
	}

	public Double getValorPagamento(){
		return this.valorPagamento;
	}
	public void setValorPagamento(Double parm){
		this.valorPagamento = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}

	public java.sql.Date getDataUltAlteracao(){
		return this.dataUltAlteracao;
	}
	public void setDataUltAlteracao(java.sql.Date parm){
		this.dataUltAlteracao = parm;
	}

	public String getHoraUltAlteracao(){
		return this.horaUltAlteracao;
	}
	public void setHoraUltAlteracao(String parm){
		this.horaUltAlteracao = parm;
	}

	public String getUsuarioUltAlteracao(){
		return this.usuarioUltAlteracao;
	}
	public void setUsuarioUltAlteracao(String parm){
		this.usuarioUltAlteracao = parm;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getCustoPerfil() {
		return custoPerfil;
	}
	public void setCustoPerfil(Double custoPerfil) {
		this.custoPerfil = custoPerfil;
	}
	public Collection getColPerfisByMarcosFin() {
		return colPerfisByMarcosFin;
	}
	public void setColPerfisByMarcosFin(Collection colPerfisByMarcosFin) {
		this.colPerfisByMarcosFin = colPerfisByMarcosFin;
	}
	public Collection getColProdutosByMarcosFin() {
		return colProdutosByMarcosFin;
	}
	public void setColProdutosByMarcosFin(Collection colProdutosByMarcosFin) {
		this.colProdutosByMarcosFin = colProdutosByMarcosFin;
	}
	public Double getTempoAlocMinutosTotal() {
		return tempoAlocMinutosTotal;
	}
	public void setTempoAlocMinutosTotal(Double tempoAlocMinutosTotal) {
		this.tempoAlocMinutosTotal = tempoAlocMinutosTotal;
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
}
