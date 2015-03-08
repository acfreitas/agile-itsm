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

public class FaturaApuracaoANSDTO implements IDto {
    private Integer idFaturaApuracaoANS;
    private Integer idFatura;
    private Integer idAcordoNivelServicoContrato;
    private Double valorApurado;
    private String detalhamento;
    private Double percentualGlosa;
    private Double valorGlosa;
    private java.sql.Date dataApuracao;
    private String descricao;

    public Integer getIdFaturaApuracaoANS() {
        return idFaturaApuracaoANS;
    }

    public void setIdFaturaApuracaoANS(Integer parm) {
        idFaturaApuracaoANS = parm;
    }

    public Integer getIdFatura() {
        return idFatura;
    }

    public void setIdFatura(Integer parm) {
        idFatura = parm;
    }

    public Integer getIdAcordoNivelServicoContrato() {
        return idAcordoNivelServicoContrato;
    }

    public void setIdAcordoNivelServicoContrato(Integer parm) {
        idAcordoNivelServicoContrato = parm;
    }

    public Double getValorApurado() {
        return valorApurado;
    }

    public void setValorApurado(Double parm) {
        valorApurado = parm;
    }

    public String getDetalhamento() {
        return detalhamento;
    }

    public void setDetalhamento(String parm) {
        detalhamento = parm;
    }

    public Double getPercentualGlosa() {
        return percentualGlosa;
    }

    public void setPercentualGlosa(Double parm) {
        percentualGlosa = parm;
    }

    public Double getValorGlosa() {
        return valorGlosa;
    }

    public void setValorGlosa(Double parm) {
        valorGlosa = parm;
    }

    public java.sql.Date getDataApuracao() {
        return dataApuracao;
    }

    public void setDataApuracao(java.sql.Date parm) {
        dataApuracao = parm;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao
     *            the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
