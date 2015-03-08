package br.com.centralit.citcorpore.bean;


import java.sql.Date;

import br.com.citframework.dto.IDto;

public class RelatorioQuantitativoRetornoDTO implements IDto {

	private static final long serialVersionUID = 1L;
	
	private Integer idOcorrencia;
	private Integer idInstancia;
	private Integer idSolicitacaoServico;
	private Date dataRegistro;
	private String horaRegistro;
	private String nome;
	private String ocorrencia;
	private String dataCompleta;
	private Integer idUsuario;
	private Integer idItemTrabalho;
	private Integer idElemento;
	private Integer idFluxo;
	
	public Integer getIdOcorrencia() {
		return idOcorrencia;
	}
	public void setIdOcorrencia(Integer idOcorrencia) {
		this.idOcorrencia = idOcorrencia;
	}
	public Integer getIdInstancia() {
		return idInstancia;
	}
	public void setIdInstancia(Integer idInstancia) {
		this.idInstancia = idInstancia;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Date getDataRegistro() {
		return dataRegistro;
	}
	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}
	public String getHoraRegistro() {
		return horaRegistro;
	}
	public void setHoraRegistro(String horaRegistro) {
		this.horaRegistro = horaRegistro;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getOcorrencia() {
		return ocorrencia;
	}
	public void setOcorrencia(String ocorrencia) {
		this.ocorrencia = ocorrencia;
	}
	public String getDataCompleta() {
		return dataCompleta;
	}
	public void setDataCompleta(String dataCompleta) {
		this.dataCompleta = dataCompleta;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdItemTrabalho() {
		return idItemTrabalho;
	}
	public void setIdItemTrabalho(Integer idItemTrabalho) {
		this.idItemTrabalho = idItemTrabalho;
	}
	public Integer getIdElemento() {
		return idElemento;
	}
	public void setIdElemento(Integer idElemento) {
		this.idElemento = idElemento;
	}
	public Integer getIdFluxo() {
		return idFluxo;
	}
	public void setIdFluxo(Integer idFluxo) {
		this.idFluxo = idFluxo;
	}
}
