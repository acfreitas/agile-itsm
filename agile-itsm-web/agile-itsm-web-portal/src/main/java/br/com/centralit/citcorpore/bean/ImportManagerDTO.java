package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ImportManagerDTO implements IDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5826581473179525246L;
	
	private Integer idImportConfig;
	private Integer idExternalConnection;
	private Integer idImportarDados;
	private String tabelaOrigem;
	private String tabelaDestino;
	private String jsonMatriz;
	
	private String nome;
	private String tipo;
	private String filtroOrigem;
	
	private Object result;

	public Integer getIdExternalConnection() {
		return idExternalConnection;
	}

	public void setIdExternalConnection(Integer idExternalConnection) {
		this.idExternalConnection = idExternalConnection;
	}

	public String getTabelaOrigem() {
		return tabelaOrigem;
	}

	public void setTabelaOrigem(String tabelaOrigem) {
		this.tabelaOrigem = tabelaOrigem;
	}

	public String getTabelaDestino() {
		return tabelaDestino;
	}

	public void setTabelaDestino(String tabelaDestino) {
		this.tabelaDestino = tabelaDestino;
	}

	public String getJsonMatriz() {
		return jsonMatriz;
	}

	public void setJsonMatriz(String jsonMatriz) {
		this.jsonMatriz = jsonMatriz;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFiltroOrigem() {
		return filtroOrigem;
	}

	public void setFiltroOrigem(String filtroOrigem) {
		this.filtroOrigem = filtroOrigem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdImportConfig() {
		return idImportConfig;
	}

	public void setIdImportConfig(Integer idImportConfig) {
		this.idImportConfig = idImportConfig;
	}

	public Integer getIdImportarDados() {
		return idImportarDados;
	}

	public void setIdImportarDados(Integer idImportarDados) {
		this.idImportarDados = idImportarDados;
	}
}