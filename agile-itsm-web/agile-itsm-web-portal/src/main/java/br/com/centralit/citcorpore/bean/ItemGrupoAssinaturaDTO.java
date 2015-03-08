package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class ItemGrupoAssinaturaDTO implements IDto{

    private static final long serialVersionUID = -6639560890633897592L;

    private Integer idItemGrupoAssinatura;
    private Integer idGrupoAssinatura;
    private Integer idAssinatura;
    private Integer ordem;
    private Date dataInicio;
    private Date dataFim;

    //Campos auxiliares para apresentação na tela
    private String nomeResponsavel;
    private String papel;
    private String fase;

    public Integer getIdItemGrupoAssinatura() {
	return this.idItemGrupoAssinatura;
    }
    public void setIdItemGrupoAssinatura(Integer idItemGrupoAssinatura) {
	this.idItemGrupoAssinatura = idItemGrupoAssinatura;
    }
    public Integer getIdGrupoAssinatura() {
	return this.idGrupoAssinatura;
    }
    public void setIdGrupoAssinatura(Integer idGrupoAssinatura) {
	this.idGrupoAssinatura = idGrupoAssinatura;
    }
    public Integer getIdAssinatura() {
	return this.idAssinatura;
    }
    public void setIdAssinatura(Integer idAssinatura) {
	this.idAssinatura = idAssinatura;
    }
    public Integer getOrdem() {
	return this.ordem;
    }
    public void setOrdem(Integer ordem) {
	this.ordem = ordem;
    }
    public Date getDataInicio() {
	return this.dataInicio;
    }
    public void setDataInicio(Date dataInicio) {
	this.dataInicio = dataInicio;
    }
    public Date getDataFim() {
	return this.dataFim;
    }
    public void setDataFim(Date dataFim) {
	this.dataFim = dataFim;
    }

    public String getNomeResponsavel() {
	return this.nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
	this.nomeResponsavel = nomeResponsavel;
    }

    public String getPapel() {
	return this.papel;
    }

    public void setPapel(String papel) {
	this.papel = papel;
    }

    public String getFase() {
	return this.fase;
    }

    public void setFase(String fase) {
	this.fase = fase;
    }

}
