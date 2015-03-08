package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class PrioridadeDTO implements IDto {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7848776827100833523L;

    private Integer idPrioridade;
    private Integer idEmpresa;
    private String nomePrioridade;
    private String situacao;
    private String grupoPrioridade;
    private Integer quantidade;

    public String getGrupoPrioridade() {
    	if (grupoPrioridade != null){
    		return grupoPrioridade.trim();
    	}
    	
    	return grupoPrioridade;
    }

    public void setGrupoPrioridade(String grupoPrioridade) {
	this.grupoPrioridade = grupoPrioridade;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    public String getNomePrioridade() {
	return nomePrioridade;
    }

    public void setNomePrioridade(String nomePrioridade) {
	this.nomePrioridade = nomePrioridade;
    }

    public Integer getIdPrioridade() {
	return idPrioridade;
    }

    public void setIdPrioridade(Integer idPrioridade) {
	this.idPrioridade = idPrioridade;
    }

    public Integer getIdEmpresa() {
	return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
	this.idEmpresa = idEmpresa;
    }

    public String getSituacao() {
	return situacao;
    }

    public void setSituacao(String situacao) {
	this.situacao = situacao;
    }

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
