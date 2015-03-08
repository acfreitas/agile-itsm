package br.com.citframework.util.cripto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class InfoCertificadoDigital implements Serializable {

    private static final long serialVersionUID = -4174183193277224522L;

    private String cpf;
    private String nomeTitular;
    private String nomeEmissor;
    private Date dataInicioValidade;
    private Date dataFimValidade;
    private String raiz;
    private String pais;
    private String tipo;
    private int versao;
    private String algoritmo;
    private Collection instituicoes;
    private byte[] conteudoOriginal;
    private boolean keyUsageDigitalSignature;
    private boolean keyUsageNonRepudiation;
    private boolean keyUsageKeyEncipherment;
    private boolean keyUsageDataEncipherment;
    private boolean keyUsageKeyAgreement;
    private boolean keyUsageKeyCertSign;
    private boolean keyUsageCRLSign;
    private boolean keyUsageEncipherOnly;
    private boolean keyUsageDecipherOnly;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getRaiz() {
        return raiz;
    }

    public void setRaiz(String raiz) {
        this.raiz = raiz;
    }

    public byte[] getConteudoOriginal() {
        return conteudoOriginal;
    }

    public void setConteudoOriginal(byte[] conteudoOriginal) {
        this.conteudoOriginal = conteudoOriginal;
    }

    public String getNomeEmissor() {
        return nomeEmissor;
    }

    public void setNomeEmissor(String nomeEmissor) {
        this.nomeEmissor = nomeEmissor;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Collection getInstituicoes() {
        return instituicoes;
    }

    public void setInstituicoes(Collection instituicoes) {
        this.instituicoes = instituicoes;
    }

    public Date getDataInicioValidade() {
        return dataInicioValidade;
    }

    public void setDataInicioValidade(Date dataInicioValidade) {
        this.dataInicioValidade = dataInicioValidade;
    }

    public Date getDataFimValidade() {
        return dataFimValidade;
    }

    public void setDataFimValidade(Date dataFimValidade) {
        this.dataFimValidade = dataFimValidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritimo(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public boolean isKeyUsageDigitalSignature() {
        return keyUsageDigitalSignature;
    }

    public void setKeyUsageDigitalSignature(boolean keyUsageDigitalSignature) {
        this.keyUsageDigitalSignature = keyUsageDigitalSignature;
    }

    public boolean isKeyUsageNonRepudiation() {
        return keyUsageNonRepudiation;
    }

    public void setKeyUsageNonRepudiation(boolean keyUsageNonRepudiation) {
        this.keyUsageNonRepudiation = keyUsageNonRepudiation;
    }

    public boolean isKeyUsageKeyEncipherment() {
        return keyUsageKeyEncipherment;
    }

    public void setKeyUsageKeyEncipherment(boolean keyUsageKeyEncipherment) {
        this.keyUsageKeyEncipherment = keyUsageKeyEncipherment;
    }

    public boolean isKeyUsageDataEncipherment() {
        return keyUsageDataEncipherment;
    }

    public void setKeyUsageDataEncipherment(boolean keyUsageDataEncipherment) {
        this.keyUsageDataEncipherment = keyUsageDataEncipherment;
    }

    public boolean isKeyUsageKeyAgreement() {
        return keyUsageKeyAgreement;
    }

    public void setKeyUsageKeyAgreement(boolean keyUsageKeyAgreement) {
        this.keyUsageKeyAgreement = keyUsageKeyAgreement;
    }

    public boolean isKeyUsageKeyCertSign() {
        return keyUsageKeyCertSign;
    }

    public void setKeyUsageKeyCertSign(boolean keyUsageKeyCertSign) {
        this.keyUsageKeyCertSign = keyUsageKeyCertSign;
    }

    public boolean isKeyUsageCRLSign() {
        return keyUsageCRLSign;
    }

    public void setKeyUsageCRLSign(boolean keyUsageCRLSign) {
        this.keyUsageCRLSign = keyUsageCRLSign;
    }

    public boolean isKeyUsageEncipherOnly() {
        return keyUsageEncipherOnly;
    }

    public void setKeyUsageEncipherOnly(boolean keyUsageEncipherOnly) {
        this.keyUsageEncipherOnly = keyUsageEncipherOnly;
    }

    public boolean isKeyUsageDecipherOnly() {
        return keyUsageDecipherOnly;
    }

    public void setKeyUsageDecipherOnly(boolean keyUsageDecipherOnly) {
        this.keyUsageDecipherOnly = keyUsageDecipherOnly;
    }

}
