package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class InformacoesContratoConfigDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3130191640380469166L;
	
	private Integer idInformacoesContratoConfig;
	private Integer idInformacoesContratoConfigPai;
	private String nome;
	private String descricao;
	private String funcionalidadePath;
	private String funcItem;
	private Integer idQuestionario;
	private Integer idEmpresa;
	private String situacao;
	private Integer ordem;
	private Integer[] perfilSelecionado;
	private String infoAdicional;
	private String funcAdicionalAposGravacao;
	private String chamarFuncAddAposGravar;
	private String chamarFuncAddHistorico;
	private String iconeFuncHistorico;
	private String iconeFuncHistoricoFinal;
	private String[] validacoesAux;
	private String validacoes;
	private String segurancaUnidade;
	private String segurancaUnidadePCMSO;
	private String segurancaUnidadeENFERM;
	
	private Integer nivel;
    public Integer getIdInformacoesContratoConfig() {
        return idInformacoesContratoConfig;
    }
    public void setIdInformacoesContratoConfig(Integer idInformacoesContratoConfig) {
        this.idInformacoesContratoConfig = idInformacoesContratoConfig;
    }
    public Integer getIdInformacoesContratoConfigPai() {
        return idInformacoesContratoConfigPai;
    }
    public void setIdInformacoesContratoConfigPai(
            Integer idInformacoesContratoConfigPai) {
        this.idInformacoesContratoConfigPai = idInformacoesContratoConfigPai;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getFuncionalidadePath() {
        return funcionalidadePath;
    }
    public String getFuncionalidadeCompleta() {
    	if (this.getFuncItem().trim().equalsIgnoreCase("1")){
    		return "/pages/informacoesContratoQuestionario/informacoesContratoQuestionario.load?parm=" + this.getIdQuestionario();
    	}
    	if (this.getFuncItem().trim().equalsIgnoreCase("2")){
    		return "/pages/listaServicosContrato/listaServicosContrato.load";
    	}
    	if (this.getFuncItem().trim().equalsIgnoreCase("3")){
    		return "/pages/listaOSContrato/listaOSContrato.load";
    	}
    	if (this.getFuncItem().trim().equalsIgnoreCase("6")){
    		return "/pages/contratosAnexos/contratosAnexos.load";
    	}
    	return this.getFuncionalidadePath();
    }
    public void setFuncionalidadePath(String funcionalidadePath) {
        this.funcionalidadePath = funcionalidadePath;
    }
    public String getFuncItem() {
        return funcItem;
    }
    public void setFuncItem(String funcItem) {
        this.funcItem = funcItem;
    }
    public Integer getIdQuestionario() {
        return idQuestionario;
    }
    public void setIdQuestionario(Integer idQuestionario) {
        this.idQuestionario = idQuestionario;
    }
    public Integer[] getPerfilSelecionado() {
        return perfilSelecionado;
    }
    public void setPerfilSelecionado(Integer[] perfilSelecionado) {
        this.perfilSelecionado = perfilSelecionado;
    }
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Integer getNivel() {
		return nivel;
	}
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	public String getInfoAdicional() {
		return infoAdicional;
	}
	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}
	public String getFuncAdicionalAposGravacao() {
		return funcAdicionalAposGravacao;
	}
	public void setFuncAdicionalAposGravacao(String funcAdicionalAposGravacao) {
		this.funcAdicionalAposGravacao = funcAdicionalAposGravacao;
	}
	public String getChamarFuncAddAposGravar() {
		return chamarFuncAddAposGravar;
	}
	public void setChamarFuncAddAposGravar(String chamarFuncAddAposGravar) {
		this.chamarFuncAddAposGravar = chamarFuncAddAposGravar;
	}
	public String getChamarFuncAddHistorico() {
		return chamarFuncAddHistorico;
	}
	public void setChamarFuncAddHistorico(String chamarFuncAddHistorico) {
		this.chamarFuncAddHistorico = chamarFuncAddHistorico;
	}
	public String getIconeFuncHistorico() {
		return iconeFuncHistorico;
	}
	public void setIconeFuncHistorico(String iconeFuncHistorico) {
		this.iconeFuncHistorico = iconeFuncHistorico;
	}
	public String getIconeFuncHistoricoFinal() {
		return iconeFuncHistoricoFinal;
	}
	public void setIconeFuncHistoricoFinal(String iconeFuncHistoricoFinal) {
		this.iconeFuncHistoricoFinal = iconeFuncHistoricoFinal;
	}
	public String getValidacoes() {
		return validacoes;
	}
	public void setValidacoes(String validacoes) {
		this.validacoes = validacoes;
	}
	public String[] getValidacoesAux() {
		return validacoesAux;
	}
	public void setValidacoesAux(String[] validacoesAux) {
		this.validacoesAux = validacoesAux;
	}
	public String getSegurancaUnidade() {
		return segurancaUnidade;
	}
	public void setSegurancaUnidade(String segurancaUnidade) {
		this.segurancaUnidade = segurancaUnidade;
	}
	public String getSegurancaUnidadePCMSO() {
		return segurancaUnidadePCMSO;
	}
	public void setSegurancaUnidadePCMSO(String segurancaUnidadePCMSO) {
		this.segurancaUnidadePCMSO = segurancaUnidadePCMSO;
	}
	public String getSegurancaUnidadeENFERM() {
		return segurancaUnidadeENFERM;
	}
	public void setSegurancaUnidadeENFERM(String segurancaUnidadeENFERM) {
		this.segurancaUnidadeENFERM = segurancaUnidadeENFERM;
	}
    
	
	
}
