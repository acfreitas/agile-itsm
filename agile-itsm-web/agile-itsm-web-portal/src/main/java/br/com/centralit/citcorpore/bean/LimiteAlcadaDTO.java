package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class LimiteAlcadaDTO implements IDto {
	private Integer idLimiteAlcada;
	private Integer idAlcada;
	private Integer idGrupo;
	private String tipoLimite;
	private String abrangenciaCentroCusto;
	private Double limiteItemUsoInterno;
	private Double limiteMensalUsoInterno;
    private Double limiteItemAtendCliente;
    private Double limiteMensalAtendCliente;
	private String situacao;

	public Integer getIdLimiteAlcada(){
		return this.idLimiteAlcada;
	}
	public void setIdLimiteAlcada(Integer parm){
		this.idLimiteAlcada = parm;
	}

	public Integer getIdAlcada(){
		return this.idAlcada;
	}
	public void setIdAlcada(Integer parm){
		this.idAlcada = parm;
	}

	public Integer getIdGrupo(){
		return this.idGrupo;
	}
	public void setIdGrupo(Integer parm){
		this.idGrupo = parm;
	}

	public String getTipoLimite(){
		return this.tipoLimite;
	}
	public void setTipoLimite(String parm){
		this.tipoLimite = parm;
	}

	public String getAbrangenciaCentroCusto(){
		return this.abrangenciaCentroCusto;
	}
	public void setAbrangenciaCentroCusto(String parm){
		this.abrangenciaCentroCusto = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}
    public Double getLimiteItemUsoInterno() {
        return limiteItemUsoInterno;
    }
    public void setLimiteItemUsoInterno(Double limiteItemUsoInterno) {
        this.limiteItemUsoInterno = limiteItemUsoInterno;
    }
    public Double getLimiteMensalUsoInterno() {
        return limiteMensalUsoInterno;
    }
    public void setLimiteMensalUsoInterno(Double limiteMensalUsoInterno) {
        this.limiteMensalUsoInterno = limiteMensalUsoInterno;
    }
    public Double getLimiteItemAtendCliente() {
        return limiteItemAtendCliente;
    }
    public void setLimiteItemAtendCliente(Double limiteItemAtendCliente) {
        this.limiteItemAtendCliente = limiteItemAtendCliente;
    }
    public Double getLimiteMensalAtendCliente() {
        return limiteMensalAtendCliente;
    }
    public void setLimiteMensalAtendCliente(Double limiteMensalAtendCliente) {
        this.limiteMensalAtendCliente = limiteMensalAtendCliente;
    }

}
