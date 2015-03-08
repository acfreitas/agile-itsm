package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class RequisicaoQuestionarioDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1383478887132161536L;
	private Integer idRequisicaoQuestionario;
	private Integer idQuestionario;
	private Integer idRequisicao;
	private Date dataQuestionario;
	private Integer idResponsavel;
	private Integer idTarefa;
	private String aba;
	private String divAtualizarCertificado;
	private String profissional;
	private String nomeQuestionario;
	private String subForm;
	private Integer idTipoAba;
	private Integer idTipoRequisicao;
	private String situacao;
	private Integer idItem;
	private Collection colValores;
	private Collection colAnexos;
	private Timestamp dataHoraGrav;	
	private Collection colCertificados;	
	private String campoSelecaoHistorico;
	private Integer idQuestaoVisHistorico;
	private Integer idContratoVisHistorico;	
	private Integer qtde;	
	private String conteudoImpresso;	
	private String ordemHistorico;	
	private String continuarEdt;
	private String confirmacao;
	private String valorConfirmacao;

	public String getKey(){
		if (getIdRequisicaoQuestionario() == null){
			return "NULL";
		}
		return getIdRequisicaoQuestionario().toString();
	}
	
	public Date getDataQuestionario() {
		return dataQuestionario;
	}
	public void setDataQuestionario(Date dataQuestionario) {
		this.dataQuestionario = dataQuestionario;
	}

	public Integer getIdResponsavel() {
        return idResponsavel;
    }
    public void setIdResponsavel(Integer idResponsavel) {
        this.idResponsavel = idResponsavel;
    }
    public Integer getIdTarefa() {
        return idTarefa;
    }
    public void setIdTarefa(Integer idTarefa) {
        this.idTarefa = idTarefa;
    }
    public Integer getIdQuestionario() {
		return idQuestionario;
	}
	public void setIdQuestionario(Integer idQuestionario) {
		this.idQuestionario = idQuestionario;
	}
	public String getAba() {
		return aba;
	}
	public void setAba(String aba) {
		this.aba = aba;
	}
	public Integer getIdItem() {
		return idItem;
	}
	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Collection getColValores() {
		return colValores;
	}
	public void setColValores(Collection colValores) {
		this.colValores = colValores;
	}
	public Collection getColAnexos() {
		return colAnexos;
	}
	public void setColAnexos(Collection colAnexos) {
		this.colAnexos = colAnexos;
	}
	public String getDivAtualizarCertificado() {
		return divAtualizarCertificado;
	}
	public void setDivAtualizarCertificado(String divAtualizarCertificado) {
		this.divAtualizarCertificado = divAtualizarCertificado;
	}
    public String getProfissional() {
        return profissional;
    }
    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }
    public String getNomeQuestionario() {
        return nomeQuestionario;
    }
    public void setNomeQuestionario(String nomeQuestionario) {
        this.nomeQuestionario = nomeQuestionario;
    }
	public Collection getColCertificados() {
		return colCertificados;
	}
	public void setColCertificados(Collection colCertificados) {
		this.colCertificados = colCertificados;
	}
	public String getSubForm() {
		return subForm;
	}
	public void setSubForm(String subForm) {
		this.subForm = subForm;
	}
	public Integer getQtde() {
		return qtde;
	}
	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}
	public String getCampoSelecaoHistorico() {
		return campoSelecaoHistorico;
	}
	public void setCampoSelecaoHistorico(String campoSelecaoHistorico) {
		this.campoSelecaoHistorico = campoSelecaoHistorico;
	}
	public Integer getIdQuestaoVisHistorico() {
		return idQuestaoVisHistorico;
	}
	public void setIdQuestaoVisHistorico(Integer idQuestaoVisHistorico) {
		this.idQuestaoVisHistorico = idQuestaoVisHistorico;
	}
	public Integer getIdContratoVisHistorico() {
		return idContratoVisHistorico;
	}
	public void setIdContratoVisHistorico(Integer idContratoVisHistorico) {
		this.idContratoVisHistorico = idContratoVisHistorico;
	}
	public Timestamp getDataHoraGrav() {
		return dataHoraGrav;
	}
	public void setDataHoraGrav(Timestamp dataHoraGrav) {
		this.dataHoraGrav = dataHoraGrav;
	}
	public String getConteudoImpresso() {
		return conteudoImpresso;
	}
	public void setConteudoImpresso(String conteudoImpresso) {
		this.conteudoImpresso = conteudoImpresso;
	}
	public String getOrdemHistorico() {
		return ordemHistorico;
	}
	public void setOrdemHistorico(String ordemHistorico) {
		this.ordemHistorico = ordemHistorico;
	}
	public String getContinuarEdt() {
		return continuarEdt;
	}
	public void setContinuarEdt(String continuarEdt) {
		this.continuarEdt = continuarEdt;
	}
	/**
	 * @return the idRequisicaoQuestionario
	 */
	public Integer getIdRequisicaoQuestionario() {
		return idRequisicaoQuestionario;
	}
	/**
	 * @param idRequisicaoQuestionario the idRequisicaoQuestionario to set
	 */
	public void setIdRequisicaoQuestionario(Integer idRequisicaoQuestionario) {
		this.idRequisicaoQuestionario = idRequisicaoQuestionario;
	}
	/**
	 * @return the idRequisicao
	 */
	public Integer getIdRequisicao() {
		return idRequisicao;
	}
	/**
	 * @param idRequisicao the idRequisicao to set
	 */
	public void setIdRequisicao(Integer idRequisicao) {
		this.idRequisicao = idRequisicao;
	}
	/**
	 * @return the idTipoAba
	 */
	public Integer getIdTipoAba() {
		return idTipoAba;
	}
	/**
	 * @param idTipoAba the idTipoAba to set
	 */
	public void setIdTipoAba(Integer idTipoAba) {
		this.idTipoAba = idTipoAba;
	}

	/**
	 * @return the idTipoRequisicao
	 */
	public Integer getIdTipoRequisicao() {
		return idTipoRequisicao;
	}

	/**
	 * @param idTipoRequisicao the idTipoRequisicao to set
	 */
	public void setIdTipoRequisicao(Integer idTipoRequisicao) {
		this.idTipoRequisicao = idTipoRequisicao;
	}

	public String getConfirmacao() {
		return confirmacao;
	}

	public void setConfirmacao(String confirmacao) {
		this.confirmacao = confirmacao;
	}

	public String getValorConfirmacao() {
		return valorConfirmacao;
	}

	public void setValorConfirmacao(String valorConfirmacao) {
		this.valorConfirmacao = valorConfirmacao;
	} 
	
}
