package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class ImagemHistoricoDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6113478116221550410L;
	private Integer idImagem;
	private Date data;
	private String nomeArquivo;
	private String observacao;
	private Integer idContrato;
	private Integer idProfissional;
	private Integer idEmpresa;
	private String aba;

	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public Integer getIdImagem() {
		return idImagem;
	}
	public void setIdImagem(Integer idImagem) {
		this.idImagem = idImagem;
	}
	public Integer getIdProfissional() {
		return idProfissional;
	}
	public void setIdProfissional(Integer idProfissional) {
		this.idProfissional = idProfissional;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getAba() {
		return aba;
	}
	public void setAba(String aba) {
		this.aba = aba;
	}
	public String getKey(){
		if (getIdImagem() == null){
			return "NULL";
		}
		return getIdImagem().toString();
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}	
}
