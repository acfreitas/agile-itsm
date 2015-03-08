package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class BIConsultaColunasDTO implements IDto {
	private Integer idConsultaColuna;
	private Integer idConsulta;
	private String nomeColuna;
	private String tipoFiltro;
	private Integer ordem;

	public Integer getIdConsultaColuna(){
		return this.idConsultaColuna;
	}
	public void setIdConsultaColuna(Integer parm){
		this.idConsultaColuna = parm;
	}

	public Integer getIdConsulta(){
		return this.idConsulta;
	}
	public void setIdConsulta(Integer parm){
		this.idConsulta = parm;
	}

	public String getNomeColuna(){
		return this.nomeColuna;
	}
	public void setNomeColuna(String parm){
		this.nomeColuna = parm;
	}

	public String getTipoFiltro(){
		return this.tipoFiltro;
	}
	public void setTipoFiltro(String parm){
		this.tipoFiltro = parm;
	}

	public Integer getOrdem(){
		return this.ordem;
	}
	public void setOrdem(Integer parm){
		this.ordem = parm;
	}

}
