package br.com.centralit.citcorpore.bean;

import java.util.ArrayList;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class AlcadaDTO implements IDto {
	private Integer idAlcada;
	private String nomeAlcada;
	private String tipoAlcada;
	private String situacao;
	private ArrayList<LimiteAlcadaDTO> listaDeLimites;
	private String listLimites;
	
    private Collection<LimiteAlcadaDTO> colLimites;
	
	private Collection<EmpregadoDTO> colResponsaveis;

	public Integer getIdAlcada(){
		return this.idAlcada;
	}
	public void setIdAlcada(Integer parm){
		this.idAlcada = parm;
	}

	public String getNomeAlcada(){
		return this.nomeAlcada;
	}
	public void setNomeAlcada(String parm){
		this.nomeAlcada = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}
    public String getTipoAlcada() {
        return tipoAlcada;
    }
    public void setTipoAlcada(String tipoAlcada) {
        this.tipoAlcada = tipoAlcada;
    }
    public Collection<EmpregadoDTO> getColResponsaveis() {
        return colResponsaveis;
    }
    public Collection<LimiteAlcadaDTO> getColLimites() {
        return colLimites;
    }
    public void setColLimites(Collection<LimiteAlcadaDTO> colLimites) {
        this.colLimites = colLimites;
    }
    public void setColResponsaveis(Collection<EmpregadoDTO> colResponsaveis) {
        this.colResponsaveis = colResponsaveis;
    }
	public ArrayList<LimiteAlcadaDTO> getListaDeLimites() {
		return listaDeLimites;
	}
	public void setListaDeLimites(ArrayList<LimiteAlcadaDTO> listaDeLimites) {
		this.listaDeLimites = listaDeLimites;
	}
	public String getListLimites() {
		return listLimites;
	}
	public void setListLimites(String listLimites) {
		this.listLimites = listLimites;
	}
	public boolean mesmosResponsaveis(Collection<EmpregadoDTO> colComparacao) {
		if (this.getColResponsaveis() != null && colComparacao == null)
			return false;
		if (this.getColResponsaveis() == null && colComparacao != null)
			return false;
		if (this.getColResponsaveis().size() != colComparacao.size())
			return false;
		
		int i = 0;
		for (EmpregadoDTO empregadoCompDto : colComparacao) {
			for (EmpregadoDTO empregadoDto : this.getColResponsaveis()) {
				if (empregadoCompDto.getIdEmpregado().intValue() == empregadoDto.getIdEmpregado().intValue()) 
					i++;
			}
		}
		if (i != colComparacao.size())
			return false;
		
		i = 0;
		for (EmpregadoDTO empregadoDto : this.getColResponsaveis()) {
			for (EmpregadoDTO empregadoCompDto : colComparacao) {
				if (empregadoCompDto.getIdEmpregado().intValue() != empregadoDto.getIdEmpregado().intValue()) 
					i++;
			}
		}
		return i == this.getColResponsaveis().size();
	}
}
