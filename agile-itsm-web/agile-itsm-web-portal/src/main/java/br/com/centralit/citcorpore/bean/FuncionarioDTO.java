package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class FuncionarioDTO implements IDto {
	
	private static final long serialVersionUID = -27609697957190213L;
	
	private Integer idFuncionario;
	private Integer idEmpregado;
	private String nome;
	private String cpf;
	private Timestamp dataInicio;
	private Timestamp dataFim;
	
	public Integer getIdEmpregado() {
		return idEmpregado;
	}
	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}
	public Integer getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Timestamp getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Timestamp dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Timestamp getDataFim() {
		return dataFim;
	}
	public void setDataFim(Timestamp dataFim) {
		this.dataFim = dataFim;
	}
	
}