package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * 
 * @author pedro
 *
 */
public class ServContratoCatalogoServDTO implements IDto {
	
	private static long serialVersionUID = 1582364224581163482L;
	
	private Integer idCatalogoServico;
	private Integer idServicoContrato;
	
	private String nomeServico;
	
	public Integer getIdCatalogoServico() {
		return idCatalogoServico;
	}
	public void setIdCatalogoServico(Integer idCatalogoServico) {
		this.idCatalogoServico = idCatalogoServico;
	}

	public Integer getIdServicoContrato() {
		return idServicoContrato;
	}
	public void setIdServicoContrato(Integer idServicoContrato) {
		this.idServicoContrato = idServicoContrato;
	}
	public String getNomeServico() {
		return nomeServico;
	}
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}


}
