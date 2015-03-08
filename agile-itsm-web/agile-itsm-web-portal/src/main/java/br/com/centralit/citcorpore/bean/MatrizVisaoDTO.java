package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class MatrizVisaoDTO implements IDto {
	private Integer idMatriz;
	private Integer idVisao;
	private Integer idObjetoNegocio;
	private String nomeTabelaDB;
	private Integer idCamposObjetoNegocio1;
	private String nomeDB1;
	private Integer idCamposObjetoNegocio2;
	private String nomeDB2;
	private Integer idCamposObjetoNegocio3;
	private String nomeDB3;
	private String strInfo;
	private String nomeCampo1;
	private String nomeCampo2;
	private String nomeCampo3;
	private String descricaoCampo1;
	private String descricaoCampo2;
	private String descricaoCampo3;
	
	public Integer getIdMatriz(){
		return this.idMatriz;
	}
	public void setIdMatriz(Integer parm){
		this.idMatriz = parm;
	}
	public Integer getIdVisao(){
		return this.idVisao;
	}
	public void setIdVisao(Integer parm){
		this.idVisao = parm;
	}
	public Integer getIdObjetoNegocio(){
		return this.idObjetoNegocio;
	}
	public void setIdObjetoNegocio(Integer parm){
		this.idObjetoNegocio = parm;
	}
	public Integer getIdCamposObjetoNegocio1(){
		return this.idCamposObjetoNegocio1;
	}
	public void setIdCamposObjetoNegocio1(Integer parm){
		this.idCamposObjetoNegocio1 = parm;
	}
	public Integer getIdCamposObjetoNegocio2(){
		return this.idCamposObjetoNegocio2;
	}
	public void setIdCamposObjetoNegocio2(Integer parm){
		this.idCamposObjetoNegocio2 = parm;
	}
	public Integer getIdCamposObjetoNegocio3(){
		return this.idCamposObjetoNegocio3;
	}
	public void setIdCamposObjetoNegocio3(Integer parm){
		this.idCamposObjetoNegocio3 = parm;
	}
	public String getStrInfo(){
		return this.strInfo;
	}
	public void setStrInfo(String parm){
		this.strInfo = parm;
	}
	public String getNomeCampo1(){
		return this.nomeCampo1;
	}
	public void setNomeCampo1(String parm){
		this.nomeCampo1 = parm;
	}
	public String getNomeCampo2(){
		return this.nomeCampo2;
	}
	public void setNomeCampo2(String parm){
		this.nomeCampo2 = parm;
	}
	public String getNomeCampo3(){
		return this.nomeCampo3;
	}
	public void setNomeCampo3(String parm){
		this.nomeCampo3 = parm;
	}
	public String getDescricaoCampo1() {
		return descricaoCampo1;
	}
	public void setDescricaoCampo1(String descricaoCampo1) {
		this.descricaoCampo1 = descricaoCampo1;
	}
	public String getDescricaoCampo2() {
		return descricaoCampo2;
	}
	public void setDescricaoCampo2(String descricaoCampo2) {
		this.descricaoCampo2 = descricaoCampo2;
	}
	public String getDescricaoCampo3() {
		return descricaoCampo3;
	}
	public void setDescricaoCampo3(String descricaoCampo3) {
		this.descricaoCampo3 = descricaoCampo3;
	}
	public String getNomeTabelaDB() {
		return nomeTabelaDB;
	}
	public void setNomeTabelaDB(String nomeTabelaDB) {
		this.nomeTabelaDB = nomeTabelaDB;
	}
	public String getNomeDB1() {
		return nomeDB1;
	}
	public void setNomeDB1(String nomeDB1) {
		this.nomeDB1 = nomeDB1;
	}
	public String getNomeDB2() {
		return nomeDB2;
	}
	public void setNomeDB2(String nomeDB2) {
		this.nomeDB2 = nomeDB2;
	}
	public String getNomeDB3() {
		return nomeDB3;
	}
	public void setNomeDB3(String nomeDB3) {
		this.nomeDB3 = nomeDB3;
	}

}
