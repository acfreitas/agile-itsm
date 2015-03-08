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

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class MoedaDTO implements IDto {

    private Integer idMoeda;
    private String nomeMoeda;
    private String usarCotacao;
    private Date dataInicio;
    private Date dataFim;

    public Integer getIdMoeda() {
        return idMoeda;
    }
    public void setIdMoeda(Integer idMoeda) {
        this.idMoeda = idMoeda;
    }
    public String getNomeMoeda() {
        return nomeMoeda;
    }
    public void setNomeMoeda(String nomeMoeda) {
        this.nomeMoeda = nomeMoeda;
    }
    public String getUsarCotacao() {
        return usarCotacao;
    }
    public void setUsarCotacao(String usarCotacao) {
        this.usarCotacao = usarCotacao;
    }
    public Date getDataInicio() {
        return dataInicio;
    }
    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }
    public Date getDataFim() {
        return dataFim;
    }
    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

}
