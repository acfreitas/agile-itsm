package br.com.centralit.citcorpore.metainfo.bean;

import java.util.Collection;
import java.util.HashMap;

import br.com.centralit.citcorpore.bean.MatrizVisaoDTO;
import br.com.citframework.dto.IDto;

public class VisaoDTO implements IDto {
	public static String EDIT = "ED";
	public static String TABLESEARCH = "TS";
	public static String TABLEEDIT = "TE";
	public static String EXTERNALCLASS = "EC";
	public static String MATRIZ = "MT";
	
	private Integer idVisao;
	private String descricao;
	private String tipoVisao;
	private String situacao;
	private String classeName;
	private String identificador;
	
	private Integer numero;
	private String nome;
	private String tipoNegocio;
	
	private boolean filha;
	private String acaoVisaoFilhaPesqRelacionada;
	
	private Collection colGrupos;
	private Collection colVisoesRelacionadas;
	private Collection colScripts;
	private Collection colBotoes;
	private Collection colHtmlCode;
	
	private HashMap mapScripts;
	private HashMap mapHtmlCodes;
	
	private MatrizVisaoDTO matrizVisaoDTO;
	
	private Integer idObjetoNegocioMatriz;
	private Integer idCamposObjetoNegocio1;
	private Integer idCamposObjetoNegocio2;
	private Integer idCamposObjetoNegocio3;	
	private String descricaoCampo1;
	private String descricaoCampo2;
	private String descricaoCampo3;	

	public Integer getIdVisao(){
		return this.idVisao;
	}
	public void setIdVisao(Integer parm){
		this.idVisao = parm;
	}

	public String getDescricao(){
		return this.descricao;
	}
	public void setDescricao(String parm){
		this.descricao = parm;
	}

	public String getTipoVisao(){
		return this.tipoVisao;
	}
	public void setTipoVisao(String parm){
		this.tipoVisao = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public String getSituacaoVisao(){
		return this.situacao;
	}	
	public void setSituacao(String parm){
		this.situacao = parm;
	}
	public Collection getColGrupos() {
		return colGrupos;
	}
	public void setColGrupos(Collection colGrupos) {
		this.colGrupos = colGrupos;
	}
	public Collection getColVisoesRelacionadas() {
		return colVisoesRelacionadas;
	}
	public void setColVisoesRelacionadas(Collection colVisoesRelacionadas) {
		this.colVisoesRelacionadas = colVisoesRelacionadas;
	}
	public boolean getFilha() {
		return filha;
	}
	public void setFilha(boolean filha) {
		this.filha = filha;
	}
	public String getAcaoVisaoFilhaPesqRelacionada() {
		return acaoVisaoFilhaPesqRelacionada;
	}
	public void setAcaoVisaoFilhaPesqRelacionada(
			String acaoVisaoFilhaPesqRelacionada) {
		this.acaoVisaoFilhaPesqRelacionada = acaoVisaoFilhaPesqRelacionada;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipoNegocio() {
		return tipoNegocio;
	}
	public void setTipoNegocio(String tipoNegocio) {
		this.tipoNegocio = tipoNegocio;
	}
	public Collection getColScripts() {
		return colScripts;
	}
	public void setColScripts(Collection colScripts) {
		this.colScripts = colScripts;
	}
	public HashMap getMapScripts() {
		return mapScripts;
	}
	public void setMapScripts(HashMap mapScripts) {
		this.mapScripts = mapScripts;
	}
	public String getClasseName() {
		return classeName;
	}
	public void setClasseName(String classeName) {
		this.classeName = classeName;
	}
	public Collection getColBotoes() {
		return colBotoes;
	}
	public void setColBotoes(Collection colBotoes) {
		this.colBotoes = colBotoes;
	}

	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public Collection getColHtmlCode() {
		return colHtmlCode;
	}
	public void setColHtmlCode(Collection colHtmlCode) {
		this.colHtmlCode = colHtmlCode;
	}
	public HashMap getMapHtmlCodes() {
		return mapHtmlCodes;
	}
	public void setMapHtmlCodes(HashMap mapHtmlCodes) {
		this.mapHtmlCodes = mapHtmlCodes;
	}
	public MatrizVisaoDTO getMatrizVisaoDTO() {
		return matrizVisaoDTO;
	}
	public void setMatrizVisaoDTO(MatrizVisaoDTO matrizVisaoDTO) {
		this.matrizVisaoDTO = matrizVisaoDTO;
	}
	public Integer getIdObjetoNegocioMatriz() {
		return idObjetoNegocioMatriz;
	}
	public void setIdObjetoNegocioMatriz(Integer idObjetoNegocioMatriz) {
		this.idObjetoNegocioMatriz = idObjetoNegocioMatriz;
	}
	public Integer getIdCamposObjetoNegocio1() {
		return idCamposObjetoNegocio1;
	}
	public void setIdCamposObjetoNegocio1(Integer idCamposObjetoNegocio1) {
		this.idCamposObjetoNegocio1 = idCamposObjetoNegocio1;
	}
	public Integer getIdCamposObjetoNegocio2() {
		return idCamposObjetoNegocio2;
	}
	public void setIdCamposObjetoNegocio2(Integer idCamposObjetoNegocio2) {
		this.idCamposObjetoNegocio2 = idCamposObjetoNegocio2;
	}
	public Integer getIdCamposObjetoNegocio3() {
		return idCamposObjetoNegocio3;
	}
	public void setIdCamposObjetoNegocio3(Integer idCamposObjetoNegocio3) {
		this.idCamposObjetoNegocio3 = idCamposObjetoNegocio3;
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
}
