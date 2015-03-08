package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class PerspectivaTecnicaIdiomaDTO implements IDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idPerspectivaTecnicaIdioma;
	private String descricaoIdioma;
	private String detalheIdioma;
	private String obrigatorioIdioma;
	private Integer idSolicitacaoServico;
	private Integer idIdioma;
	
	
	public Integer getIdPerspectivaTecnicaIdioma() {
		return idPerspectivaTecnicaIdioma;
	}
	public void setIdPerspectivaTecnicaIdioma(Integer idPerspectivaTecnicaIdioma) {
		this.idPerspectivaTecnicaIdioma = idPerspectivaTecnicaIdioma;
	}
	public String getDescricaoIdioma() {
		return descricaoIdioma;
	}
	public void setDescricaoIdioma(String descricaoIdioma) {
		this.descricaoIdioma = descricaoIdioma;
	}
	public String getDetalheIdioma() {
		return detalheIdioma;
	}
	public void setDetalheIdioma(String detalheIdioma) {
		this.detalheIdioma = detalheIdioma;
	}
	public String getObrigatorioIdioma() {
		return obrigatorioIdioma;
	}
	public void setObrigatorioIdioma(String obrigatorioIdioma) {
		this.obrigatorioIdioma = obrigatorioIdioma;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Integer getIdIdioma() {
		return idIdioma;
	}
	public void setIdIdioma(Integer idIdioma) {
		this.idIdioma = idIdioma;
	}
	
	
	
	
}