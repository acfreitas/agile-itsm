package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.DateAdapter;
import br.com.citframework.util.DateTimeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SolicitacaoServicoQuestionario")
public class SolicitacaoServicoQuestionarioDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1383478887132161536L;
	private Integer idSolicitacaoQuestionario;
	private Integer idQuestionario;
	private Integer idSolicitacaoServico;
	
	@XmlElement(name = "dataQuestionario")
	@XmlJavaTypeAdapter(DateAdapter.class)	
	private Date dataQuestionario;
	private Integer idResponsavel;
	private Integer idTarefa;
	private String aba;
	private String divAtualizarCertificado;
	private String profissional;
	private String nomeQuestionario;
	private String subForm;
	
	private String situacao;
	
	private Integer idItem;
	private Collection colValores;
	private Collection colAnexos;
	
	@XmlElement(name = "dataHoraGrav")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)	
	private Timestamp dataHoraGrav;
	
	private Collection colCertificados;
	
	private String campoSelecaoHistorico;
	private Integer idQuestaoVisHistorico;
	private Integer idContratoVisHistorico;
	private Integer idServico;
	
	private String respostaObrigatoria;
	
	private String questionarioRespondido;
	
	private Integer qtde;
	
	private String conteudoImpresso;
	
	private String ordemHistorico;
	
	private String continuarEdt;

	public String getKey(){
		if (getIdSolicitacaoQuestionario() == null){
			return "NULL";
		}
		return getIdSolicitacaoQuestionario().toString();
	}
	public String getTabela(){
		return "solicitacaoservquestionarios".toUpperCase();
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
	public Integer getIdSolicitacaoQuestionario() {
		return idSolicitacaoQuestionario;
	}
	public void setIdSolicitacaoQuestionario(Integer idSolicitacaoQuestionario) {
		this.idSolicitacaoQuestionario = idSolicitacaoQuestionario;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public Integer getIdServico() {
		return idServico;
	}
	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}
	/**
	 * @return the respostaObrigatoria
	 */
	public String getRespostaObrigatoria() {
		return respostaObrigatoria;
	}
	/**
	 * @param respostaObrigatoria the respostaObrigatoria to set
	 */
	public void setRespostaObrigatoria(String respostaObrigatoria) {
		this.respostaObrigatoria = respostaObrigatoria;
	}
	/**
	 * @return the questionarioRespondido
	 */
	public String getQuestionarioRespondido() {
		return questionarioRespondido;
	}
	/**
	 * @param questionarioRespondido the questionarioRespondido to set
	 */
	public void setQuestionarioRespondido(String questionarioRespondido) {
		this.questionarioRespondido = questionarioRespondido;
	}
	
	
}
