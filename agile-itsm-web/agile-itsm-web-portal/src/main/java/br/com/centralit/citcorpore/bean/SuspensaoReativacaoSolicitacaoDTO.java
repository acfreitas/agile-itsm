package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class SuspensaoReativacaoSolicitacaoDTO implements IDto {
	private static final long serialVersionUID = 1L;

	private String idContrato;
	private String solicitante;
	private String idGrupo;
	private String justificativa;
	private Integer idJustificativa;
	private String tipoAcao;

	
	public Integer getIdJustificativa() {
		return idJustificativa;
	}

	public void setIdJustificativa(Integer idJustificativa) {
		this.idJustificativa = idJustificativa;
	}

	public String getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public String getTipoAcao() {
		return tipoAcao;
	}

	public void setTipoAcao(String tipoAcao) {
		this.tipoAcao = tipoAcao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
