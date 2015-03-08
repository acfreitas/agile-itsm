package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class CriterioItemCotacaoDTO implements IDto {
	private Integer idCriterio;
	private Integer idItemCotacao;
	private Integer peso;
	
	private Integer sequencia;

	public Integer getIdCriterio(){
		return this.idCriterio;
	}
	public void setIdCriterio(Integer parm){
		this.idCriterio = parm;
	}

	public Integer getIdItemCotacao() {
        return idItemCotacao;
    }
    public void setIdItemCotacao(Integer idItemCotacao) {
        this.idItemCotacao = idItemCotacao;
    }
    public Integer getPeso(){
		return this.peso;
	}
	public void setPeso(Integer parm){
		this.peso = parm;
	}
    public Integer getSequencia() {
        return sequencia;
    }
    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

}
