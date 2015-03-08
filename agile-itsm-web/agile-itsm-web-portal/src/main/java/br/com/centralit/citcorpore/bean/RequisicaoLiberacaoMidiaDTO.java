package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class RequisicaoLiberacaoMidiaDTO implements IDto {
	
	private Integer idRequisicaoLiberacaoMidia;
	private Integer idMidiaSoftware;
	private Integer idRequisicaoLiberacao;
	private String nomeMidia;
	private Date dataFim;
	
	private static final long serialVersionUID = 1L;

	/**
	 * @return the idRequisicaoLiberacaoMidia
	 */
	public Integer getIdRequisicaoLiberacaoMidia() {
		return idRequisicaoLiberacaoMidia;
	}

	/**
	 * @param idRequisicaoLiberacaoMidia the idRequisicaoLiberacaoMidia to set
	 */
	public void setIdRequisicaoLiberacaoMidia(Integer idRequisicaoLiberacaoMidia) {
		this.idRequisicaoLiberacaoMidia = idRequisicaoLiberacaoMidia;
	}

	/**
	 * @return the idRequisicaoLiberacao
	 */
	public Integer getIdRequisicaoLiberacao() {
		return idRequisicaoLiberacao;
	}

	/**
	 * @param idRequisicaoLiberacao the idRequisicaoLiberacao to set
	 */
	public void setIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) {
		this.idRequisicaoLiberacao = idRequisicaoLiberacao;
	}

	/**
	 * @return the idMidiaSoftware
	 */
	public Integer getIdMidiaSoftware() {
		return idMidiaSoftware;
	}

	/**
	 * @param idMidiaSoftware the idMidiaSoftware to set
	 */
	public void setIdMidiaSoftware(Integer idMidiaSoftware) {
		this.idMidiaSoftware = idMidiaSoftware;
	}

	/**
	 * @return the nomeMidia
	 */
	public String getNomeMidia() {
		return nomeMidia;
	}

	/**
	 * @param nomeMidia the nomeMidia to set
	 */
	public void setNomeMidia(String nomeMidia) {
		this.nomeMidia = nomeMidia;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	

}
