package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ServicoCorporeBIDTO implements IDto {

	private static final long serialVersionUID = -3079183232889920998L;
	
	private Integer idServicoCorpore;
	private String nomeServico;
	
	public Integer getIdServicoCorpore() {
		return idServicoCorpore;
	}
	public void setIdServicoCorpore(Integer idServicoCorpore) {
		this.idServicoCorpore = idServicoCorpore;
	}
	public String getNomeServico() {
		return nomeServico;
	}
	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}
	
}
