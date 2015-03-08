package br.com.centralit.citquestionario.bean;

import br.com.citframework.dto.IDto;

public class ArquivoMultimidiaDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8859194558846575346L;
	private String nomeArquivo;
	private String caminhoArquivo;
	private String observacao;
	private String urlArquivo;
	private Integer idQuestaoQuest;
	
	private String idDIV;
	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}
	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getUrlArquivo() {
		return urlArquivo;
	}
	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}
	public String getIdDIV() {
		return idDIV;
	}
	public void setIdDIV(String idDIV) {
		this.idDIV = idDIV;
	}
	public Integer getIdQuestaoQuest() {
		return idQuestaoQuest;
	}
	public void setIdQuestaoQuest(Integer idQuestaoQuest) {
		this.idQuestaoQuest = idQuestaoQuest;
	}
}
