package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class LigacaoRequisicaoMudancaHistoricoGrupoDTO implements IDto {

	private static final long serialVersionUID = 1L;
	private Integer idligacao_mud_his_gru;
	private Integer idRequisicaoMudanca;
	private Integer idHistoricoMudanca;
	private Integer idGrupoRequisicaoMudanca;
	
	public Integer getIdligacao_mud_his_gru() {
		return idligacao_mud_his_gru;
	}
	public void setIdligacao_mud_his_gru(Integer idligacao_mud_his_gru) {
		this.idligacao_mud_his_gru = idligacao_mud_his_gru;
	}
	public Integer getIdRequisicaoMudanca() {
		return idRequisicaoMudanca;
	}
	public void setIdRequisicaoMudanca(Integer idRequisicaoMudanca) {
		this.idRequisicaoMudanca = idRequisicaoMudanca;
	}
	public Integer getIdHistoricoMudanca() {
		return idHistoricoMudanca;
	}
	public void setIdHistoricoMudanca(Integer idHistoricoMudanca) {
		this.idHistoricoMudanca = idHistoricoMudanca;
	}
	public Integer getIdGrupoRequisicaoMudanca() {
		return idGrupoRequisicaoMudanca;
	}
	public void setIdGrupoRequisicaoMudanca(Integer idGrupoRequisicaoMudanca) {
		this.idGrupoRequisicaoMudanca = idGrupoRequisicaoMudanca;
	}
	
}
