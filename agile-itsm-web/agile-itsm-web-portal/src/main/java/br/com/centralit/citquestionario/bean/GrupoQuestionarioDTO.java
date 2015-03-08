package br.com.centralit.citquestionario.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class GrupoQuestionarioDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8473113003183923972L;
	private Integer idGrupoQuestionario;
	private Integer idQuestionario;
	private String nomeGrupoQuestionario;
	private Integer ordem;
	
	private String infoGrupo;
	
	private String serializeQuestoesGrupo;
	private Collection colQuestoes;
	private Integer idControleTabela;
	
	public Integer getIdGrupoQuestionario() {
		return idGrupoQuestionario;
	}
	public void setIdGrupoQuestionario(Integer idGrupoQuestionario) {
		this.idGrupoQuestionario = idGrupoQuestionario;
	}
	public Integer getIdQuestionario() {
		return idQuestionario;
	}
	public void setIdQuestionario(Integer idQuestionario) {
		this.idQuestionario = idQuestionario;
	}
	public String getNomeGrupoQuestionario() {
		return nomeGrupoQuestionario;
	}
	public void setNomeGrupoQuestionario(String nomeGrupoQuestionario) {
		this.nomeGrupoQuestionario = nomeGrupoQuestionario;
	}
	public String getInfoGrupo() {
		return infoGrupo;
	}
	public void setInfoGrupo(String infoGrupo) {
		this.infoGrupo = infoGrupo;
	}
	public String getSerializeQuestoesGrupo() {
		return serializeQuestoesGrupo;
	}
	public void setSerializeQuestoesGrupo(String serializeQuestoesGrupo) {
		this.serializeQuestoesGrupo = serializeQuestoesGrupo;
	}
	public Collection getColQuestoes() {
		return colQuestoes;
	}
	public void setColQuestoes(Collection colQuestoes) {
		this.colQuestoes = colQuestoes;
	}
	public Integer getIdControleTabela() {
		return idControleTabela;
	}
	public void setIdControleTabela(Integer idControleTabela) {
		this.idControleTabela = idControleTabela;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
}
