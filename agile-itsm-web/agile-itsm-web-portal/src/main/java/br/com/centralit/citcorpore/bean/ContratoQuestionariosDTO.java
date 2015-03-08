package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class ContratoQuestionariosDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1383478887132161536L;
	private Integer idContratoQuestionario;
	private Integer idQuestionario;
	private Integer idContrato;
	private Date dataQuestionario;
	private Integer idProfissional;
	private Integer idEmpresa;
	private String aba;
	private String divAtualizarCertificado;
	private String profissional;
	private String nomeQuestionario;
	
	private String situacao;
	private String situacaoComplemento;
	
	private Integer idItem;
	private Collection colValores;
	private Collection colAnexos;
	
	private String codigoCID;
	private String descricaoCID;
	private String campoRetornoCID;
	
	private String produtos;
	private Integer[] idProduto;
	
	private String migrado;
	private Timestamp datahoragrav;
	
	private Collection colCertificados;
	
	private String campoSelecaoHistorico;
	private Integer idQuestaoVisHistorico;
	private Integer idContratoVisHistorico;
	
	private Integer qtde;
	
	private String conteudoImpresso;
	private Integer idMigracao;
	
	private String ordemHistorico;
	private String matricula;
	private String nomeDepartamento;
	private String sexo;
	private Double idade;
	
	private String continuarEdt;
	
	public Integer getIdMigracao() {
        return idMigracao;
    }
    public void setIdMigracao(Integer idMigracao) {
        this.idMigracao = idMigracao;
    }
	
	public String getKey(){
		if (getIdContratoQuestionario() == null){
			return "NULL";
		}
		return getIdContratoQuestionario().toString();
	}
	public String getTabela(){
		return "ContratosQuestionarios".toUpperCase();
	}
	
	public Date getDataQuestionario() {
		return dataQuestionario;
	}
	public void setDataQuestionario(Date dataQuestionario) {
		this.dataQuestionario = dataQuestionario;
	}
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public Integer getIdContratoQuestionario() {
		return idContratoQuestionario;
	}
	public void setIdContratoQuestionario(Integer idContratoQuestionario) {
		this.idContratoQuestionario = idContratoQuestionario;
	}
	public Integer getIdProfissional() {
		return idProfissional;
	}
	public void setIdProfissional(Integer idProfissional) {
		this.idProfissional = idProfissional;
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
	public String getCodigoCid10() {
		return getCodigoCID();
	}
	public void setCodigoCid10(String codigoCID) {
		setCodigoCID(codigoCID);
	}
	public String getCodigoCID() {
		return codigoCID;
	}
	public void setCodigoCID(String codigoCID) {
		this.codigoCID = codigoCID;
	}
	public String getDescricaoCid10() {
		return getDescricaoCID();
	}
	public void setDescricaoCid10(String descricaoCID) {
		setDescricaoCID(descricaoCID);
	}
	public String getDescricaoCID() {
		return descricaoCID;
	}
	public void setDescricaoCID(String descricaoCID) {
		this.descricaoCID = descricaoCID;
	}
	public String getCampoRetornoCID() {
		return campoRetornoCID;
	}
	public void setCampoRetornoCID(String campoRetornoCID) {
		this.campoRetornoCID = campoRetornoCID;
	}
	public Collection getColAnexos() {
		return colAnexos;
	}
	public void setColAnexos(Collection colAnexos) {
		this.colAnexos = colAnexos;
	}
	public String getProdutos() {
		return produtos;
	}
	public void setProdutos(String produtos) {
		this.produtos = produtos;
	}
	public Integer[] getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Integer[] idProduto) {
		this.idProduto = idProduto;
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
	public String getSituacaoComplemento() {
		return situacaoComplemento;
	}
	public void setSituacaoComplemento(String situacaoComplemento) {
		this.situacaoComplemento = situacaoComplemento;
	}
	public String getMigrado() {
		return migrado;
	}
	public void setMigrado(String migrado) {
		this.migrado = migrado;
	}
	public Timestamp getDatahoragrav() {
		return datahoragrav;
	}
	public void setDatahoragrav(Timestamp datahoragrav) {
		this.datahoragrav = datahoragrav;
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
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNomeDepartamento() {
		return nomeDepartamento;
	}
	public void setNomeDepartamento(String nomeDepartamento) {
		this.nomeDepartamento = nomeDepartamento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Double getIdade() {
		return idade;
	}
	public void setIdade(Double idade) {
		this.idade = idade;
	}
	public String getContinuarEdt() {
		return continuarEdt;
	}
	public void setContinuarEdt(String continuarEdt) {
		this.continuarEdt = continuarEdt;
	}
	
}
