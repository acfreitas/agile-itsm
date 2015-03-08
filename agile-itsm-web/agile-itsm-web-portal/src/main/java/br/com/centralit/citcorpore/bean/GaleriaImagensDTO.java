package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilStrings;

public class GaleriaImagensDTO implements IDto {
	private Integer idImagem;
	private Integer idCategoriaGaleriaImagem;
	private String nomeImagem;
	private String descricaoImagem;
	private String detalhamento;
	private String extensao;
	private String nomeCategoria;
	private String selecaoImagemEdicao;
	
	public Integer getIdImagem() {
		return idImagem;
	}
	public void setIdImagem(Integer idImagem) {
		this.idImagem = idImagem;
	}
	public Integer getIdCategoriaGaleriaImagem() {
		return idCategoriaGaleriaImagem;
	}
	public void setIdCategoriaGaleriaImagem(Integer idCategoriaGaleriaImagem) {
		this.idCategoriaGaleriaImagem = idCategoriaGaleriaImagem;
	}
	public String getNomeImagem() {
		return nomeImagem;
	}
	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}
	public void setDescricaoAndCategoriaImagem(String str) {
		
	}
	public String getDescricaoAndCategoriaImagem() {
		return UtilStrings.nullToVazio(getNomeCategoria()) + " - " + UtilStrings.nullToVazio(getDescricaoImagem());
	}
	public String getDescricaoImagem() {
		return descricaoImagem;
	}
	public void setDescricaoImagem(String descricaoImagem) {
		this.descricaoImagem = descricaoImagem;
	}
	public String getDetalhamento() {
		return detalhamento;
	}
	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}
	public String getExtensao() {
		return extensao;
	}
	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	public String getSelecaoImagemEdicao() {
		return selecaoImagemEdicao;
	}
	public void setSelecaoImagemEdicao(String selecaoImagemEdicao) {
		this.selecaoImagemEdicao = selecaoImagemEdicao;
	}
	
}
