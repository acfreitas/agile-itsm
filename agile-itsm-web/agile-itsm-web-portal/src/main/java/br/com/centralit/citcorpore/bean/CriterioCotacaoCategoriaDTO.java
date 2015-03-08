package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class CriterioCotacaoCategoriaDTO implements IDto {
	private Integer idCategoria;
	private Integer idCriterio;
	private Integer pesoCotacao;

	private Integer sequencia;
	
	public Integer getIdCategoria(){
		return this.idCategoria;
	}
	public void setIdCategoria(Integer parm){
		this.idCategoria = parm;
	}

	public Integer getIdCriterio(){
		return this.idCriterio;
	}
	public void setIdCriterio(Integer parm){
		this.idCriterio = parm;
	}

	public Integer getPesoCotacao(){
		return this.pesoCotacao;
	}
	public void setPesoCotacao(Integer parm){
		this.pesoCotacao = parm;
	}
    public Integer getSequencia() {
        return sequencia;
    }
    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

}
