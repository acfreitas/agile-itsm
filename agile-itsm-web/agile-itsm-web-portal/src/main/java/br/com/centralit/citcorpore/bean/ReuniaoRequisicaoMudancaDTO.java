package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/*
 * author Bruno Rodrigues Franco
 */
public class ReuniaoRequisicaoMudancaDTO extends AtividadePeriodicaDTO implements IDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idReuniaoRequisicaoMudanca;
	private Integer idUsuario;
	private String localReuniao;
	private String horaInicio;
	private String status;
	
	public Integer getIdReuniaoRequisicaoMudanca() {
		return idReuniaoRequisicaoMudanca;
	}
	public void setIdReuniaoRequisicaoMudanca(Integer idReuniaoRequisicaoMudanca) {
		this.idReuniaoRequisicaoMudanca = idReuniaoRequisicaoMudanca;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getLocalReuniao() {
		return localReuniao;
	}
	public void setLocalReuniao(String localReuniao) {
		this.localReuniao = localReuniao;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
