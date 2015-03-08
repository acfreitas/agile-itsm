package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;


public class FeriadoDTO implements IDto{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer idFeriado;
    private Date data;	
	private String descricao;
	private String abrangencia;
	private Integer idUf;
	private Integer idCidade;
	private String recorrente;
	
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getAbrangencia() {
        return abrangencia;
    }
    public void setAbrangencia(String abrangencia) {
        this.abrangencia = abrangencia;
    }
    public Integer getIdUf() {
        return idUf;
    }
    public void setIdUf(Integer idUf) {
        this.idUf = idUf;
    }
    public Integer getIdCidade() {
        return idCidade;
    }
    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }
    public String getRecorrente() {
        return recorrente;
    }
    public void setRecorrente(String recorrente) {
        this.recorrente = recorrente;
    }
	public Integer getIdFeriado() {
		return idFeriado;
	}
	public void setIdFeriado(Integer idFeriado) {
		this.idFeriado = idFeriado;
	}
	
	

}
	
	

	

	
	

