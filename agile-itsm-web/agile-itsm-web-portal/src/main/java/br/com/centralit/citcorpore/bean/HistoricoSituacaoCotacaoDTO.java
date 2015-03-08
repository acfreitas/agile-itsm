package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class HistoricoSituacaoCotacaoDTO implements IDto {
	private Integer idHistorico;
	private Integer idCotacao;
	private Integer idResponsavel;
	private Timestamp dataHora;
	private String situacao;

	public Integer getIdHistorico(){
		return this.idHistorico;
	}
	public void setIdHistorico(Integer parm){
		this.idHistorico = parm;
	}

	public Integer getIdCotacao(){
		return this.idCotacao;
	}
	public void setIdCotacao(Integer parm){
		this.idCotacao = parm;
	}

	public Integer getIdResponsavel(){
		return this.idResponsavel;
	}
	public void setIdResponsavel(Integer parm){
		this.idResponsavel = parm;
	}

	public Timestamp getDataHora(){
		return this.dataHora;
	}
	public void setDataHora(Timestamp parm){
		this.dataHora = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}

}
