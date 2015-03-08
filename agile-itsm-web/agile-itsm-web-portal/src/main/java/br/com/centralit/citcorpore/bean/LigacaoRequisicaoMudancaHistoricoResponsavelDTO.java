package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author murilo.pacheco
 * classe criada para fazer a ligação da tabela de responsaveis com os hitoricos.
 *
 */
public class LigacaoRequisicaoMudancaHistoricoResponsavelDTO implements IDto {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idLigacao_mud_hist_resp;
	private Integer idRequisicaoMudanca;
	private Integer idHistoricoMudanca;
	private Integer idRequisicaoMudancaResp;
	
	public Integer getIdLigacao_mud_hist_resp() {
		return idLigacao_mud_hist_resp;
	}
	public void setIdLigacao_mud_hist_resp(Integer idLigacao_mud_hist_resp) {
		this.idLigacao_mud_hist_resp = idLigacao_mud_hist_resp;
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
	public Integer getIdRequisicaoMudancaResp() {
		return idRequisicaoMudancaResp;
	}
	public void setIdRequisicaoMudancaResp(Integer idRequisicaoMudancaResp) {
		this.idRequisicaoMudancaResp = idRequisicaoMudancaResp;
	}
	
}
