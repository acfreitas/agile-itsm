package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class CidadesDTO implements IDto{
    
	private static final long serialVersionUID = -8199400906988594799L;
	
	private Integer idCidade;
	private Integer IdUf;
	private String nomeCidade;
	private String nomeUf;
	
	public Integer getIdCidade() {
		return idCidade;
	}
	
	public void setIdCidade(Integer idCidade) {
		this.idCidade = idCidade;
	}
	
	public Integer getIdUf() {
		return IdUf;
	}
	
	public void setIdUf(Integer idUf) {
		IdUf = idUf;
	}
	
	public String getNomeCidade() {
		return nomeCidade;
	}
	
	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}
	
	public String getNomeUf() {
		return nomeUf;
	}
	
	public void setNomeUf(String nomeUf) {
		this.nomeUf = nomeUf;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}