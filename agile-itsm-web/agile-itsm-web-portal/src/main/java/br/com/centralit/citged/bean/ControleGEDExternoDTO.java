package br.com.centralit.citged.bean;

import java.sql.Timestamp;
import java.util.HashMap;


public class ControleGEDExternoDTO extends ControleGEDDTO {
	private HashMap mapDemaisDados;
	private String caminhoCompletoDocumento;
	private String caminhoCompletoAssinaturaDigital;
	private String caminhoCompletoCarimboTempo;
	private byte[] conteudoDocumento;
	private byte[] conteudoAssinaturaDigital;
	private byte[] conteudoCarimboTempo;
	private Timestamp dataGeracaoConteudo;
	
	private String numeroRetorno;

	public HashMap getMapDemaisDados() {
		return mapDemaisDados;
	}

	public void setMapDemaisDados(HashMap mapDemaisDados) {
		this.mapDemaisDados = mapDemaisDados;
	}

	public byte[] getConteudoDocumento() {
		return conteudoDocumento;
	}

	public void setConteudoDocumento(byte[] conteudoDocumento) {
		this.conteudoDocumento = conteudoDocumento;
	}

	public byte[] getConteudoAssinaturaDigital() {
		return conteudoAssinaturaDigital;
	}

	public void setConteudoAssinaturaDigital(byte[] conteudoAssinaturaDigital) {
		this.conteudoAssinaturaDigital = conteudoAssinaturaDigital;
	}

	public byte[] getConteudoCarimboTempo() {
		return conteudoCarimboTempo;
	}

	public void setConteudoCarimboTempo(byte[] conteudoCarimboTempo) {
		this.conteudoCarimboTempo = conteudoCarimboTempo;
	}

	public String getCaminhoCompletoDocumento() {
		return caminhoCompletoDocumento;
	}

	public void setCaminhoCompletoDocumento(String caminhoCompletoDocumento) {
		this.caminhoCompletoDocumento = caminhoCompletoDocumento;
	}

	public String getCaminhoCompletoAssinaturaDigital() {
		return caminhoCompletoAssinaturaDigital;
	}

	public void setCaminhoCompletoAssinaturaDigital(
			String caminhoCompletoAssinaturaDigital) {
		this.caminhoCompletoAssinaturaDigital = caminhoCompletoAssinaturaDigital;
	}

	public String getCaminhoCompletoCarimboTempo() {
		return caminhoCompletoCarimboTempo;
	}

	public void setCaminhoCompletoCarimboTempo(String caminhoCompletoCarimboTempo) {
		this.caminhoCompletoCarimboTempo = caminhoCompletoCarimboTempo;
	}

	public Timestamp getDataGeracaoConteudo() {
		return dataGeracaoConteudo;
	}

	public void setDataGeracaoConteudo(Timestamp dataGeracaoConteudo) {
		this.dataGeracaoConteudo = dataGeracaoConteudo;
	}

	public String getNumeroRetorno() {
		return numeroRetorno;
	}

	public void setNumeroRetorno(String numeroRetorno) {
		this.numeroRetorno = numeroRetorno;
	}
	
}
