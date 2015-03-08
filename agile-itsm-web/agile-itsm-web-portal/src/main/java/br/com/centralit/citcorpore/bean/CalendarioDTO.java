package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.UtilDatas;

public class CalendarioDTO implements IDto {
    /**
     * 
     */
    private static final long serialVersionUID = 4675705104260302480L;
    private Integer idCalendario;
    private String descricao;
    private String consideraFeriados;
    private Integer idJornadaSeg;
    private Integer idJornadaTer;
    private Integer idJornadaQua;
    private Integer idJornadaQui;
    private Integer idJornadaSex;
    private Integer idJornadaSab;
    private Integer idJornadaDom;
    private String permiteDataInferiorHoje;

    public Integer getIdCalendario() {
	return this.idCalendario;
    }

    public void setIdCalendario(Integer parm) {
	this.idCalendario = parm;
    }

    public String getDescricao() {
	return this.descricao;
    }

    public void setDescricao(String parm) {
	this.descricao = parm;
    }

    public String getConsideraFeriados() {
	return this.consideraFeriados;
    }

    public void setConsideraFeriados(String parm) {
	this.consideraFeriados = parm;
    }

    public Integer getIdJornadaSeg() {
	return this.idJornadaSeg;
    }

    public void setIdJornadaSeg(Integer parm) {
	if(parm == null || parm == 0){
	    return;
	}
	this.idJornadaSeg = parm;
    }

    public Integer getIdJornadaTer() {
	return this.idJornadaTer;
    }

    public void setIdJornadaTer(Integer parm) {
	if(parm == null || parm == 0){
	    return;
	}
	this.idJornadaTer = parm;
    }

    public Integer getIdJornadaQua() {
	return this.idJornadaQua;
    }

    public void setIdJornadaQua(Integer parm) {
	if(parm == null || parm == 0){
	    return;
	}
	this.idJornadaQua = parm;
    }

    public Integer getIdJornadaQui() {
	return this.idJornadaQui;
    }

    public void setIdJornadaQui(Integer parm) {
	if(parm == null || parm == 0){
	    return;
	}
	this.idJornadaQui = parm;
    }

    public Integer getIdJornadaSex() {
	return this.idJornadaSex;
    }

    public void setIdJornadaSex(Integer parm) {
	if(parm == null || parm == 0){
	    return;
	}
	this.idJornadaSex = parm;
    }

    public Integer getIdJornadaSab() {
	return this.idJornadaSab;
    }

    public void setIdJornadaSab(Integer parm) {
	if(parm == null || parm == 0){
	    return;
	}
	this.idJornadaSab = parm;
    }

    public Integer getIdJornadaDom() {
	return this.idJornadaDom;
    }

    public void setIdJornadaDom(Integer parm) {
	if(parm == null || parm == 0){
	    return;
	}
	this.idJornadaDom = parm;
    }

    public Integer getIdJornada(Date dataRef) throws LogicException {
		Integer idJornada = null;
		int diaSemana = UtilDatas.getDiaSemana(UtilDatas.dateToSTR(dataRef));
		switch (diaSemana) {
			case 1:
				idJornada = this.getIdJornadaDom();
				break;
			case 2:
				idJornada = this.getIdJornadaSeg();
				break;
			case 3:
				idJornada = this.getIdJornadaTer();
				break;
			case 4:
				idJornada = this.getIdJornadaQua();
				break;
			case 5:
				idJornada = this.getIdJornadaQui();
				break;
			case 6:
				idJornada = this.getIdJornadaSex();
				break;
			case 7:
				idJornada = this.getIdJornadaSab();
				break;
			default:
				break;
		}
		return idJornada;
    }

	public String getPermiteDataInferiorHoje() {
		return permiteDataInferiorHoje;
	}

	public void setPermiteDataInferiorHoje(String permiteDataInferiorHoje) {
		this.permiteDataInferiorHoje = permiteDataInferiorHoje;
	}
}
