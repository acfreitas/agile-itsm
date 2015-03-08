package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class HistoricoCandidatoDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idHistoricoCandidato;
	private Integer idEntrevista;
	private Integer idCurriculo;
	private String resultado;
	private Integer idSolicitacaoServico;
	private String nome;
	private String funcao;
	
	public Integer getIdHistoricoCandidato() {
		return idHistoricoCandidato;
	}
	public void setIdHistoricoCandidato(Integer idHistoricoCandidato) {
		this.idHistoricoCandidato = idHistoricoCandidato;
	}
	public Integer getIdEntrevista() {
		return idEntrevista;
	}
	public void setIdEntrevista(Integer idEntrevista) {
		this.idEntrevista = idEntrevista;
	}
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFuncao() {
		return funcao;
	}
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	
	}
