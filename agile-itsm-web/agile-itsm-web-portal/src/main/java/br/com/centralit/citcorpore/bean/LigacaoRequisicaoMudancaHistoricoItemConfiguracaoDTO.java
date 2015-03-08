package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author murilo.pacheco
 * classe criada para fazer a ligação da tabela de responsaveis com os hitoricos.
 *
 */
public class LigacaoRequisicaoMudancaHistoricoItemConfiguracaoDTO implements IDto {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idLigacao_mud_hist_ic;
	private Integer idRequisicaoMudanca;
	private Integer idHistoricoMudanca;
	private Integer idrequisicaomudancaitemconfiguracao;
	
	public Integer getIdLigacao_mud_hist_ic() {
		return idLigacao_mud_hist_ic;
	}
	public void setIdLigacao_mud_hist_ic(Integer idLigacao_mud_hist_ic) {
		this.idLigacao_mud_hist_ic = idLigacao_mud_hist_ic;
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
	public Integer getIdrequisicaomudancaitemconfiguracao() {
		return idrequisicaomudancaitemconfiguracao;
	}
	public void setIdrequisicaomudancaitemconfiguracao(
			Integer idrequisicaomudancaitemconfiguracao) {
		this.idrequisicaomudancaitemconfiguracao = idrequisicaomudancaitemconfiguracao;
	}
	
}
