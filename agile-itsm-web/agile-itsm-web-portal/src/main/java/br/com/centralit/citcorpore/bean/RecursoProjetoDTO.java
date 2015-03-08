package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class RecursoProjetoDTO implements IDto {
	private Integer idProjeto;
	private Integer idEmpregado;
	private Double custoHora;
	
	private EmpregadoDTO empregadoDTO;

	public Integer getIdProjeto(){
		return this.idProjeto;
	}
	public void setIdProjeto(Integer parm){
		this.idProjeto = parm;
	}

	public Integer getIdEmpregado(){
		return this.idEmpregado;
	}
	public void setIdEmpregado(Integer parm){
		this.idEmpregado = parm;
	}

	public Double getCustoHora(){
		return this.custoHora;
	}
	public void setCustoHora(Double parm){
		this.custoHora = parm;
	}
	public EmpregadoDTO getEmpregadoDTO() {
		return empregadoDTO;
	}
	public void setEmpregadoDTO(EmpregadoDTO empregadoDTO) {
		this.empregadoDTO = empregadoDTO;
	}

}
