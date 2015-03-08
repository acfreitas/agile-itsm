package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class ImportarDadosDTO implements IDto {
	
	private static final long serialVersionUID = 4942283426115769824L;
	
	private Integer idImportarDados;
	private Integer idExternalConnection;
	private String importarPor;
	private String tipo;
	private String nome;
	private String script;
	private String agendarRotina;
	private String executarPor;
	private String horaExecucao;
	private Integer periodoHora;
	private Date dataFim;
	private String tabelaOrigem;
	private String tabelaDestino;
	private Collection<UploadDTO> anexos;
	private String jsonMatriz;

	
	public Collection<UploadDTO> getAnexos() {
		return anexos;
	}
	public void setAnexos(Collection<UploadDTO> anexos) {
		this.anexos = anexos;
	}
	public Integer getIdImportarDados() {
		return idImportarDados;
	}
	public void setIdImportarDados(Integer idImportarDados) {
		this.idImportarDados = idImportarDados;
	}
	public Integer getIdExternalConnection() {
		return idExternalConnection;
	}
	public void setIdExternalConnection(Integer idExternalConnection) {
		this.idExternalConnection = idExternalConnection;
	}
	public String getImportarPor() {
		return importarPor;
	}
	public void setImportarPor(String importarPor) {
		this.importarPor = importarPor;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getAgendarRotina() {
		return agendarRotina;
	}
	public void setAgendarRotina(String agendarRotina) {
		this.agendarRotina = agendarRotina;
	}
	public String getExecutarPor() {
		return executarPor;
	}
	public void setExecutarPor(String executarPor) {
		this.executarPor = executarPor;
	}
	public String getHoraExecucao() {
		return horaExecucao;
	}
	public void setHoraExecucao(String horaExecucao) {
		this.horaExecucao = horaExecucao;
	}
	public Integer getPeriodoHora() {
		return periodoHora;
	}
	public void setPeriodoHora(Integer periodoHora) {
		this.periodoHora = periodoHora;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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

}