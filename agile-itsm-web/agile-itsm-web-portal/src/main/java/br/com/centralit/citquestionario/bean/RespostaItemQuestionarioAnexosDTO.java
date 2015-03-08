package br.com.centralit.citquestionario.bean;

import br.com.citframework.dto.IDto;

public class RespostaItemQuestionarioAnexosDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2088779287936858372L;
	private Integer idRespostaItmQuestionarioAnexo;
	private Integer idRespostaItemQuestionario;
	private String caminhoAnexo;
	private String observacao;
	private Integer idQuestaoQuestionario;
	private String nomeArquivo;
	private Integer idControleGED;
	public Integer getIdRespostaItmQuestionarioAnexo() {
		return idRespostaItmQuestionarioAnexo;
	}
	public void setIdRespostaItmQuestionarioAnexo(
			Integer idRespostaItmQuestionarioAnexo) {
		this.idRespostaItmQuestionarioAnexo = idRespostaItmQuestionarioAnexo;
	}
	public Integer getIdRespostaItemQuestionario() {
		return idRespostaItemQuestionario;
	}
	public void setIdRespostaItemQuestionario(Integer idRespostaItemQuestionario) {
		this.idRespostaItemQuestionario = idRespostaItemQuestionario;
	}
	public String getCaminhoAnexo() {
		return caminhoAnexo;
	}
	public void setCaminhoAnexo(String caminhoAnexo) {
		this.caminhoAnexo = caminhoAnexo;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Integer getIdQuestaoQuestionario() {
		return idQuestaoQuestionario;
	}
	public void setIdQuestaoQuestionario(Integer idQuestaoQuestionario) {
		this.idQuestaoQuestionario = idQuestaoQuestionario;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public Integer getIdControleGED() {
		return idControleGED;
	}
	public void setIdControleGED(Integer idControleGED) {
		this.idControleGED = idControleGED;
	}
}
