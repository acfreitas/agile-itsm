package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilStrings;

public class CentroResultadoDTO implements IDto {

	private static final long serialVersionUID = 5314639262309812533L;
	
	private Integer idCentroResultado;	
	private String codigoCentroResultado;
	private String nomeCentroResultado;
	private Integer idCentroResultadoPai;
	private String permiteRequisicaoProduto;
	private String situacao;
	private String nomeCentroResultadoPai;
	private Integer nivel;
	private String imagem;
	
	private Collection<AlcadaCentroResultadoDTO> colAlcadas;
	private Collection<ResponsavelCentroResultadoDTO> colResponsaveis;
	
	public Integer getIdCentroResultado() {
		return this.idCentroResultado;
	}
	
	public void setIdCentroResultado(Integer parm) {
		this.idCentroResultado = parm;
	}

	public String getCodigoCentroResultado() {
		return this.codigoCentroResultado;
	}
	
	public void setCodigoCentroResultado(String parm) {
		this.codigoCentroResultado = parm;
	}

	public String getNomeCentroResultado() {
	    return nomeCentroResultado;
	}
	
    public String getNomeHierarquizado() {
    	
        if (nomeCentroResultado == null) 
        	return "";
        
        if (this.getNivel() == null) 
        	return UtilStrings.nullToVazio(codigoCentroResultado) + " " + nomeCentroResultado;
        
        String aux = "";
        
        for (int i = 0; i < this.getNivel().intValue(); i++) {
        	aux += ".....";
        }
        
        return aux + UtilStrings.nullToVazio(codigoCentroResultado) + " " + nomeCentroResultado;
    }	
    
	public void setNomeCentroResultado(String parm) {
		this.nomeCentroResultado = parm;
	}
	
	public Integer getIdCentroResultadoPai() {
		return this.idCentroResultadoPai;
	}
	
	public void setIdCentroResultadoPai(Integer parm) {
		this.idCentroResultadoPai = parm;
	}

	public String getSituacao() {
		return this.situacao;
	}
	
	public void setSituacao(String parm) {
		this.situacao = parm;
	}
	
    public String getNomeCentroResultadoPai() {
		return nomeCentroResultadoPai;
	}

	public void setNomeCentroResultadoPai(String nomeCentroResultadoPai) {
		this.nomeCentroResultadoPai = nomeCentroResultadoPai;
	}

	public String getPermiteRequisicaoProduto() {
    	return permiteRequisicaoProduto;
    }
    
    public void setPermiteRequisicaoProduto(String permiteRequisicaoProduto) {
    	this.permiteRequisicaoProduto = permiteRequisicaoProduto;
    }
    
    public Integer getNivel() {
    	return nivel;
    }
    
    public void setNivel(Integer nivel) {
    	this.nivel = nivel;
    }
    
    public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getNomeHierarquiaHTML() {
    	
    	if (nomeCentroResultado == null) 
    		return "";
    	
        if (this.getNivel() == null) 
    		return nomeCentroResultado;
        
        String aux = "";
        
        for(int i = 0; i < this.getNivel().intValue(); i++) {
            aux += ".....";
        }
        
        return aux + nomeCentroResultado;
    }

    public Collection<AlcadaCentroResultadoDTO> getColAlcadas() {
        return colAlcadas;
    }

    public void setColAlcadas(Collection<AlcadaCentroResultadoDTO> colAlcadas) {
        this.colAlcadas = colAlcadas;
    }

	public Collection<ResponsavelCentroResultadoDTO> getColResponsaveis() {
		return colResponsaveis;
	}

	public void setColResponsaveis(
			Collection<ResponsavelCentroResultadoDTO> colResponsaveis) {
		this.colResponsaveis = colResponsaveis;
	}
}