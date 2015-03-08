package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author murilo.pacheco
 * classe criada para fazer a ligação da tabela de responsaveis com os hitoricos.
 *
 */
public class LigacaoRequisicaoLiberacaoHistoricoResponsavelDTO implements IDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idLigacao_lib_hist_resp;
	private Integer idRequisicaoLiberacao;
	private Integer idHistoricoLiberacao;
	private Integer idRequisicaoLiberacaoResp;
	
	public Integer getIdLigacao_lib_hist_resp() {
		return idLigacao_lib_hist_resp;
	}
	public void setIdLigacao_lib_hist_resp(Integer idLigacao_lib_hist_resp) {
		this.idLigacao_lib_hist_resp = idLigacao_lib_hist_resp;
	}
	public Integer getIdRequisicaoLiberacao() {
		return idRequisicaoLiberacao;
	}
	public void setIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) {
		this.idRequisicaoLiberacao = idRequisicaoLiberacao;
	}
	public Integer getIdHistoricoLiberacao() {
		return idHistoricoLiberacao;
	}
	public void setIdHistoricoLiberacao(Integer idHistoricoLiberacao) {
		this.idHistoricoLiberacao = idHistoricoLiberacao;
	}
	public Integer getIdRequisicaoLiberacaoResp() {
		return idRequisicaoLiberacaoResp;
	}
	public void setIdRequisicaoLiberacaoResp(Integer idRequisicaoLiberacaoResp) {
		this.idRequisicaoLiberacaoResp = idRequisicaoLiberacaoResp;
	}
	
	
	
}
