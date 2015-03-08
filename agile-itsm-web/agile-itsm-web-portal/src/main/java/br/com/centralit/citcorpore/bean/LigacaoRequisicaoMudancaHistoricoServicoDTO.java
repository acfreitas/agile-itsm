package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class LigacaoRequisicaoMudancaHistoricoServicoDTO implements IDto {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idligacao_mud_hist_se;
	private Integer idRequisicaoMudanca;
	private Integer idHistoricoMudanca;
	private Integer idrequisicaomudancaservico;
	
	public Integer getIdligacao_mud_hist_se() {
		return idligacao_mud_hist_se;
	}
	public void setIdligacao_mud_hist_se(Integer idligacao_mud_hist_se) {
		this.idligacao_mud_hist_se = idligacao_mud_hist_se;
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
	public Integer getIdrequisicaomudancaservico() {
		return idrequisicaomudancaservico;
	}
	public void setIdrequisicaomudancaservico(Integer idrequisicaomudancaservico) {
		this.idrequisicaomudancaservico = idrequisicaomudancaservico;
	}

}
