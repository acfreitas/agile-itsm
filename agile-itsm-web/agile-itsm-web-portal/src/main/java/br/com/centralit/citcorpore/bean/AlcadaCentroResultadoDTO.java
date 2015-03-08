package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilDatas;

public class AlcadaCentroResultadoDTO implements IDto {
	
	private static final long serialVersionUID = 4182540164619994646L;
	
	private Integer idAlcadaCentroResultado;
	private Integer idCentroResultado;
	private Integer idEmpregado;
	private Integer idAlcada;
	private Date dataInicio;
	private Date dataFim;
	private String nomeEmpregado;
	private String nomeCentroResultado;
	private String nomeAlcada;
	
	private Integer sequencia;
	private String dataInicioStr;
	private String dataFimStr;
		
	public Integer getIdAlcadaCentroResultado() {
		return idAlcadaCentroResultado;
	}
	
	public void setIdAlcadaCentroResultado(Integer idAlcadaCentroResultado) {
		this.idAlcadaCentroResultado = idAlcadaCentroResultado;
	}
	
	public Integer getIdCentroResultado() {
		return idCentroResultado;
	}
	
	public void setIdCentroResultado(Integer idCentroResultado) {
		this.idCentroResultado = idCentroResultado;
	}
	
	public Integer getIdEmpregado() {
		return idEmpregado;
	}
	
	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}
	
	public Integer getIdAlcada() {
		return idAlcada;
	}
	
	public void setIdAlcada(Integer idAlcada) {
		this.idAlcada = idAlcada;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
		if (dataInicio != null)
		    this.dataInicioStr = UtilDatas.dateToSTR(dataInicio);
	}
	
	public Date getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	    if (dataFim != null)
	        this.dataFimStr = UtilDatas.dateToSTR(dataFim);
	}
	
	public String getNomeEmpregado() {
		return this.nomeEmpregado;
	}
	
	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}
	
	public String getNomeCentroResultado() {
		return this.nomeCentroResultado;
	}
	
	public void setNomeCentroResultado(String nomeCentroResultado) {
		this.nomeCentroResultado = nomeCentroResultado;
	}
	
	public String getNomeAlcada() {
		return this.nomeAlcada;
	}
	
	public void setNomeAlcada(String nomeAlcada) {
		this.nomeAlcada = nomeAlcada;
	}

    public String getDataInicioStr() {
        return dataInicioStr;
    }

    public void setDataInicioStr(String dataInicioStr) {
        this.dataInicioStr = dataInicioStr;
    }

    public String getDataFimStr() {
        return dataFimStr;
    }

    public void setDataFimStr(String dataFimStr) {
        this.dataFimStr = dataFimStr;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }
}
