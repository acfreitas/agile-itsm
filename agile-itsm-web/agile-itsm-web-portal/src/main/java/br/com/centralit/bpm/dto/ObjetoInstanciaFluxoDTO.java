package br.com.centralit.bpm.dto;

import br.com.citframework.dto.IDto;

public class ObjetoInstanciaFluxoDTO implements IDto {

    private static final long serialVersionUID = -3401382998327232131L;

    private Integer idObjetoInstancia;
    private Integer idInstancia;
    private Integer idItemTrabalho;
    private Integer idObjetoNegocio;
    private String nomeObjeto;
    private String nomeClasse;
    private String tipoAssociacao;
    private String campoChave;
    private String objetoPrincipal;
    private String nomeTabelaBD;
    private String nomeCampoBD;
    private String tipoCampoBD;
    private String valor;

    public Integer getIdObjetoInstancia() {
        return idObjetoInstancia;
    }

    public void setIdObjetoInstancia(final Integer idObjetoInstancia) {
        this.idObjetoInstancia = idObjetoInstancia;
    }

    public Integer getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(final Integer idInstancia) {
        this.idInstancia = idInstancia;
    }

    public Integer getIdItemTrabalho() {
        return idItemTrabalho;
    }

    public void setIdItemTrabalho(final Integer idItemTrabalho) {
        this.idItemTrabalho = idItemTrabalho;
    }

    public Integer getIdObjetoNegocio() {
        return idObjetoNegocio;
    }

    public void setIdObjetoNegocio(final Integer idObjetoNegocio) {
        this.idObjetoNegocio = idObjetoNegocio;
    }

    public String getTipoAssociacao() {
        return tipoAssociacao;
    }

    public void setTipoAssociacao(final String tipoAssociacao) {
        this.tipoAssociacao = tipoAssociacao;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(final String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public String getCampoChave() {
        return campoChave;
    }

    public void setCampoChave(final String campoChave) {
        this.campoChave = campoChave;
    }

    public String getObjetoPrincipal() {
        return objetoPrincipal;
    }

    public void setObjetoPrincipal(final String objetoPrincipal) {
        this.objetoPrincipal = objetoPrincipal;
    }

    public String getNomeTabelaBD() {
        return nomeTabelaBD;
    }

    public void setNomeTabelaBD(final String nomeTabelaBD) {
        this.nomeTabelaBD = nomeTabelaBD;
    }

    public String getNomeObjeto() {
        return nomeObjeto;
    }

    public void setNomeObjeto(final String nomeObjeto) {
        this.nomeObjeto = nomeObjeto;
    }

    public String getNomeCampoBD() {
        return nomeCampoBD;
    }

    public void setNomeCampoBD(final String nomeCampoBD) {
        this.nomeCampoBD = nomeCampoBD;
    }

    public String getTipoCampoBD() {
        return tipoCampoBD;
    }

    public void setTipoCampoBD(final String tipoCampoBD) {
        this.tipoCampoBD = tipoCampoBD;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(final String valor) {
        this.valor = valor;
    }

}
