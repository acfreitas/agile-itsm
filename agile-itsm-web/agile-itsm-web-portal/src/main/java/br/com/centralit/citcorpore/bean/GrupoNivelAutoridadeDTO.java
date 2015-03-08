package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class GrupoNivelAutoridadeDTO implements IDto {
	private Integer idNivelAutoridade;
	private Integer idGrupo;
	
	private Integer sequencia;

	public Integer getIdNivelAutoridade(){
		return this.idNivelAutoridade;
	}
	public void setIdNivelAutoridade(Integer parm){
		this.idNivelAutoridade = parm;
	}

	public Integer getIdGrupo(){
		return this.idGrupo;
	}
	public void setIdGrupo(Integer parm){
		this.idGrupo = parm;
	}
	public Integer getSequencia() {
		return sequencia;
	}
	public void setSequencia(Integer sequencia) {
		this.sequencia = sequencia;
	}

}
