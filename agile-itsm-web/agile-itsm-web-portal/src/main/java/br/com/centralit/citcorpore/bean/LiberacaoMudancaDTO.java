package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class LiberacaoMudancaDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idLiberacao;
	private Integer idRequisicaoMudanca;
	private Integer idHistoricoLiberacao;
	private Integer idHistoricoMudanca;
	private String titulo;
	
	private String status;
	
	private String situacaoLiberacao;
	
	private String descricao;

	public Integer getIdLiberacao(){
		return this.idLiberacao;
	}
	public void setIdLiberacao(Integer parm){
		this.idLiberacao = parm;
	}

	public Integer getIdRequisicaoMudanca(){
		return this.idRequisicaoMudanca;
	}
	public void setIdRequisicaoMudanca(Integer parm){
		this.idRequisicaoMudanca = parm;
	}
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
	
	public Integer getIdHistoricoLiberacao() {
		return idHistoricoLiberacao;
	}
	public void setIdHistoricoLiberacao(Integer idHistoricoLiberacao) {
		this.idHistoricoLiberacao = idHistoricoLiberacao;
	}
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getSituacaoLiberacao() {
		return situacaoLiberacao;
	}
	
	public void setSituacaoLiberacao(String situacaoLiberacao) {
		this.situacaoLiberacao = situacaoLiberacao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getIdHistoricoMudanca() {
		return idHistoricoMudanca;
	}
	public void setIdHistoricoMudanca(Integer idHistoricoMudanca) {
		this.idHistoricoMudanca = idHistoricoMudanca;
	}
}
