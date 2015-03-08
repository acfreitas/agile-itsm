package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class SolicitacaoServicoEvtMonDTO implements IDto {
	private Integer idSolicitacaoServico;
	private Integer idEventoMonitoramento;
	private Integer idRecurso;
	private String nomeHost;
	private String nomeService;
	private String infoAdd;

	public Integer getIdSolicitacaoServico(){
		return this.idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer parm){
		this.idSolicitacaoServico = parm;
	}

	public Integer getIdEventoMonitoramento(){
		return this.idEventoMonitoramento;
	}
	public void setIdEventoMonitoramento(Integer parm){
		this.idEventoMonitoramento = parm;
	}

	public Integer getIdRecurso(){
		return this.idRecurso;
	}
	public void setIdRecurso(Integer parm){
		this.idRecurso = parm;
	}

	public String getNomeHost(){
		return this.nomeHost;
	}
	public void setNomeHost(String parm){
		this.nomeHost = parm;
	}

	public String getNomeService(){
		return this.nomeService;
	}
	public void setNomeService(String parm){
		this.nomeService = parm;
	}

	public String getInfoAdd(){
		return this.infoAdd;
	}
	public void setInfoAdd(String parm){
		this.infoAdd = parm;
	}

}
