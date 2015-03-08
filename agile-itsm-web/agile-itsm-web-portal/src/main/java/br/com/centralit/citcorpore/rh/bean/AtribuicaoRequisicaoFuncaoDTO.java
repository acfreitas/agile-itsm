package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class AtribuicaoRequisicaoFuncaoDTO implements IDto {

	private static final long serialVersionUID = 1L;
	private Integer idAtribuicao;
	private String descricao;
	private String detalhe;
	private Date dataInicio;
	private Date dataFim;

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getIdAtribuicao() {
		return idAtribuicao;
	}
	public void setIdAtribuicao(Integer idAtribuicao) {
		this.idAtribuicao = idAtribuicao;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public String getDetalhe() {
		return detalhe;
	}
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
}

