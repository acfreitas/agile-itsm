package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ValorLimiteAprovacaoDTO implements IDto {
	private Integer idValorLimiteAprovacao;
	private Integer idLimiteAprovacao;
	private String tipoUtilizacao;
	private String tipoLimite;
	private Double valorLimite;
	private Integer intervaloDias;
	
	private Integer sequencia;

	public Integer getIdValorLimiteAprovacao(){
		return this.idValorLimiteAprovacao;
	}
	public void setIdValorLimiteAprovacao(Integer parm){
		this.idValorLimiteAprovacao = parm;
	}

	public Integer getIdLimiteAprovacao(){
		return this.idLimiteAprovacao;
	}
	public void setIdLimiteAprovacao(Integer parm){
		this.idLimiteAprovacao = parm;
	}

	public String getTipoUtilizacao(){
		return this.tipoUtilizacao;
	}
	public void setTipoUtilizacao(String parm){
		this.tipoUtilizacao = parm;
	}

	public String getTipoLimite(){
		return this.tipoLimite;
	}
	public void setTipoLimite(String parm){
		this.tipoLimite = parm;
	}

	public Double getValorLimite(){
		return this.valorLimite;
	}
	public void setValorLimite(Double parm){
		this.valorLimite = parm;
	}

	public Integer getIntervaloDias(){
		return this.intervaloDias;
	}
	public void setIntervaloDias(Integer parm){
		this.intervaloDias = parm;
	}
	public Integer getSequencia() {
		return sequencia;
	}
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}

}
