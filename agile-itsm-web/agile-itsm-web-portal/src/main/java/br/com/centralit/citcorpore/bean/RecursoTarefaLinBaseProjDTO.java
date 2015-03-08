package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class RecursoTarefaLinBaseProjDTO implements IDto {
	private Integer idRecursoTarefaLinBaseProj;
	private Integer idTarefaLinhaBaseProjeto;
	private Integer idPerfilContrato;
	private Integer idEmpregado;
	private Double percentualAloc;
	private String tempoAloc;
	private Double percentualExec;
	private Double tempoAlocMinutos;
	private Double custo;
	private Double custoPerfil;
	private String esforcoPorOS;
	
	private EmpregadoDTO empregadoDTO;
	
	private double tempoAlocEmMinutos = 0;

	public Integer getIdRecursoTarefaLinBaseProj(){
		return this.idRecursoTarefaLinBaseProj;
	}
	public void setIdRecursoTarefaLinBaseProj(Integer parm){
		this.idRecursoTarefaLinBaseProj = parm;
	}

	public Integer getIdTarefaLinhaBaseProjeto(){
		return this.idTarefaLinhaBaseProjeto;
	}
	public void setIdTarefaLinhaBaseProjeto(Integer parm){
		this.idTarefaLinhaBaseProjeto = parm;
	}

	public Integer getIdPerfilContrato(){
		return this.idPerfilContrato;
	}
	public void setIdPerfilContrato(Integer parm){
		this.idPerfilContrato = parm;
	}

	public Integer getIdEmpregado(){
		return this.idEmpregado;
	}
	public void setIdEmpregado(Integer parm){
		this.idEmpregado = parm;
	}

	public Double getPercentualAloc(){
		return this.percentualAloc;
	}
	public void setPercentualAloc(Double parm){
		this.percentualAloc = parm;
	}
	public String getTempoAloc() {
		return tempoAloc;
	}
	public void setTempoAloc(String tempoAloc) {
		this.tempoAloc = tempoAloc;
	}
	public Double getPercentualExec() {
		return percentualExec;
	}
	public void setPercentualExec(Double percentualExec) {
		this.percentualExec = percentualExec;
	}
	public double getTempoAlocEmMinutos() {
		return tempoAlocEmMinutos;
	}
	public void setTempoAlocEmMinutos(double tempoAlocEmMinutos) {
		this.tempoAlocEmMinutos = tempoAlocEmMinutos;
	}
	public EmpregadoDTO getEmpregadoDTO() {
		return empregadoDTO;
	}
	public void setEmpregadoDTO(EmpregadoDTO empregadoDTO) {
		this.empregadoDTO = empregadoDTO;
	}
	public Double getTempoAlocMinutos() {
		return tempoAlocMinutos;
	}
	public void setTempoAlocMinutos(Double tempoAlocMinutos) {
		this.tempoAlocMinutos = tempoAlocMinutos;
	}
	public Double getCusto() {
		return custo;
	}
	public void setCusto(Double custo) {
		this.custo = custo;
	}
	public Double getCustoPerfil() {
		return custoPerfil;
	}
	public void setCustoPerfil(Double custoPerfil) {
		this.custoPerfil = custoPerfil;
	}
	public String getEsforcoPorOS() {
		return esforcoPorOS;
	}
	public void setEsforcoPorOS(String esforcoPorOS) {
		this.esforcoPorOS = esforcoPorOS;
	}

}
