
package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class ManualCompetenciaTecnicaDTO implements IDto{

	private static final long serialVersionUID = 1L;
	
	private Integer idManualCompetenciaTecnica;
	private Integer idManualFuncao;
	private Integer idCompetenciaTecnica;
	private Integer idNivelCompetenciaAcesso;
	private Integer idNivelCompetenciaFuncao;
	private String descricao;
	private String situacao;
	private String nomeCompetenciaTecnica;
	private String nomeCompetenciaAcesso;
	private String nomeCompetenciaFuncao;
	
	
	public Integer getIdManualCompetenciaTecnica() {
		return idManualCompetenciaTecnica;
	}
	public void setIdManualCompetenciaTecnica(Integer idManualCompetenciaTecnica) {
		this.idManualCompetenciaTecnica = idManualCompetenciaTecnica;
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
	public Integer getIdManualFuncao() {
		return idManualFuncao;
	}
	public void setIdManualFuncao(Integer idManualFuncao) {
		this.idManualFuncao = idManualFuncao;
	}
	public Integer getIdNivelCompetenciaAcesso() {
		return idNivelCompetenciaAcesso;
	}
	public void setIdNivelCompetenciaAcesso(Integer idNivelCompetenciaAcesso) {
		this.idNivelCompetenciaAcesso = idNivelCompetenciaAcesso;
	}
	public Integer getIdNivelCompetenciaFuncao() {
		return idNivelCompetenciaFuncao;
	}
	public void setIdNivelCompetenciaFuncao(Integer idNivelCompetenciaFuncao) {
		this.idNivelCompetenciaFuncao = idNivelCompetenciaFuncao;
	}
	public String getNomeCompetenciaTecnica() {
		return nomeCompetenciaTecnica;
	}
	public void setNomeCompetenciaTecnica(String nomeCompetenciaTecnica) {
		this.nomeCompetenciaTecnica = nomeCompetenciaTecnica;
	}
	public String getNomeCompetenciaAcesso() {
		return nomeCompetenciaAcesso;
	}
	public void setNomeCompetenciaAcesso(String nomeCompetenciaAcesso) {
		this.nomeCompetenciaAcesso = nomeCompetenciaAcesso;
	}
	public String getNomeCompetenciaFuncao() {
		return nomeCompetenciaFuncao;
	}
	public void setNomeCompetenciaFuncao(String nomeCompetenciaFuncao) {
		this.nomeCompetenciaFuncao = nomeCompetenciaFuncao;
	}
	public Integer getIdCompetenciaTecnica() {
		return idCompetenciaTecnica;
	}
	public void setIdCompetenciaTecnica(Integer idCompetenciaTecnica) {
		this.idCompetenciaTecnica = idCompetenciaTecnica;
	}	
}

