package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class LigacaoRequisicaoMudancaAprovacaoDTO implements IDto {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idligacao_mud_hist_aprov_mud;
	private Integer idRequisicaoMudanca;
	private Integer idHistoricoMudanca;
	private Integer idAprovacaoMudanca;
	
	
	public Integer getIdligacao_mud_hist_aprov_mud() {
		return idligacao_mud_hist_aprov_mud;
	}
	public void setIdligacao_mud_hist_aprov_mud(Integer idligacao_mud_hist_aprov_mud) {
		this.idligacao_mud_hist_aprov_mud = idligacao_mud_hist_aprov_mud;
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
	public Integer getIdAprovacaoMudanca() {
		return idAprovacaoMudanca;
	}
	public void setIdAprovacaoMudanca(Integer idAprovacaoMudanca) {
		this.idAprovacaoMudanca = idAprovacaoMudanca;
	}
	
}
