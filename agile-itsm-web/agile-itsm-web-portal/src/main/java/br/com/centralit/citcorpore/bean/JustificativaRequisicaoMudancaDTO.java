package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class JustificativaRequisicaoMudancaDTO implements IDto {

	
	private Integer idJustificativaMudanca ;
	
	private String   descricaoJustificativa  ;
	
	private  String suspensao ;
	
	private String  situacao ;
	
	private String  aprovacao ;
	
	private  String  deleted  ;

	
	
	/**
	 * @return the suspensao
	 */
	public String getSuspensao() {
		return suspensao;
	}

	/**
	 * @param suspensao the suspensao to set
	 */
	public void setSuspensao(String suspensao) {
		this.suspensao = suspensao;
	}

	/**
	 * @return the situacao
	 */
	public String getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	/**
	 * @return the aprovacao
	 */
	public String getAprovacao() {
		return aprovacao;
	}

	/**
	 * @param aprovacao the aprovacao to set
	 */
	public void setAprovacao(String aprovacao) {
		this.aprovacao = aprovacao;
	}

	/**
	 * @return the deleted
	 */
	public String getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}


	/**
	 * @return the descricaoJustificativa
	 */
	public String getDescricaoJustificativa() {
		return descricaoJustificativa;
	}


	/**
	 * @param descricaoJustificativa the descricaoJustificativa to set
	 */
	public void setDescricaoJustificativa(String descricaoJustificativa) {
		this.descricaoJustificativa = descricaoJustificativa;
	}

	/**
	 * @return the idJustificativaMudanca
	 */
	public Integer getIdJustificativaMudanca() {
		return idJustificativaMudanca;
	}

	/**
	 * @param idJustificativaMudanca the idJustificativaMudanca to set
	 */
	public void setIdJustificativaMudanca(Integer idJustificativaMudanca) {
		this.idJustificativaMudanca = idJustificativaMudanca;
	}
	
	
}
