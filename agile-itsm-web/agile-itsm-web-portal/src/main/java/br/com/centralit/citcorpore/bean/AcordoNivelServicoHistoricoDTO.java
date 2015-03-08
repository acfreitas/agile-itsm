package br.com.centralit.citcorpore.bean;


public class AcordoNivelServicoHistoricoDTO extends AcordoNivelServicoDTO {
	private Integer idAcordoNivelServico_Hist;
	private String conteudodados;

	public Integer getIdAcordoNivelServico_Hist() {
		return idAcordoNivelServico_Hist;
	}

	public void setIdAcordoNivelServico_Hist(Integer idAcordoNivelServico_Hist) {
		this.idAcordoNivelServico_Hist = idAcordoNivelServico_Hist;
	}

	public String getConteudodados() {
		return conteudodados;
	}

	public void setConteudodados(String conteudodados) {
		this.conteudodados = conteudodados;
	}
}
