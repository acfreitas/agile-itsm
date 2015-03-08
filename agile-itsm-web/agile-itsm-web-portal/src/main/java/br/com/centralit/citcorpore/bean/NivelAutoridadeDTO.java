package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.centralit.citcorpore.util.Enumerados.MotivoRejeicaoAlcada;
import br.com.citframework.dto.IDto;

public class NivelAutoridadeDTO implements IDto {
	private Integer idNivelAutoridade;
	private String nomeNivelAutoridade;
	private Integer hierarquia;
	private String situacao;
	
	private Collection<GrupoNivelAutoridadeDTO> colGrupos;

	private boolean alcadaRejeitada;
	private MotivoRejeicaoAlcada motivoRejeicao;


	public Integer getIdNivelAutoridade(){
		return this.idNivelAutoridade;
	}
	public void setIdNivelAutoridade(Integer parm){
		this.idNivelAutoridade = parm;
	}

	public String getNomeNivelAutoridade(){
		return this.nomeNivelAutoridade;
	}
	public void setNomeNivelAutoridade(String parm){
		this.nomeNivelAutoridade = parm;
	}

	public Integer getHierarquia(){
		return this.hierarquia;
	}
	public void setHierarquia(Integer parm){
		this.hierarquia = parm;
	}
	public Collection<GrupoNivelAutoridadeDTO> getColGrupos() {
		return colGrupos;
	}
	public void setColGrupos(Collection<GrupoNivelAutoridadeDTO> colGrupos) {
		this.colGrupos = colGrupos;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public boolean isAlcadaRejeitada() {
		return alcadaRejeitada;
	}
	public void setAlcadaRejeitada(boolean alcadaRejeitada) {
		this.alcadaRejeitada = alcadaRejeitada;
	}
	public MotivoRejeicaoAlcada getMotivoRejeicao() {
		return motivoRejeicao;
	}
	public void setMotivoRejeicao(MotivoRejeicaoAlcada motivoRejeicao) {
		this.motivoRejeicao = motivoRejeicao;
	}

}
