package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class AvaliacaoReferenciaFornecedorDTO implements IDto {
	private Integer idAvaliacaoFornecedor;
	
	private Integer idEmpregado;
	
	private String decisao;
	
	private String observacoes;
	
	private Integer sequencia;
	
	private String nome;
	
	private String telefone;

	public Integer getIdAvaliacaoFornecedor(){
		return this.idAvaliacaoFornecedor;
	}
	public void setIdAvaliacaoFornecedor(Integer parm){
		this.idAvaliacaoFornecedor = parm;
	}

	public String getDecisao(){
		return this.decisao;
	}
	public void setDecisao(String parm){
		this.decisao = parm;
	}

	public String getObservacoes(){
		return this.observacoes;
	}
	public void setObservacoes(String parm){
		this.observacoes = parm;
	}
	/**
	 * @return the sequencia
	 */
	public Integer getSequencia() {
		return sequencia;
	}
	/**
	 * @param sequencia the sequencia to set
	 */
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}
	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
    public Integer getIdEmpregado() {
        return idEmpregado;
    }
    public void setIdEmpregado(Integer idEmpregado) {
        this.idEmpregado = idEmpregado;
    }
	
}
