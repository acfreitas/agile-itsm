package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class EventosDTO implements IDto {
	private Integer idEvento;
	private Integer idUsuario;
	private Integer idEmpresa;
	private String descricao;
	private String gerarQuando;
	private String ligarCasoDesl;
	private Date dataCriacao;
	private Date dataInicio;
	private Date dataFim;
	
	public Integer getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getGerarQuando() {
		return gerarQuando;
	}
	public void setGerarQuando(String gerarQuando) {
		this.gerarQuando = gerarQuando;
	}
	public String getLigarCasoDesl() {
		return ligarCasoDesl;
	}
	public void setLigarCasoDesl(String ligarCasoDesl) {
		this.ligarCasoDesl = ligarCasoDesl;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
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
