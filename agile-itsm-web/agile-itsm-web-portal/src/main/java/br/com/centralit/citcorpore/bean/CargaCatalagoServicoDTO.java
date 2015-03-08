package br.com.centralit.citcorpore.bean;

import java.util.List;

import br.com.citframework.dto.IDto;


public class CargaCatalagoServicoDTO implements IDto {

    private static final long serialVersionUID = 2984986456849222230L;
    private Integer idCatalagoServico;
    private List<CategoriaServicoDTO> categoriaServicoDTO;
    private List<TipoDemandaDTO> tipoDemandaDTO;
    private List<TipoEventoServicoDTO> tipoEventoServicoDTO;
	
    /**
	 * @return the idCatalagoServico
	 */
	public Integer getIdCatalagoServico() {
		return idCatalagoServico;
	}
	/**
	 * @param idCatalagoServico the idCatalagoServico to set
	 */
	public void setIdCatalagoServico(Integer idCatalagoServico) {
		this.idCatalagoServico = idCatalagoServico; }
	/**
	 * @return the categoriaServicoDTO
	 */
	public List<CategoriaServicoDTO> getCategoriaServicoDTO() {
		return categoriaServicoDTO;
	}
	/**
	 * @param categoriaServicoDTO the categoriaServicoDTO to set
	 */
	public void setCategoriaServicoDTO(List<CategoriaServicoDTO> categoriaServicoDTO) {
		this.categoriaServicoDTO = categoriaServicoDTO;
	}
	/**
	 * @return the tipoDemandaDTO
	 */
	public List<TipoDemandaDTO> getTipoDemandaDTO() {
		return tipoDemandaDTO;
	}
	/**
	 * @param tipoDemandaDTO the tipoDemandaDTO to set
	 */
	public void setTipoDemandaDTO(List<TipoDemandaDTO> tipoDemandaDTO) {
		this.tipoDemandaDTO = tipoDemandaDTO;
	}
	/**
	 * @return the tipoEventoServicoDTO
	 */
	public List<TipoEventoServicoDTO> getTipoEventoServicoDTO() {
		return tipoEventoServicoDTO;
	}
	/**
	 * @param tipoEventoServicoDTO the tipoEventoServicoDTO to set
	 */
	public void setTipoEventoServicoDTO(List<TipoEventoServicoDTO> tipoEventoServicoDTO) {
		this.tipoEventoServicoDTO = tipoEventoServicoDTO;
	}

}
