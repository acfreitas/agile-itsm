/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author breno.guimaraes
 * 
 */
public class IncidentesRelacionadosDTO implements IDto {

    private static final long serialVersionUID = 808193377991557947L;
    
    private Integer idIncidentesRelacionados;
    private Integer idIncidente;
    private Integer idIncidenteRelacionado;
    
    private Integer idSolicitacaoServico;
    private Integer idSolicitacaoIncRel;
    
    public Integer getIdSolicitacaoServico() {
        return idSolicitacaoServico;
    }

    public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
        this.idSolicitacaoServico = idSolicitacaoServico;
    }

    private Integer[] filhasAdicionar;

    public Integer[] getFilhasAdicionar() {
	return filhasAdicionar;
    }

    public void setFilhasAdicionar(Integer[] filhasAdicionar) {
	this.filhasAdicionar = filhasAdicionar;
    }

    public Integer getIdSolicitacaoIncRel() {
        return idSolicitacaoIncRel;
    }

    public void setIdSolicitacaoIncRel(Integer idSolicitacaoIncRel) {
        this.idSolicitacaoIncRel = idSolicitacaoIncRel;
    }

	public Integer getIdIncidentesRelacionados() {
		return idIncidentesRelacionados;
	}

	public void setIdIncidentesRelacionados(Integer idIncidentesRelacionados) {
		this.idIncidentesRelacionados = idIncidentesRelacionados;
	}

	public Integer getIdIncidente() {
		return idIncidente;
	}

	public void setIdIncidente(Integer idIncidente) {
		this.idIncidente = idIncidente;
	}

	public Integer getIdIncidenteRelacionado() {
		return idIncidenteRelacionado;
	}

	public void setIdIncidenteRelacionado(Integer idIncidenteRelacionado) {
		this.idIncidenteRelacionado = idIncidenteRelacionado;
	}

}
