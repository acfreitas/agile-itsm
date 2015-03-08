package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class MarcaDTO implements IDto {
	private Integer idMarca;
	private Integer idFabricante;
	private String nomeMarca;
	private String situacao;
	private String nomeFabricante;

	public Integer getIdMarca(){
		return this.idMarca;
	}
	public void setIdMarca(Integer parm){
		this.idMarca = parm;
	}

	public Integer getIdFabricante(){
		return this.idFabricante;
	}
	public void setIdFabricante(Integer parm){
		this.idFabricante = parm;
	}

	public String getNomeMarca(){
		return this.nomeMarca;
	}
	public void setNomeMarca(String parm){
		this.nomeMarca = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}
	public String getNomeFabricante() {
		return nomeFabricante;
	}
	public void setNomeFabricante(String nomeFabricante) {
		this.nomeFabricante = nomeFabricante;
	}

}
