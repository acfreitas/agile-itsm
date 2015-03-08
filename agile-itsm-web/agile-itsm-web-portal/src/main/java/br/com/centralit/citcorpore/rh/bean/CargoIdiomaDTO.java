package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class CargoIdiomaDTO implements IDto {
	private Integer idDescricaoCargo;
	private Integer idIdioma;
	private String obrigatorio;
	
	private String descricao;
	private String detalhe;
	
	public String getDetalhe() {
			return detalhe;
	}
	public void setDetalhe(String detalhe) {
			this.detalhe = detalhe;
	}	
	public Integer getIdDescricaoCargo() {
		return idDescricaoCargo;
	}

	public void setIdDescricaoCargo(Integer idDescricaoCargo) {
		this.idDescricaoCargo = idDescricaoCargo;
	}

	public Integer getIdIdioma() {
		return idIdioma;
	}

	public void setIdIdioma(Integer idIdioma) {
		this.idIdioma = idIdioma;
	}

	public String getObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(String obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	

}
