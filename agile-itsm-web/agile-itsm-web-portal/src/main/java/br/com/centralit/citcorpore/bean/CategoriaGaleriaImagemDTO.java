package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class CategoriaGaleriaImagemDTO implements IDto {
	
	private static final long serialVersionUID = 1505208303547612863L;
	private Integer idCategoriaGaleriaImagem;
	private String nomeCategoria;
	private Date dataInicio;
	private Date dataFim;
	
	public Integer getIdCategoriaGaleriaImagem() {
		return idCategoriaGaleriaImagem;
	}
	public void setIdCategoriaGaleriaImagem(Integer idCategoriaGaleriaImagem) {
		this.idCategoriaGaleriaImagem = idCategoriaGaleriaImagem;
	}
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
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
