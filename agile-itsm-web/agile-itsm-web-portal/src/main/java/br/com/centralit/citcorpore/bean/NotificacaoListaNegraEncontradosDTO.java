package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author ronnie.lopes
 * DTO criado apenas para envio do email Softwares Lista Negra, não possui tabela no banco de dados
 */
@SuppressWarnings("serial")
public class NotificacaoListaNegraEncontradosDTO implements IDto{
	
	private String computador;
	private String softwarelistanegra;
	private String softwareencontrado;
	private String dataHora;
	private String tabelaListaNegra;
	private String nomeContato;
	
	public String getComputador() {
		return computador;
	}
	public void setComputador(String computador) {
		this.computador = computador;
	}
	public String getSoftwarelistanegra() {
		return softwarelistanegra;
	}
	public void setSoftwarelistanegra(String softwarelistanegra) {
		this.softwarelistanegra = softwarelistanegra;
	}
	public String getSoftwareencontrado() {
		return softwareencontrado;
	}
	public void setSoftwareencontrado(String softwareencontrado) {
		this.softwareencontrado = softwareencontrado;
	}
	public String getDataHora() {
		return dataHora;
	}
	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}
	public String getTabelaListaNegra() {
		return tabelaListaNegra;
	}
	public void setTabelaListaNegra(String tabelaListaNegra) {
		this.tabelaListaNegra = tabelaListaNegra;
	}
	public String getNomeContato() {
		return nomeContato;
	}
	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
	}
}
