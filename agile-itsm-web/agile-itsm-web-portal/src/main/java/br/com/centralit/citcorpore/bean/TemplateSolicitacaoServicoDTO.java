package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class TemplateSolicitacaoServicoDTO implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idTemplate;
	private String nomeTemplate;
	private String identificacao;
    private String nomeClasseAction;
	private String nomeClasseDto;
	private String nomeClasseServico;
	private String urlRecuperacao;
	private String habilitaDirecionamento;
	private String habilitaSituacao;
	private String habilitaSolucao;
	private String habilitaUrgenciaImpacto;
	private String habilitaNotificacaoEmail;
    private String habilitaProblema;
    private String habilitaMudanca;
    private String habilitaItemConfiguracao;
    private String habilitaSolicitacaoRelacionada;
    private String habilitaGravarEContinuar;    
	private String scriptAposRecuperacao;
	private Integer alturaDiv;
	private Integer idQuestionario;
	private String aprovacao;

	public Integer getIdTemplate(){
		return this.idTemplate;
	}
	public void setIdTemplate(Integer parm){
		this.idTemplate = parm;
	}

	public String getNomeTemplate(){
		return this.nomeTemplate;
	}
	public void setNomeTemplate(String parm){
		this.nomeTemplate = parm;
	}

	public String getNomeClasseServico(){
		return this.nomeClasseServico;
	}
	public void setNomeClasseServico(String parm){
		this.nomeClasseServico = parm;
	}

	public String getUrlRecuperacao(){
		return this.urlRecuperacao;
	}
	public void setUrlRecuperacao(String parm){
		this.urlRecuperacao = parm;
	}

    public String getNomeClasseDto() {
        return nomeClasseDto;
    }
    public void setNomeClasseDto(String nomeClasseDto) {
        this.nomeClasseDto = nomeClasseDto;
    }
    public String getNomeClasseAction() {
        return nomeClasseAction;
    }
    public void setNomeClasseAction(String nomeClasseAction) {
        this.nomeClasseAction = nomeClasseAction;
    }
    public String getIdentificacao() {
        return identificacao;
    }
    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }
    public String getScriptAposRecuperacao() {
        return scriptAposRecuperacao;
    }
    public void setScriptAposRecuperacao(String scriptAposRecuperacao) {
        this.scriptAposRecuperacao = scriptAposRecuperacao;
    }
    public String getHabilitaDirecionamento() {
        return habilitaDirecionamento;
    }
    public void setHabilitaDirecionamento(String habilitaDirecionamento) {
        this.habilitaDirecionamento = habilitaDirecionamento;
    }
    public String getHabilitaSituacao() {
        return habilitaSituacao;
    }
    public void setHabilitaSituacao(String habilitaSituacao) {
        this.habilitaSituacao = habilitaSituacao;
    }
    public String getHabilitaSolucao() {
        return habilitaSolucao;
    }
    public Integer getAlturaDiv() {
        return alturaDiv;
    }
    public void setAlturaDiv(Integer alturaDiv) {
        this.alturaDiv = alturaDiv;
    }
    public void setHabilitaSolucao(String habilitaSolucao) {
        this.habilitaSolucao = habilitaSolucao;
    }
    public String getHabilitaUrgenciaImpacto() {
        return habilitaUrgenciaImpacto;
    }
    public void setHabilitaUrgenciaImpacto(String habilitaUrgenciaImpacto) {
        this.habilitaUrgenciaImpacto = habilitaUrgenciaImpacto;
    }
    public String getHabilitaNotificacaoEmail() {
        return habilitaNotificacaoEmail;
    }
    public void setHabilitaNotificacaoEmail(String habilitaNotificacaoEmail) {
        this.habilitaNotificacaoEmail = habilitaNotificacaoEmail;
    }
    public String getHabilitaProblema() {
        return habilitaProblema;
    }
    public void setHabilitaProblema(String habilitaProblema) {
        this.habilitaProblema = habilitaProblema;
    }
    public String getHabilitaMudanca() {
        return habilitaMudanca;
    }
    public void setHabilitaMudanca(String habilitaMudanca) {
        this.habilitaMudanca = habilitaMudanca;
    }
    public String getHabilitaItemConfiguracao() {
        return habilitaItemConfiguracao;
    }
    public void setHabilitaItemConfiguracao(String habilitaItemConfiguracao) {
        this.habilitaItemConfiguracao = habilitaItemConfiguracao;
    }
    public String getHabilitaSolicitacaoRelacionada() {
        return habilitaSolicitacaoRelacionada;
    }
    public void setHabilitaSolicitacaoRelacionada(
            String habilitaSolicitacaoRelacionada) {
        this.habilitaSolicitacaoRelacionada = habilitaSolicitacaoRelacionada;
    }
    public String getHabilitaGravarEContinuar() {
        return habilitaGravarEContinuar;
    }
    public void setHabilitaGravarEContinuar(String habilitaGravarEContinuar) {
        this.habilitaGravarEContinuar = habilitaGravarEContinuar;
    }
	public Integer getIdQuestionario() {
		return idQuestionario;
	}
	public void setIdQuestionario(Integer idQuestionario) {
		this.idQuestionario = idQuestionario;
	}
	
	public boolean isQuestionario() {
	    return this.idQuestionario != null && this.idQuestionario.intValue() > 0;
	}
	public String getAprovacao() {
		return aprovacao;
	}
	public void setAprovacao(String aprovacao) {
		this.aprovacao = aprovacao;
	}

}
