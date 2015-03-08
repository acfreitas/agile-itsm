package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class AvaliacaoFornecedorDTO implements IDto {

	private Integer idAvaliacaoFornecedor;

	private Integer idFornecedor;

	private Integer idResponsavel;

	private Date dataAvaliacao;

	private String decisaoQualificacao;

	private String observacoesAvaliacaoFornecedor;

	private Collection<CriterioAvaliacaoFornecedorDTO> listCriterioAvaliacaoFornecedor;

	private Collection<AvaliacaoReferenciaFornecedorDTO> listAvaliacaoReferenciaFornecedor;
	
	private String nomeResponsavel ;

	
	public Integer getIdAvaliacaoFornecedor() {
		return this.idAvaliacaoFornecedor;
	}

	public void setIdAvaliacaoFornecedor(Integer parm) {
		this.idAvaliacaoFornecedor = parm;
	}

	public Integer getIdFornecedor() {
		return this.idFornecedor;
	}

	public void setIdFornecedor(Integer parm) {
		this.idFornecedor = parm;
	}

	public Integer getIdResponsavel() {
		return this.idResponsavel;
	}

	public void setIdResponsavel(Integer parm) {
		this.idResponsavel = parm;
	}

	public Date getDataAvaliacao() {
		return this.dataAvaliacao;
	}

	public void setDataAvaliacao(Date parm) {
		this.dataAvaliacao = parm;
	}

	public String getDecisaoQualificacao() {
		return this.decisaoQualificacao;
	}

	public void setDecisaoQualificacao(String parm) {
		this.decisaoQualificacao = parm;
	}


	/**
	 * @return the listCriterioAvaliacaoFornecedor
	 */
	public Collection<CriterioAvaliacaoFornecedorDTO> getListCriterioAvaliacaoFornecedor() {
		return listCriterioAvaliacaoFornecedor;
	}

	/**
	 * @param listCriterioAvaliacaoFornecedor
	 *            the listCriterioAvaliacaoFornecedor to set
	 */
	public void setListCriterioAvaliacaoFornecedor(Collection<CriterioAvaliacaoFornecedorDTO> listCriterioAvaliacaoFornecedor) {
		this.listCriterioAvaliacaoFornecedor = listCriterioAvaliacaoFornecedor;
	}

	/**
	 * @return the listAvaliacaoReferenciaFornecedor
	 */
	public Collection<AvaliacaoReferenciaFornecedorDTO> getListAvaliacaoReferenciaFornecedor() {
		return listAvaliacaoReferenciaFornecedor;
	}

	/**
	 * @param listAvaliacaoReferenciaFornecedor
	 *            the listAvaliacaoReferenciaFornecedor to set
	 */
	public void setListAvaliacaoReferenciaFornecedor(Collection<AvaliacaoReferenciaFornecedorDTO> listAvaliacaoReferenciaFornecedor) {
		this.listAvaliacaoReferenciaFornecedor = listAvaliacaoReferenciaFornecedor;
	}

	/**
	 * @return the nomeResponsavel
	 */
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	/**
	 * @param nomeResponsavel the nomeResponsavel to set
	 */
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	/**
	 * @return the observacoesAvaliacaoFornecedor
	 */
	public String getObservacoesAvaliacaoFornecedor() {
		return observacoesAvaliacaoFornecedor;
	}

	/**
	 * @param observacoesAvaliacaoFornecedor the observacoesAvaliacaoFornecedor to set
	 */
	public void setObservacoesAvaliacaoFornecedor(String observacoesAvaliacaoFornecedor) {
		this.observacoesAvaliacaoFornecedor = observacoesAvaliacaoFornecedor;
	}


}
