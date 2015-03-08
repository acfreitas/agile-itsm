/**
 *
 * ************************************************************************************************************
 *
 * Dependentes: BI Citsmart
 *
 * Obs:
 * Qualquer alteração nesta tabela deverá ser informada aos responsáveis pelo desenvolvimento do BI Citsmart.
 * O database do BI Citsmart precisa ter suas tabelas atualizadas de acordo com as mudanças nesta tabela.
 *
 * ************************************************************************************************************
 *
 */

package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class AtividadesOSDTO implements IDto {
    private Integer idAtividadesOS;
    private Integer idOS;
    private Integer sequencia;
    private Integer idAtividadeServicoContrato;
    private String descricaoAtividade;
    private String obsAtividade;
    private Double custoAtividade ;
    private Double glosaAtividade;
    private Double qtdeExecutada;
    private String complexidade;
    private String numeroOS;
    private String deleted;
    private Double custoRealizado;
    private Double custos;
    private Object listaAcordoNivelServico;
    private Object listaAtividadeOs;
    private Object listaGlosasOs;
    private String formula;
    private String contabilizar;
    private Integer idServicoContratoContabil;

    public Object getListaGlosasOs() {
        return listaGlosasOs;
    }

    public void setListaGlosasOs(Object listaGlosasOs) {
        this.listaGlosasOs = listaGlosasOs;
    }

    public Double getCustos() {
        return custos;
    }

    public void setCustos(Double custos) {
        this.custos = custos;
    }

    public Double getCustoRealizado() {
        return custoRealizado;
    }

    public void setCustoRealizado(Double custoRealizado) {
        this.custoRealizado = custoRealizado;
    }

    public Object getListaAtividadeOs() {
        return listaAtividadeOs;
    }

    public void setListaAtividadeOs(Object listaAtividadeOs) {
        this.listaAtividadeOs = listaAtividadeOs;
    }

    public Object getListaAcordoNivelServico() {
        return listaAcordoNivelServico;
    }

    public void setListaAcordoNivelServico(Object listaAcordoNivelServico) {
        this.listaAcordoNivelServico = listaAcordoNivelServico;
    }

    public Integer getIdAtividadesOS() {
        return idAtividadesOS;
    }

    public void setIdAtividadesOS(Integer parm) {
        idAtividadesOS = parm;
    }

    public Integer getIdOS() {
        return idOS;
    }

    public void setIdOS(Integer parm) {
        idOS = parm;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer parm) {
        sequencia = parm;
    }

    public Integer getIdAtividadeServicoContrato() {
        return idAtividadeServicoContrato;
    }

    public void setIdAtividadeServicoContrato(Integer parm) {
        idAtividadeServicoContrato = parm;
    }

    public String getDescricaoAtividade() {
        return descricaoAtividade;
    }

    public void setDescricaoAtividade(String parm) {
        descricaoAtividade = parm;
    }

    public String getObsAtividade() {
        return obsAtividade;
    }

    public void setObsAtividade(String parm) {
        obsAtividade = parm;
    }

    public Double getCustoAtividade() {
        return custoAtividade;
    }

    public void setCustoAtividade(Double parm) {
        custoAtividade = parm;
    }

    public String getComplexidade() {
        return complexidade;
    }

    public void setComplexidade(String parm) {
        complexidade = parm;
    }

    public String getDeleted() {
        return deleted;
    }
    public void setDeleted(String parm) {
        deleted = parm;
    }

    public Double getGlosaAtividade() {
        return glosaAtividade;
    }

    public void setGlosaAtividade(Double glosaAtividade) {
        this.glosaAtividade = glosaAtividade;
    }

    public Double getQtdeExecutada() {
        return qtdeExecutada;
    }

    public void setQtdeExecutada(Double qtdeExecutada) {
        this.qtdeExecutada = qtdeExecutada;
    }

    public String getNumeroOS() {
        return numeroOS;
    }

    public void setNumeroOS(String numeroOS) {
        this.numeroOS = numeroOS;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getContabilizar() {
        return contabilizar;
    }

    public void setContabilizar(String contabilizar) {
        this.contabilizar = contabilizar;
    }

    public Integer getIdServicoContratoContabil() {
        return idServicoContratoContabil;
    }

    public void setIdServicoContratoContabil(Integer idServicoContratoContabil) {
        this.idServicoContratoContabil = idServicoContratoContabil;
    }

}
