package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author murilo.pacheco
 * classe criada para fazer a ligação da tabela de midia definitiva com os hitoricos.
 *
 */
public class LigacaoRequisicaoLiberacaoHistoricoMidiaDTO implements IDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idLigacao_lib_hist_midia;
	private Integer idRequisicaoLiberacao;
	private Integer idHistoricoLiberacao;
	private Integer idRequisicaoLiberacaoMidia;
	private Integer idMidiaSoftware;
	
	public Integer getIdLigacao_lib_hist_midia() {
		return idLigacao_lib_hist_midia;
	}
	public void setIdLigacao_lib_hist_midia(Integer idLigacao_lib_hist_midia) {
		this.idLigacao_lib_hist_midia = idLigacao_lib_hist_midia;
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
	public Integer getIdRequisicaoLiberacaoMidia() {
		return idRequisicaoLiberacaoMidia;
	}
	public void setIdRequisicaoLiberacaoMidia(Integer idRequisicaoLiberacaoMidia) {
		this.idRequisicaoLiberacaoMidia = idRequisicaoLiberacaoMidia;
	}
	public Integer getIdMidiaSoftware() {
		return idMidiaSoftware;
	}
	public void setIdMidiaSoftware(Integer idMidiaSoftware) {
		this.idMidiaSoftware = idMidiaSoftware;
	}
	
	
}
