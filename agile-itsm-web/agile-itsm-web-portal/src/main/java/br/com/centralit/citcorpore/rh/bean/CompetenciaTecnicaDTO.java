package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class CompetenciaTecnicaDTO implements IDto{

	private static final long serialVersionUID = 1L;
	
	private Integer idCompetencia;
	private String descricao;
	private String situacao;
	
	//variaveis auxiliares, não salvas no banco
	private String idNivelCompetenciaAcesso;
	private String idNivelCompetenciaFuncao;
	private String descricaoNivelCompetenciaAcesso;
	private String descricaoNivelCompetenciaFuncao;
	
	public Integer getIdCompetencia() {
		return idCompetencia;
	}
	public void setIdCompetencia(Integer idCompetencia) {
		this.idCompetencia = idCompetencia;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getIdNivelCompetenciaAcesso() {
		return idNivelCompetenciaAcesso;
	}
	public void setIdNivelCompetenciaAcesso(String idNivelCompetenciaAcesso) {
		this.idNivelCompetenciaAcesso = idNivelCompetenciaAcesso;
	}
	public String getIdNivelCompetenciaFuncao() {
		return idNivelCompetenciaFuncao;
	}
	public void setIdNivelCompetenciaFuncao(String idNivelCompetenciaFuncao) {
		this.idNivelCompetenciaFuncao = idNivelCompetenciaFuncao;
	}
	public String getDescricaoNivelCompetenciaAcesso() {
		return descricaoNivelCompetenciaAcesso;
	}
	public void setDescricaoNivelCompetenciaAcesso(String descricaoNivelCompetenciaAcesso) {
		this.descricaoNivelCompetenciaAcesso = descricaoNivelCompetenciaAcesso;
	}
	public String getDescricaoNivelCompetenciaFuncao() {
		return descricaoNivelCompetenciaFuncao;
	}
	public void setDescricaoNivelCompetenciaFuncao(String descricaoNivelCompetenciaFuncao) {
		this.descricaoNivelCompetenciaFuncao = descricaoNivelCompetenciaFuncao;
	}

}
