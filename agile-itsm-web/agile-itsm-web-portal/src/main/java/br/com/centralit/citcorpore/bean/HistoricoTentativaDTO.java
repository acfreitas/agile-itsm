package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class HistoricoTentativaDTO implements IDto {
    private static final long serialVersionUID = 1L;

    private Integer idHistoricoTentativa;
    private Integer idEvento;
    private Integer idEmpregado;
    private Integer idBaseItemConfiguracao;
    private Integer idItemConfiguracao;
    private String descricao;
    private String hora;
    private Date data;

    public Integer getIdHistoricoTentativa() {
	return this.idHistoricoTentativa;
    }

    public void setIdHistoricoTentativa(Integer parm) {
	this.idHistoricoTentativa = parm;
    }

    public String getDescricao() {
	return this.descricao;
    }

    public void setDescricao(String parm) {
	this.descricao = parm;
    }

    public Date getData() {
	return this.data;
    }

    public void setData(Date parm) {
	this.data = parm;
    }

    public String getHora() {
	return this.hora;
    }

    public void setHora(String parm) {
	this.hora = parm;
    }

    public Integer getIdEvento() {
	return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
	this.idEvento = idEvento;
    }

    public Integer getIdEmpregado() {
	return idEmpregado;
    }

    public void setIdEmpregado(Integer idEmpregado) {
	this.idEmpregado = idEmpregado;
    }

    public Integer getIdBaseItemConfiguracao() {
	return idBaseItemConfiguracao;
    }

    public void setIdBaseItemConfiguracao(Integer idBaseItemConfiguracao) {
	this.idBaseItemConfiguracao = idBaseItemConfiguracao;
    }

    public Integer getIdItemConfiguracao() {
	return idItemConfiguracao;
    }

    public void setIdItemConfiguracao(Integer idItemConfiguracao) {
	this.idItemConfiguracao = idItemConfiguracao;
    }

}
