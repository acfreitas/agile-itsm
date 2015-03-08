package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class MeuCatalogoDTO implements IDto{
	/**
	 * 
	*/
	private static final long serialVersionUID = 638687400065001805L;
	private Integer idServico;
	private Integer idUsuario;
	
	public Integer getIdServico() {
		return idServico;
	}
	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

}
