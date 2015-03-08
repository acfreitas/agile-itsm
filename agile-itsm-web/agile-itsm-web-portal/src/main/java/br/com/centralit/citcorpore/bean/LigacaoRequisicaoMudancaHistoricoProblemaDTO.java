package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class LigacaoRequisicaoMudancaHistoricoProblemaDTO implements IDto {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idligacao_mud_hist_pr;
	private Integer idRequisicaoMudanca;
	private Integer idHistoricoMudanca;
	private Integer idProblemaMudanca;
	
	public Integer getIdligacao_mud_hist_pr() {
		return idligacao_mud_hist_pr;
	}
	public void setIdligacao_mud_hist_pr(Integer idligacao_mud_hist_pr) {
		this.idligacao_mud_hist_pr = idligacao_mud_hist_pr;
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
	public Integer getIdProblemaMudanca() {
		return idProblemaMudanca;
	}
	public void setIdProblemaMudanca(Integer idProblemaMudanca) {
		this.idProblemaMudanca = idProblemaMudanca;
	}
	

	
	
}
