package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author david.silva
 *
 */
public class BlackListDTO implements IDto{

	private static final long serialVersionUID = 1L;
	
	private Integer idListaNegra;
	private Integer idCandidato;
	private Integer idJustificativa;
	private Integer idResponsavel;
	private String descricao;
	private Date dataInicio;
	private Date dataFim;
	
	private String acao;
	
	public Integer getIdListaNegra() {
		return idListaNegra;
	}
	public void setIdListaNegra(Integer idListaNegra) {
		this.idListaNegra = idListaNegra;
	}
	public Integer getIdCandidato() {
		return idCandidato;
	}
	public void setIdCandidato(Integer idCandidato) {
		this.idCandidato = idCandidato;
	}
	public Integer getIdJustificativa() {
		return idJustificativa;
	}
	public void setIdJustificativa(Integer idJustificativa) {
		this.idJustificativa = idJustificativa;
	}
	public Integer getIdResponsavel() {
		return idResponsavel;
	}
	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	
}
