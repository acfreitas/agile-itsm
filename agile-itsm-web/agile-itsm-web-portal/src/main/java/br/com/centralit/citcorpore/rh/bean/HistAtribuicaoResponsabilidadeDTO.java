package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class HistAtribuicaoResponsabilidadeDTO extends MovimentacaoPessoalDTO {

	private Integer idhistAtribuicaoResponsabilidade;
	private String descricaoPerspectivaComplexidade;
	private Integer idNivel;
	private Integer idManualFuncao;
	private Date dataAlteracao;
	private Timestamp horaAlteracao;
	private Integer idUsuarioAlteracao;
	private Double versao;
	private Integer idhistManualFuncao;
	

	public Integer getIdhistManualFuncao() {
		return idhistManualFuncao;
	}

	public void setIdhistManualFuncao(Integer idhistManualFuncao) {
		this.idhistManualFuncao = idhistManualFuncao;
	}

	public Integer getIdhistAtribuicaoResponsabilidade() {
		return idhistAtribuicaoResponsabilidade;
	}

	public void setIdhistAtribuicaoResponsabilidade(Integer idhistAtribuicaoResponsabilidade) {
		this.idhistAtribuicaoResponsabilidade = idhistAtribuicaoResponsabilidade;
	}

	public String getDescricaoPerspectivaComplexidade() {
		return descricaoPerspectivaComplexidade;
	}

	public void setDescricaoPerspectivaComplexidade(String descricaoPerspectivaComplexidade) {
		this.descricaoPerspectivaComplexidade = descricaoPerspectivaComplexidade;
	}

	public Integer getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(Integer idNivel) {
		this.idNivel = idNivel;
	}

	public Integer getIdManualFuncao() {
		return idManualFuncao;
	}

	public void setIdManualFuncao(Integer idManualFuncao) {
		this.idManualFuncao = idManualFuncao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Timestamp getHoraAlteracao() {
		return horaAlteracao;
	}

	public void setHoraAlteracao(Timestamp horaAlteracao) {
		this.horaAlteracao = horaAlteracao;
	}

	public Integer getIdUsuarioAlteracao() {
		return idUsuarioAlteracao;
	}

	public void setIdUsuarioAlteracao(Integer idUsuarioAlteracao) {
		this.idUsuarioAlteracao = idUsuarioAlteracao;
	}

	public Double getVersao() {
		return versao;
	}

	public void setVersao(Double versao) {
		this.versao = versao;
	}

}