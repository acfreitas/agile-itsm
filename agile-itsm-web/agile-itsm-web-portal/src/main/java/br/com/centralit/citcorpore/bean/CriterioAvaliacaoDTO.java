package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class CriterioAvaliacaoDTO implements IDto {
	private Integer idCriterio;
	private String descricao;
	private String aplicavelCotacao;
	private String aplicavelAvaliacaoSolicitante;
	private String aplicavelAvaliacaoComprador;
    private String aplicavelQualificacaoFornecedor;
    private String tipoAvaliacao;

    private Integer sequencia;
	private String obs;
	private String valor;
	
	private Integer peso;

	public Integer getIdCriterio(){
		return this.idCriterio;
	}
	public void setIdCriterio(Integer parm){
		this.idCriterio = parm;
	}

	public String getDescricao(){
		return this.descricao;
	}
	public void setDescricao(String parm){
		this.descricao = parm;
	}
	public String getAplicavelCotacao(){
		return this.aplicavelCotacao;
	}
	public void setAplicavelCotacao(String parm){
		this.aplicavelCotacao = parm;
	}

	public String getAplicavelAvaliacaoSolicitante(){
		return this.aplicavelAvaliacaoSolicitante;
	}
	public void setAplicavelAvaliacaoSolicitante(String parm){
		this.aplicavelAvaliacaoSolicitante = parm;
	}

	public String getAplicavelAvaliacaoComprador(){
		return this.aplicavelAvaliacaoComprador;
	}
	public void setAplicavelAvaliacaoComprador(String parm){
		this.aplicavelAvaliacaoComprador = parm;
	}
	public Integer getSequencia() {
		return sequencia;
	}
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
    public Integer getPeso() {
        return peso;
    }
    public void setPeso(Integer peso) {
        this.peso = peso;
    }
    public String getAplicavelQualificacaoFornecedor() {
        return aplicavelQualificacaoFornecedor;
    }
    public void setAplicavelQualificacaoFornecedor(
            String aplicavelQualificacaoFornecedor) {
        this.aplicavelQualificacaoFornecedor = aplicavelQualificacaoFornecedor;
    }
    public String getTipoAvaliacao() {
        return tipoAvaliacao;
    }
    public void setTipoAvaliacao(String tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

}
