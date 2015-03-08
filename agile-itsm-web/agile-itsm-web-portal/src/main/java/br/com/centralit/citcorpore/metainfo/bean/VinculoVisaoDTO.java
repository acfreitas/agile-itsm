package br.com.centralit.citcorpore.metainfo.bean;

import br.com.citframework.dto.IDto;

public class VinculoVisaoDTO implements IDto {
	public static String VINCULO_1_TO_N = "1";
	public static String VINCULO_N_TO_N = "2";
	
	private Integer idVisaoRelacionada;
	private Integer seq;
	private String tipoVinculo;
	private Integer idGrupoVisaoPai;
	private Integer idGrupoVisaoFilho;
	private Integer idCamposObjetoNegocioPai;
	private Integer idCamposObjetoNegocioFilho;
	private Integer idCamposObjetoNegocioPaiNN;
	private Integer idCamposObjetoNegocioFilhoNN;
	private String nomeTabelaPai;
	private String nomeTabelaPaiNN;
	private String nomeTabelaFilho;
	private String nomeTabelaFilhoNN;
	
	private String nomeDBPai;
	private String nomeDBPaiNN;
	private String nomeDBPaiFilho;
	private String nomeDBPaiFilhoNN;
	
	private String controle;

	public Integer getIdVisaoRelacionada(){
		return this.idVisaoRelacionada;
	}
	public void setIdVisaoRelacionada(Integer parm){
		this.idVisaoRelacionada = parm;
	}

	public Integer getSeq(){
		return this.seq;
	}
	public void setSeq(Integer parm){
		this.seq = parm;
	}

	public String getTipoVinculo(){
		return this.tipoVinculo;
	}
	public void setTipoVinculo(String parm){
		this.tipoVinculo = parm;
	}

	public Integer getIdGrupoVisaoPai(){
		return this.idGrupoVisaoPai;
	}
	public void setIdGrupoVisaoPai(Integer parm){
		this.idGrupoVisaoPai = parm;
	}

	public Integer getIdCamposObjetoNegocioPai(){
		return this.idCamposObjetoNegocioPai;
	}
	public void setIdCamposObjetoNegocioPai(Integer parm){
		this.idCamposObjetoNegocioPai = parm;
	}

	public Integer getIdGrupoVisaoFilho(){
		return this.idGrupoVisaoFilho;
	}
	public void setIdGrupoVisaoFilho(Integer parm){
		this.idGrupoVisaoFilho = parm;
	}

	public Integer getIdCamposObjetoNegocioFilho(){
		return this.idCamposObjetoNegocioFilho;
	}
	public void setIdCamposObjetoNegocioFilho(Integer parm){
		this.idCamposObjetoNegocioFilho = parm;
	}

	public Integer getIdCamposObjetoNegocioPaiNN(){
		return this.idCamposObjetoNegocioPaiNN;
	}
	public void setIdCamposObjetoNegocioPaiNN(Integer parm){
		this.idCamposObjetoNegocioPaiNN = parm;
	}

	public Integer getIdCamposObjetoNegocioFilhoNN(){
		return this.idCamposObjetoNegocioFilhoNN;
	}
	public void setIdCamposObjetoNegocioFilhoNN(Integer parm){
		this.idCamposObjetoNegocioFilhoNN = parm;
	}
	public String getControle() {
		return controle;
	}
	public void setControle(String controle) {
		this.controle = controle;
	}
	public String getNomeDBPai() {
	    return nomeDBPai;
	}
	public void setNomeDBPai(String nomeDBPai) {
	    this.nomeDBPai = nomeDBPai;
	}
	public String getNomeDBPaiFilhoNN() {
	    return nomeDBPaiFilhoNN;
	}
	public void setNomeDBPaiFilhoNN(String nomeDBPaiFilhoNN) {
	    this.nomeDBPaiFilhoNN = nomeDBPaiFilhoNN;
	}
	public String getNomeDBPaiNN() {
	    return nomeDBPaiNN;
	}
	public void setNomeDBPaiNN(String nomeDBPaiNN) {
	    this.nomeDBPaiNN = nomeDBPaiNN;
	}
	public String getNomeDBPaiFilho() {
	    return nomeDBPaiFilho;
	}
	public void setNomeDBPaiFilho(String nomeDBPaiFilho) {
	    this.nomeDBPaiFilho = nomeDBPaiFilho;
	}
	public String getNomeTabelaFilho() {
		return nomeTabelaFilho;
	}
	public void setNomeTabelaFilho(String nomeTabelaFilho) {
		this.nomeTabelaFilho = nomeTabelaFilho;
	}
	public String getNomeTabelaFilhoNN() {
		return nomeTabelaFilhoNN;
	}
	public void setNomeTabelaFilhoNN(String nomeTabelaFilhoNN) {
		this.nomeTabelaFilhoNN = nomeTabelaFilhoNN;
	}
	public String getNomeTabelaPai() {
		return nomeTabelaPai;
	}
	public void setNomeTabelaPai(String nomeTabelaPai) {
		this.nomeTabelaPai = nomeTabelaPai;
	}
	public String getNomeTabelaPaiNN() {
		return nomeTabelaPaiNN;
	}
	public void setNomeTabelaPaiNN(String nomeTabelaPaiNN) {
		this.nomeTabelaPaiNN = nomeTabelaPaiNN;
	}
	
}
