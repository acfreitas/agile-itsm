package br.com.centralit.citcorpore.bean;

import java.util.Collection;
import java.util.List;

import br.com.citframework.dto.IDto;

public class BIConsultaDTO implements IDto {
	private Integer idConsulta;
	private String identificacao;
	private String nomeConsulta;
	private String tipoConsulta;
	private String textoSQL;
	private String situacao;
	private String acaoCruzado;
	private String template;
	private String scriptExec;
	private String parametros;
	private Integer idCategoria;
	private String naoAtualizBase;
	
	private String acao;
	private String conteudo;
	
	private String parmOK = "false";
	private String dashPart = "N";
	private boolean parametersPreenchidos = false;
	private List listParameters;
	
	private Collection colColunas;

	public Integer getIdConsulta(){
		return this.idConsulta;
	}
	public void setIdConsulta(Integer parm){
		this.idConsulta = parm;
	}

	public String getNomeConsulta(){
		return this.nomeConsulta;
	}
	public void setNomeConsulta(String parm){
		this.nomeConsulta = parm;
	}

	public String getTipoConsulta(){
		return this.tipoConsulta;
	}
	public void setTipoConsulta(String parm){
		this.tipoConsulta = parm;
	}

	public String getTextoSQL(){
		return this.textoSQL;
	}
	public void setTextoSQL(String parm){
		this.textoSQL = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}

	public String getAcaoCruzado(){
		return this.acaoCruzado;
	}
	public void setAcaoCruzado(String parm){
		this.acaoCruzado = parm;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getScriptExec() {
		return scriptExec;
	}
	public void setScriptExec(String scriptExec) {
		this.scriptExec = scriptExec;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	public String getParametros() {
		return parametros;
	}
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
	public boolean isParametersPreenchidos() {
		return parametersPreenchidos;
	}
	public boolean getParametersPreenchidos() {
		return isParametersPreenchidos();
	}	
	public void setParametersPreenchidos(boolean parametersPreenchidos) {
		this.parametersPreenchidos = parametersPreenchidos;
	}
	public List getListParameters() {
		return listParameters;
	}
	public void setListParameters(List listParameters) {
		this.listParameters = listParameters;
	}
	public String getParmOK() {
		return parmOK;
	}
	public void setParmOK(String parmOK) {
		setParametersPreenchidos(false);
		if (parmOK != null){
			if (parmOK.equalsIgnoreCase("S") || parmOK.equalsIgnoreCase("true")){
				setParametersPreenchidos(true);
			}
		}
		this.parmOK = parmOK;
	}
	public Collection getColColunas() {
		return colColunas;
	}
	public void setColColunas(Collection colColunas) {
		this.colColunas = colColunas;
	}
	public Integer getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getNaoAtualizBase() {
		return naoAtualizBase;
	}
	public void setNaoAtualizBase(String naoAtualizBase) {
		this.naoAtualizBase = naoAtualizBase;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getDashPart() {
		return dashPart;
	}
	public void setDashPart(String dashPart) {
		this.dashPart = dashPart;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

}
