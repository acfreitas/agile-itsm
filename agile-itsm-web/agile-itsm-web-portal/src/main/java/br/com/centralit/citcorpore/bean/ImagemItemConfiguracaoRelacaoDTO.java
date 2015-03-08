package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ImagemItemConfiguracaoRelacaoDTO implements IDto {

	private static final long serialVersionUID = 12312312345L;
	
	private Integer idImagemItemConfiguracaoRel;
	private Integer idImagemItemConfiguracao;
	private Integer idImagemItemConfiguracaoPai;
	
	public Integer getIdImagemItemConfiguracaoRel() {
		return idImagemItemConfiguracaoRel;
	}
	public void setIdImagemItemConfiguracaoRel(
			Integer idImagemItemConfiguracaoRel) {
		this.idImagemItemConfiguracaoRel = idImagemItemConfiguracaoRel;
	}
	public Integer getIdImagemItemConfiguracao() {
		return idImagemItemConfiguracao;
	}
	public void setIdImagemItemConfiguracao(Integer idImagemItemConfiguracao) {
		this.idImagemItemConfiguracao = idImagemItemConfiguracao;
	}
	public Integer getIdImagemItemConfiguracaoPai() {
		return idImagemItemConfiguracaoPai;
	}
	public void setIdImagemItemConfiguracaoPai(Integer idImagemItemConfiguracaoPai) {
		this.idImagemItemConfiguracaoPai = idImagemItemConfiguracaoPai;
	}
	

}
