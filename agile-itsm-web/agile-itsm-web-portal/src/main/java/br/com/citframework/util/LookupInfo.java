package br.com.citframework.util;

import java.util.ArrayList;
import java.util.Collection;

public class LookupInfo {
	private String nome;
	private String tabela;
	private Collection colCamposPesquisa;
	private Collection colCamposRetorno;
	private Collection colCamposChave;
	private Collection colCamposOrdenacao;
	private String daoProcessor;
	private String where;
	private String scriptRef;
	private Collection colScriptRef;
	private String script;
	private String separaCampos;
	public Collection getColCamposChave() {
		return colCamposChave;
	}
	public void setColCamposChave(Collection colCamposChave) {
		this.colCamposChave = colCamposChave;
	}
	public Collection getColCamposOrdenacao() {
		return colCamposOrdenacao;
	}
	public void setColCamposOrdenacao(Collection colCamposOrdenacao) {
		this.colCamposOrdenacao = colCamposOrdenacao;
	}
	public Collection getColCamposPesquisa() {
		return colCamposPesquisa;
	}
	public void setColCamposPesquisa(Collection colCamposPesquisa) {
		this.colCamposPesquisa = colCamposPesquisa;
	}
	public Collection getColCamposRetorno() {
		return colCamposRetorno;
	}
	public void setColCamposRetorno(Collection colCamposRetorno) {
		this.colCamposRetorno = colCamposRetorno;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTabela() {
		return tabela;
	}
	public void setTabela(String tabela) {
		this.tabela = tabela;
	}
	public String getDaoProcessor() {
		return daoProcessor;
	}
	public void setDaoProcessor(String daoProcessor) {
		this.daoProcessor = daoProcessor;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public String getScriptRef() {
		return scriptRef;
	}
	public void setScriptRef(String scriptRef) {
		this.scriptRef = scriptRef;
		if (scriptRef == null || scriptRef.trim().equalsIgnoreCase("")){
			this.colScriptRef = null;
			return;
		}
		String scriptRefAux = scriptRef;
		scriptRefAux += ";";
		String str[] = scriptRefAux.split(";");
		this.colScriptRef = new ArrayList();
		if (str != null){
			for(int i=0; i < str.length; i++){
				this.colScriptRef.add(str[i]);
			}
		}
	}
	public Collection getColScriptRef() {
		return colScriptRef;
	}
	public void setColScriptRef(Collection colScriptRef) {
		this.colScriptRef = colScriptRef;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getSeparaCampos() {
		if (this.separaCampos == null) return "N";
		return separaCampos;
	}
	public void setSeparaCampos(String separaCampos) {
		this.separaCampos = separaCampos;
	}
}
