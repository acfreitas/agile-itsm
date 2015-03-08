package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class LigacaoRequisicaoMudancaHistoricoRiscosDTO implements IDto {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idligacao_mud_hist_riscos;
	private Integer idRequisicaoMudanca;
	private Integer idHistoricoMudanca;
	private Integer idRequisicaoMudancaRisco;
	
	
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
	public Integer getIdligacao_mud_hist_riscos() {
		return idligacao_mud_hist_riscos;
	}
	public void setIdligacao_mud_hist_riscos(Integer idligacao_mud_hist_riscos) {
		this.idligacao_mud_hist_riscos = idligacao_mud_hist_riscos;
	}
	public Integer getIdRequisicaoMudancaRisco() {
		return idRequisicaoMudancaRisco;
	}
	public void setIdRequisicaoMudancaRisco(Integer idRequisicaoMudancaRisco) {
		this.idRequisicaoMudancaRisco = idRequisicaoMudancaRisco;
	}
	
	
}
