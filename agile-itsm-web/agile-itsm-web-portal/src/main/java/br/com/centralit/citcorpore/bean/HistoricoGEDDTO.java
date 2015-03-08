package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author danillo.lisboa
 *
 */
public class HistoricoGEDDTO implements IDto {

	private Integer idLigacaoHistoricoGed;
	private Integer idControleGed;
	private Integer idRequisicaoMudanca;
	private Integer idHistoricoMudanca;
	private Integer idTabela;
	private Date dataFim;
	
	
	public Integer getIdLigacaoHistoricoGed() {
		return idLigacaoHistoricoGed;
	}
	public void setIdLigacaoHistoricoGed(Integer idLigacaoHistoricoGed) {
		this.idLigacaoHistoricoGed = idLigacaoHistoricoGed;
	}
	public Integer getIdControleGed() {
		return idControleGed;
	}
	public void setIdControleGed(Integer idControleGed) {
		this.idControleGed = idControleGed;
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
	public Integer getIdTabela() {
		return idTabela;
	}
	public void setIdTabela(Integer idTabela) {
		this.idTabela = idTabela;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	
	
}
