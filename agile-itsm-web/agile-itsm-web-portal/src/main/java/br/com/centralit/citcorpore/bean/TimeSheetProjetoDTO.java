package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class TimeSheetProjetoDTO implements IDto {
	private Integer idTimeSheetProjeto;
	private Integer idRecursoTarefaLinBaseProj;
	private java.sql.Timestamp dataHoraReg;
	private java.sql.Date data;
	private String hora;
	private Double qtdeHoras;
	private Double custo;
	private String detalhamento;
	private Double percExecutado;
	
	private Integer idLinhaBaseProjeto;

	public Integer getIdTimeSheetProjeto(){
		return this.idTimeSheetProjeto;
	}
	public void setIdTimeSheetProjeto(Integer parm){
		this.idTimeSheetProjeto = parm;
	}

	public Integer getIdRecursoTarefaLinBaseProj(){
		return this.idRecursoTarefaLinBaseProj;
	}
	public void setIdRecursoTarefaLinBaseProj(Integer parm){
		this.idRecursoTarefaLinBaseProj = parm;
	}

	public java.sql.Timestamp getDataHoraReg(){
		return this.dataHoraReg;
	}
	public void setDataHoraReg(java.sql.Timestamp parm){
		this.dataHoraReg = parm;
	}

	public java.sql.Date getData(){
		return this.data;
	}
	public void setData(java.sql.Date parm){
		this.data = parm;
	}

	public String getHora(){
		return this.hora;
	}
	public void setHora(String parm){
		this.hora = parm;
	}

	public Double getQtdeHoras(){
		return this.qtdeHoras;
	}
	public void setQtdeHoras(Double parm){
		this.qtdeHoras = parm;
	}

	public Double getCusto(){
		return this.custo;
	}
	public void setCusto(Double parm){
		this.custo = parm;
	}

	public String getDetalhamento(){
		return this.detalhamento;
	}
	public void setDetalhamento(String parm){
		this.detalhamento = parm;
	}
	public Double getPercExecutado() {
		return percExecutado;
	}
	public void setPercExecutado(Double percExecutado) {
		this.percExecutado = percExecutado;
	}
	public Integer getIdLinhaBaseProjeto() {
		return idLinhaBaseProjeto;
	}
	public void setIdLinhaBaseProjeto(Integer idLinhaBaseProjeto) {
		this.idLinhaBaseProjeto = idLinhaBaseProjeto;
	}

}
