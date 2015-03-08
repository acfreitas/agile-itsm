package br.com.centralit.citcorpore.bean;

import java.util.Collection;
import java.util.List;

import br.com.citframework.dto.IDto;

public class BIDashBoardDTO implements IDto {
	private Integer idDashBoard;
	private String nomeDashBoard;
	private String tipo;
	private String identificacao;
	private String situacao;
	private Integer idUsuario;
	private String parametros;
	private String naoAtualizBase;
	private Integer tempoRefresh;
	
	private Collection colItens;
	private List listParameters;
	
	private String parmOK = "false";
	private boolean parametersPreenchidos = false;	

	public Integer getIdDashBoard(){
		return this.idDashBoard;
	}
	public void setIdDashBoard(Integer parm){
		this.idDashBoard = parm;
	}

	public String getNomeDashBoard(){
		return this.nomeDashBoard;
	}
	public void setNomeDashBoard(String parm){
		this.nomeDashBoard = parm;
	}

	public String getTipo(){
		return this.tipo;
	}
	public void setTipo(String parm){
		this.tipo = parm;
	}

	public String getIdentificacao(){
		return this.identificacao;
	}
	public void setIdentificacao(String parm){
		this.identificacao = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}

	public Integer getIdUsuario(){
		return this.idUsuario;
	}
	public void setIdUsuario(Integer parm){
		this.idUsuario = parm;
	}
	public Collection getColItens() {
		return colItens;
	}
	public void setColItens(Collection colItens) {
		this.colItens = colItens;
	}
	public String getParametros() {
		return parametros;
	}
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
	public String getNaoAtualizBase() {
		return naoAtualizBase;
	}
	public void setNaoAtualizBase(String naoAtualizBase) {
		this.naoAtualizBase = naoAtualizBase;
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
	public boolean isParametersPreenchidos() {
		return parametersPreenchidos;
	}
	public boolean getParametersPreenchidos() {
		return parametersPreenchidos;
	}
	public void setParametersPreenchidos(boolean parametersPreenchidos) {
		this.parametersPreenchidos = parametersPreenchidos;
	}
	public Integer getTempoRefresh() {
		return tempoRefresh;
	}
	public void setTempoRefresh(Integer tempoRefresh) {
		this.tempoRefresh = tempoRefresh;
	}

}
