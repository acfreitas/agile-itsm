package br.com.centralit.citquestionario.bean;

import br.com.citframework.dto.IDto;

public class AplicacaoQuestionarioDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2984986456849222230L;
	private Integer idAplicacaoQuestionario;
	private Integer idQuestionario;
	private Integer idTipoProduto;
	private String situacao;
	private String aplicacao;
	
    public Integer getIdAplicacaoQuestionario() {
        return idAplicacaoQuestionario;
    }
    public void setIdAplicacaoQuestionario(Integer idAplicacaoQuestionario) {
        this.idAplicacaoQuestionario = idAplicacaoQuestionario;
    }
    public Integer getIdQuestionario() {
        return idQuestionario;
    }
    public void setIdQuestionario(Integer idQuestionario) {
        this.idQuestionario = idQuestionario;
    }
    public Integer getIdTipoProduto() {
        return idTipoProduto;
    }
    public void setIdTipoProduto(Integer idTipoProduto) {
        this.idTipoProduto = idTipoProduto;
    }
    public String getSituacao() {
        return situacao;
    }
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    public String getAplicacao() {
        return aplicacao;
    }
    public void setAplicacao(String aplicacao) {
        this.aplicacao = aplicacao;
    }
	
}
