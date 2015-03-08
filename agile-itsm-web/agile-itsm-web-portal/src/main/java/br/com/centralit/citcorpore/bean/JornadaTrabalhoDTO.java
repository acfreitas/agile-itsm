package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;

import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.dto.IDto;


public class JornadaTrabalhoDTO implements IDto {
	private Integer idJornada;
	private String descricao;
	private String inicio1;
	private String termino1;
	private String inicio2;
	private String termino2;
	private String inicio3;
	private String termino3;
	private String inicio4;
	private String termino4;
	private String inicio5;
	private String termino5;
	private String cargaHoraria;
	private Date dataInicio;
	private Date dataFim;
	
	private Timestamp dataHoraInicial;
	
	private double[] inicio;
    private double[] termino;

    public Integer getIdJornada(){
		return this.idJornada;
	}
	public void setIdJornada(Integer parm){
		this.idJornada = parm;
	}

	public String getDescricao(){
		return this.descricao;
	}
	public void setDescricao(String parm){
		this.descricao = parm;
	}

	public String getInicio1(){
		return this.inicio1;
	}
	public void setInicio1(String parm){
		this.inicio1 = parm;
	}

	public String getTermino1(){
		return this.termino1;
	}
	public void setTermino1(String parm){
		this.termino1 = parm;
	}

	public String getInicio2(){
		return this.inicio2;
	}
	public void setInicio2(String parm){
		this.inicio2 = parm;
	}

	public String getTermino2(){
		return this.termino2;
	}
	public void setTermino2(String parm){
		this.termino2 = parm;
	}

	public String getInicio3(){
		return this.inicio3;
	}
	public void setInicio3(String parm){
		this.inicio3 = parm;
	}

	public String getTermino3(){
		return this.termino3;
	}
	public void setTermino3(String parm){
		this.termino3 = parm;
	}

	public String getInicio4(){
		return this.inicio4;
	}
	public void setInicio4(String parm){
		this.inicio4 = parm;
	}

	public String getTermino4(){
		return this.termino4;
	}
	public void setTermino4(String parm){
		this.termino4 = parm;
	}

	public String getInicio5(){
		return this.inicio5;
	}
	public void setInicio5(String parm){
		this.inicio5 = parm;
	}

	public String getTermino5(){
		return this.termino5;
	}
	public void setTermino5(String parm){
		this.termino5 = parm;
	}

	public String getCargaHoraria(){
		return this.cargaHoraria;
	}
	public void setCargaHoraria(String parm){
		this.cargaHoraria = parm;
	}
	public String getInicio(int i) {
		String result = null;
		switch (i) {
			case 1:
				if (getInicio1() != null && getInicio1().trim().length() > 0)
					result = getInicio1();
				break;
			case 2:
				if (getInicio2() != null && getInicio2().trim().length() > 0)
					result = getInicio2();
				break;
			case 3:
				if (getInicio3() != null && getInicio3().trim().length() > 0)
					result = getInicio3();
				break;
			case 4:
				if (getInicio4() != null && getInicio4().trim().length() > 0)
					result = getInicio4();
				break;
			case 5:
				if (getInicio5() != null && getInicio5().trim().length() > 0)
					result = getInicio5();
				break;
		}
		return result;
	}
	public String getTermino(int i) {
		String result = null;
		switch (i) {
			case 1:
				if (getTermino1() != null && getTermino1().trim().length() > 0)
					result = getTermino1();
				break;
			case 2:
				if (getTermino2() != null && getTermino2().trim().length() > 0)
					result = getTermino2();
				break;
			case 3:
				if (getTermino3() != null && getTermino3().trim().length() > 0)
					result = getTermino3();
				break;
			case 4:
				if (getTermino4() != null && getTermino4().trim().length() > 0)
					result = getTermino4();
				break;
			case 5:
				if (getTermino5() != null && getTermino5().trim().length() > 0)
					result = getTermino5();
				break;
		}
		return result;
	}
	public double[] getInicio() {
        inicio = new double[] {99,99,99,99,99,99};
        for (int i = 1; i <= 5; i++) {
            if (this.getInicio(i) != null) {
                try {
                    inicio[i] = Util.getHoraDbl(this.getInicio(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return inicio;
    }
    public void setInicio(double[] inicio) {
        this.inicio = inicio;
    }
    public double[] getTermino() {
        termino = new double[] {0,0,0,0,0,0};
        for (int i = 1; i <= 5; i++) {
            if (this.getInicio(i) != null) {
                try {
                    termino[i] = Util.getHoraDbl(this.getTermino(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return termino;
    }
    public void setTermino(double[] termino) {
        this.termino = termino;
    }
	public Timestamp getDataHoraInicial() {
		return dataHoraInicial;
	}
	public void setDataHoraInicial(Timestamp dataHoraInicial) {
		this.dataHoraInicial = dataHoraInicial;
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
