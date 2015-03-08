package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class PesquisaCurriculoDTO implements IDto {

	private static final long serialVersionUID = 1L;

	private String pesquisa_chave;
	private String pesquisa_formacao;
	private String pesquisa_certificacao;
	private String pesquisa_idiomas;
	private String pesquisa_cidade;
	private String funcao;
	private Integer idCurriculo;
	private Integer idCandidato;
	private Integer idHistorico;
	private Integer idPais;
	
	public String getPesquisa_chave() {
		return pesquisa_chave;
	}
	public void setPesquisa_chave(String pesquisa_chave) {
		this.pesquisa_chave = pesquisa_chave;
	}
	public String getPesquisa_formacao() {
		return pesquisa_formacao;
	}
	public void setPesquisa_formacao(String pesquisa_formacao) {
		this.pesquisa_formacao = pesquisa_formacao;
	}
	public String getPesquisa_certificacao() {
		return pesquisa_certificacao;
	}
	public void setPesquisa_certificacao(String pesquisa_certificacao) {
		this.pesquisa_certificacao = pesquisa_certificacao;
	}
	public String getPesquisa_idiomas() {
		return pesquisa_idiomas;
	}
	public void setPesquisa_idiomas(String pesquisa_idiomas) {
		this.pesquisa_idiomas = pesquisa_idiomas;
	}
	public String getPesquisa_cidade() {
		return pesquisa_cidade;
	}
	public void setPesquisa_cidade(String pesquisa_cidade) {
		this.pesquisa_cidade = pesquisa_cidade;
	}
	public String getFuncao() {
		return funcao;
	}
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	public Integer getIdCandidato() {
		return idCandidato;
	}
	public void setIdCandidato(Integer idCandidato) {
		this.idCandidato = idCandidato;
	}
	public Integer getIdHistorico() {
		return idHistorico;
	}
	public void setIdHistorico(Integer idHistorico) {
		this.idHistorico = idHistorico;
	}
	public Integer getIdPais() {
		return idPais;
	}
	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}
	
}