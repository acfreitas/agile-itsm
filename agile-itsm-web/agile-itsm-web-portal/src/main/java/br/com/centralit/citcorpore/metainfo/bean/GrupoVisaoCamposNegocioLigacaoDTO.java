package br.com.centralit.citcorpore.metainfo.bean;

import br.com.citframework.dto.IDto;

public class GrupoVisaoCamposNegocioLigacaoDTO implements IDto {
	public static String PRESENTATION = "A";
	public static String VALUE = "V";
	public static String FILTER = "F";
	public static String ORDER = "O";
	
	private Integer idGrupoVisaoCamposNegocioLigacao;
	private Integer idGrupoVisao;
	private Integer idCamposObjetoNegocio;
	private Integer idCamposObjetoNegocioLigacao;
	private String tipoLigacao;
	private String filtro;
	private String descricao;

	public Integer getIdGrupoVisaoCamposNegocioLigacao(){
		return this.idGrupoVisaoCamposNegocioLigacao;
	}
	public void setIdGrupoVisaoCamposNegocioLigacao(Integer parm){
		this.idGrupoVisaoCamposNegocioLigacao = parm;
	}

	public Integer getIdGrupoVisao(){
		return this.idGrupoVisao;
	}
	public void setIdGrupoVisao(Integer parm){
		this.idGrupoVisao = parm;
	}

	public Integer getIdCamposObjetoNegocio(){
		return this.idCamposObjetoNegocio;
	}
	public void setIdCamposObjetoNegocio(Integer parm){
		this.idCamposObjetoNegocio = parm;
	}

	public Integer getIdCamposObjetoNegocioLigacao(){
		return this.idCamposObjetoNegocioLigacao;
	}
	public void setIdCamposObjetoNegocioLigacao(Integer parm){
		this.idCamposObjetoNegocioLigacao = parm;
	}

	public String getTipoLigacao(){
		return this.tipoLigacao;
	}
	public void setTipoLigacao(String parm){
		this.tipoLigacao = parm;
	}

	public String getFiltro(){
		return this.filtro;
	}
	public void setFiltro(String parm){
		this.filtro = parm;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
