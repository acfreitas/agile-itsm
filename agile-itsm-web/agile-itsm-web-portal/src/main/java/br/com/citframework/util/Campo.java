package br.com.citframework.util;

import java.io.Serializable;
import java.util.Collection;

public class Campo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3859065156813835430L;
	private String nomeFisico;
	private String descricao;
	private boolean obrigatorio;
	private String type;
	private int tamanho;
	private boolean somenteBusca;
	private String scriptLostFocus;
	private String mesmalinha;
	
	private String strValue;
	private int intValue;
	private Object objValue;
	private Collection colValores;
	
	public Campo(){
	}
	public Campo(String nome, String desc, boolean obr, String type, int tam){
		this.setNomeFisico(nome);
		this.setDescricao(desc);
		this.setObrigatorio(obr);
		this.setType(type);
		this.setTamanho(tam);
		this.setMesmalinha(null);
		this.setScriptLostFocus(null);
		this.setColValores(null);
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNomeFisico() {
		return nomeFisico;
	}
	public void setNomeFisico(String nomeFisico) {
		this.nomeFisico = nomeFisico;
	}
	public boolean isObrigatorio() {
		return obrigatorio;
	}
	public void setObrigatorio(boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTamanho() {
		return tamanho;
	}
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	public int getIntValue() {
		return intValue;
	}
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	public String getStrValue() {
		return strValue;
	}
	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}
	public Object getObjValue() {
		return objValue;
	}
	public void setObjValue(Object objValue) {
		this.objValue = objValue;
	}
	public boolean isSomenteBusca() {
		return somenteBusca;
	}
	public void setSomenteBusca(boolean somenteBusca) {
		this.somenteBusca = somenteBusca;
	}
	public String getScriptLostFocus() {
		return scriptLostFocus;
	}
	public void setScriptLostFocus(String scriptLostFocus) {
		this.scriptLostFocus = scriptLostFocus;
	}
	public String getMesmalinha() {
		if (mesmalinha == null) return "";
		return mesmalinha;
	}
	public void setMesmalinha(String mesmalinha) {
		this.mesmalinha = mesmalinha;
	}
	public Collection getColValores() {
		return colValores;
	}
	public void setColValores(Collection colValores) {
		this.colValores = colValores;
	}
}
