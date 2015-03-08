package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class AtitudeCandidatoDTO implements IDto {
	private Integer idEntrevista;
	private Integer idAtitudeOrganizacional;
	private String avaliacao;
	
	private String descricao;

	public Integer getIdEntrevista(){
		return this.idEntrevista;
	}
	public void setIdEntrevista(Integer parm){
		this.idEntrevista = parm;
	}

	public Integer getIdAtitudeOrganizacional(){
		return this.idAtitudeOrganizacional;
	}
	public void setIdAtitudeOrganizacional(Integer parm){
		this.idAtitudeOrganizacional = parm;
	}

	public String getAvaliacao(){
		return this.avaliacao;
	}
	public void setAvaliacao(String parm){
		this.avaliacao = parm;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
