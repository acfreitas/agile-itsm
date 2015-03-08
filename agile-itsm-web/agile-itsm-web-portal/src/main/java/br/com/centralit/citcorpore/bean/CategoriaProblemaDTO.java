package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class CategoriaProblemaDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idCategoriaProblemaPai;
	private Integer idCategoriaProblema;
	private String nomeCategoria;
	private int nivel;
	private Integer idTemplate;
	
	
	/**
	 * @author geber.costa
	 * criação de atributos idTipoFluxo, idGrupoExecutor, dataInicio e dataFim
	 */
	
	private Integer idTipoFluxo;
	private Integer idGrupoExecutor;
	private Date dataInicio;
	private Date dataFim;
	
	private String impacto;
	
	private String urgencia;
	
	public String getNomeNivel() {
		if (this.getNomeCategoria() == null) {
			return this.nomeCategoria;
		}
		String str = "";
		for (int i = 0; i < this.getNivel(); i++) {
			str += "....";
		}
		return str + this.nomeCategoria;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public Integer getIdCategoriaProblema() {
		return this.idCategoriaProblema;
	}

	public void setIdCategoriaProblema(Integer parm) {
		this.idCategoriaProblema = parm;
	}

	public String getNomeCategoria() {
		return this.nomeCategoria;
	}

	public void setNomeCategoria(String parm) {
		this.nomeCategoria = parm;
	}

	public Integer getIdCategoriaProblemaPai() {
		return idCategoriaProblemaPai;
	}

	public void setIdCategoriaProblemaPai(Integer idCategoriaProblemaPai) {
		this.idCategoriaProblemaPai = idCategoriaProblemaPai;
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

	public Integer getIdTipoFluxo() {
		return idTipoFluxo;
	}

	public void setIdTipoFluxo(Integer idTipoFluxo) {
		this.idTipoFluxo = idTipoFluxo;
	}

	public Integer getIdGrupoExecutor() {
		return idGrupoExecutor;
	}

	public void setIdGrupoExecutor(Integer idGrupoExecutor) {
		this.idGrupoExecutor = idGrupoExecutor;
	}

	public Integer getIdTemplate() {
		return idTemplate;
	}

	public void setIdTemplate(Integer idTemplate) {
		this.idTemplate = idTemplate;
	}

	public String getImpacto() {
		return impacto;
	}

	public void setImpacto(String impacto) {
		this.impacto = impacto;
	}

	public String getUrgencia() {
		return urgencia;
	}

	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}
	
}
