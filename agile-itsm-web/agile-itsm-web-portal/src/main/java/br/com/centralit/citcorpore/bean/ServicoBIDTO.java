package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class ServicoBIDTO implements IDto {

    private static final long serialVersionUID = -7912295059907446253L;

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

    private Integer idConexaoBI;

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

    public String getConteudodados() {
        return conteudodados;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public java.sql.Date getDataInicio() {
        return this.dataInicio;
    }

    public String getDeleted() {
        return this.deleted;
    }

    public String getDescricaoProcesso() {
        return this.descricaoProcesso;
    }

    public String getDetalheServico() {
        return this.detalheServico;
    }

    public String getDispPortal() {
        return this.dispPortal;
    }

    public Integer getIdBaseconhecimento() {
        return idBaseconhecimento;
    }

    public Integer getIdCategoriaServico() {
        return this.idCategoriaServico;
    }

    public Integer getIdEmpresa() {
        return this.idEmpresa;
    }

    public Integer getIdImportanciaNegocio() {
        return this.idImportanciaNegocio;
    }

    public Integer getIdLocalExecucaoServico() {
        return this.idLocalExecucaoServico;
    }

    public Integer getIdServico() {
        return this.idServico;
    }

    public Integer getIdSituacaoServico() {
        return this.idSituacaoServico;
    }

    public Integer getIdTemplateAcompanhamento() {
        return idTemplateAcompanhamento;
    }

    public Integer getIdTemplateSolicitacao() {
        return idTemplateSolicitacao;
    }

    public Integer getIdTipoDemandaServico() {
        return this.idTipoDemandaServico;
    }

    public Integer getIdTipoEventoServico() {
        return this.idTipoEventoServico;
    }

    public Integer getIdTipoServico() {
        return this.idTipoServico;
    }

    public String getLinkProcesso() {
        return this.linkProcesso;
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
        return this.nomeServico;
    }

    public String getNomeTipoServico() {
        return nomeTipoServico;
    }

    public String getObjetivo() {
        return this.objetivo;
    }

    public String getPassosServico() {
        return this.passosServico;
    }

    public String getQuadroOrientPortal() {
        return this.quadroOrientPortal;
    }

    public String getSiglaAbrev() {
        return siglaAbrev;
    }

    public String getTipoDescProcess() {
        return this.tipoDescProcess;
    }

    public void setConteudodados(String conteudodados) {
        this.conteudodados = conteudodados;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public void setDataInicio(java.sql.Date parm) {
        this.dataInicio = parm;
    }

    public void setDeleted(String parm) {
        this.deleted = parm;
    }

    public void setDescricaoProcesso(String parm) {
        this.descricaoProcesso = parm;
    }

    public void setDetalheServico(String parm) {
        this.detalheServico = parm;
    }

    public void setDispPortal(String parm) {
        this.dispPortal = parm;
    }

    public void setIdBaseconhecimento(Integer idBaseconhecimento) {
        this.idBaseconhecimento = idBaseconhecimento;
    }

    public void setIdCategoriaServico(Integer parm) {
        this.idCategoriaServico = parm;
    }

    public void setIdEmpresa(Integer parm) {
        this.idEmpresa = parm;
    }

    public void setIdImportanciaNegocio(Integer parm) {
        this.idImportanciaNegocio = parm;
    }

    public void setIdLocalExecucaoServico(Integer parm) {
        this.idLocalExecucaoServico = parm;
    }

    public void setIdServico(Integer parm) {
        this.idServico = parm;
    }

    public void setIdSituacaoServico(Integer parm) {
        this.idSituacaoServico = parm;
    }

    public void setIdTemplateAcompanhamento(Integer idTemplateAcompanhamento) {
        this.idTemplateAcompanhamento = idTemplateAcompanhamento;
    }

    public void setIdTemplateSolicitacao(Integer idTemplateSolicitacao) {
        this.idTemplateSolicitacao = idTemplateSolicitacao;
    }

    public void setIdTipoDemandaServico(Integer parm) {
        this.idTipoDemandaServico = parm;
    }

    public void setIdTipoEventoServico(Integer parm) {
        this.idTipoEventoServico = parm;
    }

    public void setIdTipoServico(Integer parm) {
        this.idTipoServico = parm;
    }

    public void setLinkProcesso(String parm) {
        this.linkProcesso = parm;
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
        this.nomeServico = parm;
    }

    public void setNomeTipoServico(String nomeTipoServico) {
        this.nomeTipoServico = nomeTipoServico;
    }

    public void setObjetivo(String parm) {
        this.objetivo = parm;
    }

    public void setPassosServico(String parm) {
        this.passosServico = parm;
    }

    public void setQuadroOrientPortal(String parm) {
        this.quadroOrientPortal = parm;
    }

    public void setSiglaAbrev(String siglaAbrev) {
        this.siglaAbrev = siglaAbrev;
    }

    public void setTipoDescProcess(String parm) {
        this.tipoDescProcess = parm;
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

    public Integer getIdConexaoBI() {
        return idConexaoBI;
    }

    public void setIdConexaoBI(Integer idConexaoBI) {
        this.idConexaoBI = idConexaoBI;
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

        ServicoBIDTO other = (ServicoBIDTO) obj;
        if (idServico == null) {
            if (other.idServico != null) {
                return false;
            }
        } else if (!idServico.equals(other.idServico)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idServico == null) ? 0 : idServico.hashCode());
        return result;
    }

}
