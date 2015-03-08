package br.com.centralit.citgerencial.bean;

import java.util.HashMap;

import br.com.citframework.dto.IDto;

public class GerencialInfoGenerateDTO implements IDto {
	public static final String TIPO_INFORMACAO_PESSOAS = "PESSOAS";
	public static final String TIPO_INFORMACAO_PROGRAMAS = "PROGRAMAS";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6004363026482410376L;
	private String saida;
	private String graphType;
	
	private String nomeQuestao;
	
	private HashMap hashParametros;
	
	private String tipoSaidaApresentada;
	
	private String caminhoArquivosGraficos;
	private String caminhoArquivosPdfs;
	
	private String tipoInformacao; //O TipoInformacao indica de onde obter os resultados (PESSOAS, PROGRAMAS).
	private String tipoQuestao;
	
	public String getSaida() {
		return saida;
	}
	public void setSaida(String saida) {
		this.saida = saida;
	}
	public String getGraphType() {
		return graphType;
	}
	public void setGraphType(String graphType) {
		this.graphType = graphType;
	}
	public String getNomeQuestao() {
		return nomeQuestao;
	}
	public void setNomeQuestao(String nomeQuestao) {
		this.nomeQuestao = nomeQuestao;
	}
	public HashMap getHashParametros() {
		return hashParametros;
	}
	public void setHashParametros(HashMap hashParametros) {
		this.hashParametros = hashParametros;
	}
	public String getTipoSaidaApresentada() {
		return tipoSaidaApresentada;
	}
	public void setTipoSaidaApresentada(String tipoSaidaApresentada) {
		this.tipoSaidaApresentada = tipoSaidaApresentada;
	}
	public String getCaminhoArquivosGraficos() {
		return caminhoArquivosGraficos;
	}
	public void setCaminhoArquivosGraficos(String caminhoArquivosGraficos) {
		this.caminhoArquivosGraficos = caminhoArquivosGraficos;
	}
	public String getCaminhoArquivosPdfs() {
		return caminhoArquivosPdfs;
	}
	public void setCaminhoArquivosPdfs(String caminhoArquivosPdfs) {
		this.caminhoArquivosPdfs = caminhoArquivosPdfs;
	}
	public String getTipoInformacao() {
		return tipoInformacao;
	}
	public void setTipoInformacao(String tipoInformacao) {
		this.tipoInformacao = tipoInformacao;
	}
	public String getTipoQuestao() {
		return tipoQuestao;
	}
	public void setTipoQuestao(String tipoQuestao) {
		this.tipoQuestao = tipoQuestao;
	}
	
}
