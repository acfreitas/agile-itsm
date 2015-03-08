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
import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class ServicoDTO implements IDto {
    private static final long serialVersionUID = 1L;
    private String conteudodados;
    private Date dataFim;
    private Date dataInicio;
    private String deleted;
    private String descricaoProcesso;
    private String detalheServico;
    private String dispPortal;
    private Integer idBaseconhecimento;
    private Integer idCategoriaServico;
    private Integer idEmpresa;
    private Integer idImportanciaNegocio;
    private Integer idLocalExecucaoServico;
    private Integer idServico;
    private Integer idContrato;
    private Integer idSituacaoServico;
    private Integer idTemplateAcompanhamento;
    private Integer idTemplateSolicitacao;
    private Integer idTipoDemandaServico;
    private Integer idTipoEventoServico;
    private Integer idTipoServico;
    private String linkProcesso;
    private Timestamp modificadoEm;
    private String modificadoPor;
    private String nomeCategoriaServico;
    private String nomeServico;
    private String nomeTipoServico;
    private String objetivo;
    private String passosServico;
    private String quadroOrientPortal;
    private String siglaAbrev;
    private String tipoDescProcess;

    private Integer idSolicitacao;
    private String complexidade;
    private String nomeTipoDemandaServico;
    private String custoAtividade;
    private String slaAtendido;
    private String nomeSolucionador;

    private String tempoDecorrido;
    private String situacao;

    private Integer tempoAtendimentoHH;
    private Integer tempoAtendimentoMM;
    private Double valorServico;
    private Double valorTotalServico;
    
    /**
     * Valor Top List
     * 
     * @author thyen.chang
     */
    private Integer topList;
    
    public Integer getTopList() {
		return topList;
	}

    public void setTopList(Integer topList) {
		this.topList = topList;
	}

	public String getSlaAtendido() {
        return slaAtendido;
    }

    public void setSlaAtendido(String slaAtendido) {
        this.slaAtendido = slaAtendido;
    }

    public Integer getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(Integer idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    public String getComplexidade() {
        return complexidade;
    }

    public void setComplexidade(String complexidade) {
        this.complexidade = complexidade;
    }

    public String getNomeTipoDemandaServico() {
        return nomeTipoDemandaServico;
    }

    public void setNomeTipoDemandaServico(String nomeTipoDemandaServico) {
        this.nomeTipoDemandaServico = nomeTipoDemandaServico;
    }

    public String getCustoAtividade() {
        return custoAtividade;
    }

    public void setCustoAtividade(String custoAtividade) {
        this.custoAtividade = custoAtividade;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ServicoDTO other = (ServicoDTO) obj;
        if (idServico == null) {
            if (other.idServico != null) {
                return false;
            }
        } else if (!idServico.equals(other.idServico)) {
            return false;
        }
        return true;
    }

    public String getConteudodados() {
        return conteudodados;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public java.sql.Date getDataInicio() {
        return dataInicio;
    }

    public String getDeleted() {
        return deleted;
    }

    public String getDescricaoProcesso() {
        return descricaoProcesso;
    }

    public String getDetalheServico() {
        return detalheServico;
    }

    public String getDispPortal() {
        return dispPortal;
    }

    public Integer getIdBaseconhecimento() {
        return idBaseconhecimento;
    }

    public Integer getIdCategoriaServico() {
        return idCategoriaServico;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public Integer getIdImportanciaNegocio() {
        return idImportanciaNegocio;
    }

    public Integer getIdLocalExecucaoServico() {
        return idLocalExecucaoServico;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public Integer getIdSituacaoServico() {
        return idSituacaoServico;
    }

    public Integer getIdTemplateAcompanhamento() {
        return idTemplateAcompanhamento;
    }

    public Integer getIdTemplateSolicitacao() {
        return idTemplateSolicitacao;
    }

    public Integer getIdTipoDemandaServico() {
        return idTipoDemandaServico;
    }

    public Integer getIdTipoEventoServico() {
        return idTipoEventoServico;
    }

    public Integer getIdTipoServico() {
        return idTipoServico;
    }

    public String getLinkProcesso() {
        return linkProcesso;
    }

    public Timestamp getModificadoEm() {
        return modificadoEm;
    }

    public String getModificadoPor() {
        return modificadoPor;
    }

    public String getNomeCategoriaServico() {
        return nomeCategoriaServico;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public String getNomeTipoServico() {
        return nomeTipoServico;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public String getPassosServico() {
        return passosServico;
    }

    public String getQuadroOrientPortal() {
        return quadroOrientPortal;
    }

    public String getSiglaAbrev() {
        return siglaAbrev;
    }

    public String getTipoDescProcess() {
        return tipoDescProcess;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idServico == null) ? 0 : idServico.hashCode());
        return result;
    }

    public void setConteudodados(String conteudodados) {
        this.conteudodados = conteudodados;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public void setDataInicio(java.sql.Date parm) {
        dataInicio = parm;
    }

    public void setDeleted(String parm) {
        deleted = parm;
    }

    public void setDescricaoProcesso(String parm) {
        descricaoProcesso = parm;
    }

    public void setDetalheServico(String parm) {
        detalheServico = parm;
    }

    public void setDispPortal(String parm) {
        dispPortal = parm;
    }

    public void setIdBaseconhecimento(Integer idBaseconhecimento) {
        this.idBaseconhecimento = idBaseconhecimento;
    }

    public void setIdCategoriaServico(Integer parm) {
        idCategoriaServico = parm;
    }

    public void setIdEmpresa(Integer parm) {
        idEmpresa = parm;
    }

    public void setIdImportanciaNegocio(Integer parm) {
        idImportanciaNegocio = parm;
    }

    public void setIdLocalExecucaoServico(Integer parm) {
        idLocalExecucaoServico = parm;
    }

    public void setIdServico(Integer parm) {
        idServico = parm;
    }

    public void setIdSituacaoServico(Integer parm) {
        idSituacaoServico = parm;
    }

    public void setIdTemplateAcompanhamento(Integer idTemplateAcompanhamento) {
        this.idTemplateAcompanhamento = idTemplateAcompanhamento;
    }

    public void setIdTemplateSolicitacao(Integer idTemplateSolicitacao) {
        this.idTemplateSolicitacao = idTemplateSolicitacao;
    }

    public void setIdTipoDemandaServico(Integer parm) {
        idTipoDemandaServico = parm;
    }

    public void setIdTipoEventoServico(Integer parm) {
        idTipoEventoServico = parm;
    }

    public void setIdTipoServico(Integer parm) {
        idTipoServico = parm;
    }

    public void setLinkProcesso(String parm) {
        linkProcesso = parm;
    }

    public void setModificadoEm(Timestamp modificadoEm) {
        this.modificadoEm = modificadoEm;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public void setNomeCategoriaServico(String nomeCategoriaServico) {
        this.nomeCategoriaServico = nomeCategoriaServico;
    }

    public void setNomeServico(String parm) {
        nomeServico = parm;
    }

    public void setNomeTipoServico(String nomeTipoServico) {
        this.nomeTipoServico = nomeTipoServico;
    }

    public void setObjetivo(String parm) {
        objetivo = parm;
    }

    public void setPassosServico(String parm) {
        passosServico = parm;
    }

    public void setQuadroOrientPortal(String parm) {
        quadroOrientPortal = parm;
    }

    public void setSiglaAbrev(String siglaAbrev) {
        this.siglaAbrev = siglaAbrev;
    }

    public void setTipoDescProcess(String parm) {
        tipoDescProcess = parm;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public String getNomeSolucionador() {
        return nomeSolucionador;
    }

    public void setNomeSolucionador(String nomeSolucionador) {
        this.nomeSolucionador = nomeSolucionador;
    }

    public Integer getTempoAtendimentoHH() {
        return tempoAtendimentoHH;
    }

    public void setTempoAtendimentoHH(Integer tempoAtendimentoHH) {
        this.tempoAtendimentoHH = tempoAtendimentoHH;
    }

    public Integer getTempoAtendimentoMM() {
        return tempoAtendimentoMM;
    }

    public void setTempoAtendimentoMM(Integer tempoAtendimentoMM) {
        this.tempoAtendimentoMM = tempoAtendimentoMM;
    }

    public String getTempoDecorrido() {
        return tempoDecorrido;
    }

    public void setTempoDecorrido(String tempoDecorrido) {
        this.tempoDecorrido = tempoDecorrido;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Double getValorServico() {
        return valorServico;
    }

    public void setValorServico(Double valorServico) {
        this.valorServico = valorServico;
    }

    public Double getValorTotalServico() {
        return valorTotalServico;
    }

    public void setValorTotalServico(Double valorTotalServico) {
        this.valorTotalServico = valorTotalServico;
    }

}
