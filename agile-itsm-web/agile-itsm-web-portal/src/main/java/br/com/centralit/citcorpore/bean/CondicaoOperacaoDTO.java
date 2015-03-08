package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author ygor.magalhaes
 *
 */
public class CondicaoOperacaoDTO implements IDto {

	private Integer idCondicaoOperacao;
	private Integer idEmpresa;
	private String nomeCondicaoOperacao;
	private Date dataInicio;
	private Date dataFim;

	public Integer getIdCondicaoOperacao() {
		return idCondicaoOperacao;
	}

	public void setIdCondicaoOperacao(Integer idCondicaoOperacao) {
		this.idCondicaoOperacao = idCondicaoOperacao;
	}

	public String getNomeCondicaoOperacao() {
		return nomeCondicaoOperacao;
	}

	public void setNomeCondicaoOperacao(String nomeCondicaoOperacao) {
		this.nomeCondicaoOperacao = nomeCondicaoOperacao;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
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

}