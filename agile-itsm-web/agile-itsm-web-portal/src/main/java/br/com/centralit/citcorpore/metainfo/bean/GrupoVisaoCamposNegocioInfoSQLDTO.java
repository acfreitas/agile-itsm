package br.com.centralit.citcorpore.metainfo.bean;

import br.com.citframework.dto.IDto;

public class GrupoVisaoCamposNegocioInfoSQLDTO implements IDto {
	private Integer idGrupoVisaoCamposNegocioInfoSQL;
	private Integer idGrupoVisao;
	private Integer idCamposObjetoNegocio;
	private String campo;
	private String tipoLigacao;
	private String filtro;
	private String descricao;

	public Integer getIdGrupoVisaoCamposNegocioInfoSQL(){
		return this.idGrupoVisaoCamposNegocioInfoSQL;
	}
	public void setIdGrupoVisaoCamposNegocioInfoSQL(Integer parm){
		this.idGrupoVisaoCamposNegocioInfoSQL = parm;
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

	public String getCampo(){
		return this.campo;
	}
	public void setCampo(String parm){
		this.campo = parm;
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
