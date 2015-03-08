package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class ExperienciaProfissionalRequisicaoPessoalDTO implements IDto {

	private Integer idExperienciaProfissional;
	
	private String descricaoCargo;
	private String descricaoEmpresa;
	private Integer idCurriculo;
	
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	public String getDescricaoCargo() {
		return descricaoCargo;
	}
	public void setDescricaoCargo(String descricaoCargo) {
		this.descricaoCargo = descricaoCargo;
	}
	public Integer getIdExperienciaProfissional() {
		return idExperienciaProfissional;
	}
	public void setIdExperienciaProfissional(Integer idExperienciaProfissional) {
		this.idExperienciaProfissional = idExperienciaProfissional;
	}
	public String getDescricaoEmpresa() {
		return descricaoEmpresa;
	}
	public void setDescricaoEmpresa(String descricaoEmpresa) {
		this.descricaoEmpresa = descricaoEmpresa;
	}
	
}