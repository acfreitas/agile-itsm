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

public class AtividadesServicoContratoDTO implements IDto {
    private Integer idAtividadeServicoContrato;
    private Integer idFormulaOs;
    private String estruturaFormulaOs;
    private Integer idServicoContrato;
    private String descricaoAtividade;
    private String obsAtividade;
    private String complexidade;
    private Double custoAtividade;
    private String deleted;
    private Double hora;
    private Integer quantidade;
    private String periodo;
    private String formula;
    private String formulaCalculoFinal;
    private String contabilizar;
    private Integer idServicoContratoContabil;
    private String nomeServico;
    private String tipoCusto;
    private String complexidadeCustoTotal;
    private Integer idContrato;

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public String getFormulaCalculoFinal() {
        return formulaCalculoFinal;
    }

    public void setFormulaCalculoFinal(String formulaCalculoFinal) {
        this.formulaCalculoFinal = formulaCalculoFinal;
    }

    public String getEstruturaFormulaOs() {
        return estruturaFormulaOs;
    }

    public void setEstruturaFormulaOs(String estruturaFormulaOs) {
        this.estruturaFormulaOs = estruturaFormulaOs;
    }

    public Integer getIdFormulaOs() {
        return idFormulaOs;
    }

    public void setIdFormulaOs(Integer idFormulaOs) {
        this.idFormulaOs = idFormulaOs;
    }

    public String getComplexidadeCustoTotal() {
        return complexidadeCustoTotal;
    }

    public void setComplexidadeCustoTotal(String complexidadeCustoTotal) {
        this.complexidadeCustoTotal = complexidadeCustoTotal;
    }

    public Integer getIdAtividadeServicoContrato() {
        return idAtividadeServicoContrato;
    }

    public void setIdAtividadeServicoContrato(Integer parm) {
        idAtividadeServicoContrato = parm;
    }

    public Integer getIdServicoContrato() {
        return idServicoContrato;
    }

    public void setIdServicoContrato(Integer parm) {
        idServicoContrato = parm;
    }

    public String getDescricaoAtividade() {
        return descricaoAtividade;
    }

    public void setDescricaoAtividade(String parm) {
        descricaoAtividade = parm;
    }

    public Double getCustoAtividade() {
        return custoAtividade;
    }

    public void setCustoAtividade(Double parm) {
        custoAtividade = parm;
    }

    public String getObsAtividade() {
        return obsAtividade;
    }

    public void setObsAtividade(String parm) {
        obsAtividade = parm;
    }

    public String getDeleted() {
        if (deleted == null || deleted.trim().equalsIgnoreCase("")) {
            return "N";
        }
        return deleted;
    }

    public void setDeleted(String parm) {
        deleted = parm;
    }

    public String getComplexidade() {
        return complexidade;
    }

    public void setComplexidade(String complexidade) {
        this.complexidade = complexidade;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Double getHora() {
        return hora;
    }

    public void setHora(Double hora) {
        this.hora = hora;
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

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getTipoCusto() {
        return tipoCusto;
    }

    public void setTipoCusto(String tipoCusto) {
        this.tipoCusto = tipoCusto;
    }

}
