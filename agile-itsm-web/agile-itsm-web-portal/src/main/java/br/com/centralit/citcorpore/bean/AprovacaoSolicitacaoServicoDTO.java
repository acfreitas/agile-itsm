package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import br.com.citframework.dto.IDto;

public class AprovacaoSolicitacaoServicoDTO implements IDto {
	private Integer idAprovacaoSolicitacaoServico;
	private Integer idSolicitacaoServico;
	private Integer idResponsavel;
	private Integer idTarefa;
	private Integer idJustificativa;
	private Timestamp dataHora;
	private String complementoJustificativa;
	private String observacoes;
	private String aprovacao;

	public Integer getIdAprovacaoSolicitacaoServico(){
		return this.idAprovacaoSolicitacaoServico;
	}
	public void setIdAprovacaoSolicitacaoServico(Integer parm){
		this.idAprovacaoSolicitacaoServico = parm;
	}

	public Integer getIdSolicitacaoServico(){
		return this.idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer parm){
		this.idSolicitacaoServico = parm;
	}

	public Integer getIdResponsavel(){
		return this.idResponsavel;
	}
	public void setIdResponsavel(Integer parm){
		this.idResponsavel = parm;
	}

	public Integer getIdTarefa(){
		return this.idTarefa;
	}
	public void setIdTarefa(Integer parm){
		this.idTarefa = parm;
	}

	public Integer getIdJustificativa(){
		return this.idJustificativa;
	}
	public void setIdJustificativa(Integer parm){
		this.idJustificativa = parm;
	}

	public Timestamp getDataHora(){
		return this.dataHora;
	}
	public void setDataHora(Timestamp parm){
		this.dataHora = parm;
	}

	public String getComplementoJustificativa(){
		return this.complementoJustificativa;
	}
	public void setComplementoJustificativa(String parm){
		this.complementoJustificativa = parm;
	}

	public String getObservacoes(){
		return this.observacoes;
	}
	public void setObservacoes(String parm){
		this.observacoes = parm;
	}

	public String getAprovacao(){
		return this.aprovacao;
	}
	public void setAprovacao(String parm){
		this.aprovacao = parm;
	}
	public String getDataHoraStr() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(dataHora);
	}
}
