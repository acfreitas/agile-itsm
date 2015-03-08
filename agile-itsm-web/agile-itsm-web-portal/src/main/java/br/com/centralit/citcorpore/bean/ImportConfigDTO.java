package br.com.centralit.citcorpore.bean;

import java.util.Collection;
import java.util.Date;

import br.com.citframework.dto.IDto;

@SuppressWarnings("rawtypes")
public class ImportConfigDTO implements IDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9160239194104870607L;
	
	private Integer idImportConfig;
	private Integer idImportarDados;
	private String tipo;
	private Integer idExternalConnection;
	private String tabelaOrigem;
	private String tabelaDestino;
	private String filtroOrigem;
	private String nome;
	private Date dataFim;
	
	private Collection colDadosCampos;

	
	public Integer getIdImportarDados() {
		return idImportarDados;
	}
	public void setIdImportarDados(Integer idImportarDados) {
		this.idImportarDados = idImportarDados;
	}
	public Integer getIdImportConfig(){
		return this.idImportConfig;
	}
	public void setIdImportConfig(Integer parm){
		this.idImportConfig = parm;
	}

	public String getTipo(){
		return this.tipo;
	}
	public void setTipo(String parm){
		this.tipo = parm;
	}

	public Integer getIdExternalConnection(){
		return this.idExternalConnection;
	}
	public void setIdExternalConnection(Integer parm){
		this.idExternalConnection = parm;
	}

	public String getTabelaOrigem(){
		return this.tabelaOrigem;
	}
	public void setTabelaOrigem(String parm){
		this.tabelaOrigem = parm;
	}

	public String getTabelaDestino(){
		return this.tabelaDestino;
	}
	public void setTabelaDestino(String parm){
		this.tabelaDestino = parm;
	}

	public String getFiltroOrigem(){
		return this.filtroOrigem;
	}
	public void setFiltroOrigem(String parm){
		this.filtroOrigem = parm;
	}
	public Collection getColDadosCampos() {
		return colDadosCampos;
	}
	public void setColDadosCampos(Collection colDadosCampos) {
		this.colDadosCampos = colDadosCampos;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}