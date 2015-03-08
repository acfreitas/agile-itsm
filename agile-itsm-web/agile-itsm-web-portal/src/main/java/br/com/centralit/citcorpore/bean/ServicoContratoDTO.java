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

import java.util.Collection;
import java.util.List;

import br.com.citframework.dto.IDto;

public class ServicoContratoDTO implements IDto {

    private static final long serialVersionUID = -3967063332902600349L;

    private Integer idServicoContrato;
    private Integer idServico;
    private Integer idContrato;
    private Integer idCondicaoOperacao;
    private java.sql.Date dataInicio;
    private java.sql.Date dataFim;
    private String observacao;
    private Double custo;
    private String restricoesPressup;
    private String objetivo;
    private String permiteSLANoCadInc;
    private String linkProcesso;
    private String descricaoProcesso;
    private String tipoDescProcess;
    private String areaRequisitante;
    private Integer totalItens;
    private Integer totalPagina;
    private String observacaoPortal;

    private Integer idModeloEmailCriacao;
    private Integer idModeloEmailFinalizacao;
    private Integer idModeloEmailAcoes;
    private Integer idGrupoNivel1;
    private Integer idGrupoExecutor;
    private Integer idGrupoAprovador;
    private Integer idCalendario;

    private String permSLATempoACombinar;
    private String permMudancaSLA;
    private String permMudancaCalendario;

    private String nomeServico;
    private String nomeTipoDemandaServico;
    private ServicoDTO servicoDto;

    private String deleted;
    private Integer situacaoServico;

    private boolean temSLA;

    private Double dentroPrazo;
    private Double foraPrazo;

    private Integer qtdeDentroPrazo;
    private Integer qtdeForaPrazo;

    private Integer quantidade;
    private Double valorServico;
    private String descricao;
    private String nomeCategoriaServico;

    private List<FluxoServicoDTO> listaFluxo;
    private List<ServicoDTO> listaServico;

    //para busca de checkbox
    private  String colAllLOOKUP_SERVICOATIVOS_DIFERENTE_CONTRATO;
    private String[] colServicosCheckado = null;
    
    private Integer idInfoCatalogoServico;

    private String expandir;
    
    private boolean permiteAnexar;
    
    private boolean exibirColunaAnexar;
    
    private String existeQuestionario;
    
    private Integer idQuestionario;

    private String respostaObrigatoria;
    
    private Collection<SolicitacaoServicoQuestionarioDTO> listSolicitacaoServicoQuestionario;
	

    public Integer getIdServicoContrato() {
        return idServicoContrato;
    }

    public void setIdServicoContrato(Integer parm) {
        idServicoContrato = parm;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer parm) {
        idServico = parm;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer parm) {
        idContrato = parm;
    }

    public Integer getIdCondicaoOperacao() {
        return idCondicaoOperacao;
    }

    public void setIdCondicaoOperacao(Integer parm) {
        idCondicaoOperacao = parm;
    }

    public java.sql.Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(java.sql.Date parm) {
        dataInicio = parm;
    }

    public java.sql.Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(java.sql.Date parm) {
        dataFim = parm;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String parm) {
        observacao = parm;
    }

    public Double getCusto() {
        return custo;
    }

    public void setCusto(Double parm) {
        custo = parm;
    }

    public String getRestricoesPressup() {
        return restricoesPressup;
    }

    public void setRestricoesPressup(String parm) {
        restricoesPressup = parm;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String parm) {
        objetivo = parm;
    }

    public String getPermiteSLANoCadInc() {
        return permiteSLANoCadInc;
    }

    public void setPermiteSLANoCadInc(String parm) {
        permiteSLANoCadInc = parm;
    }

    public String getLinkProcesso() {
        return linkProcesso;
    }

    public void setLinkProcesso(String parm) {
        linkProcesso = parm;
    }

    public String getDescricaoProcesso() {
        return descricaoProcesso;
    }

    public void setDescricaoProcesso(String parm) {
        descricaoProcesso = parm;
    }

    public String getTipoDescProcess() {
        return tipoDescProcess;
    }

    public void setTipoDescProcess(String parm) {
        tipoDescProcess = parm;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public ServicoDTO getServicoDto() {
        return servicoDto;
    }

    public void setServicoDto(ServicoDTO servicoDto) {
        this.servicoDto = servicoDto;
    }

    public String getAreaRequisitante() {
        return areaRequisitante;
    }

    public void setAreaRequisitante(String areaRequisitante) {
        this.areaRequisitante = areaRequisitante;
    }

    public Integer getIdModeloEmailCriacao() {
        return idModeloEmailCriacao;
    }

    public void setIdModeloEmailCriacao(Integer idModeloEmailCriacao) {
        this.idModeloEmailCriacao = idModeloEmailCriacao;
    }

    public Integer getIdModeloEmailFinalizacao() {
        return idModeloEmailFinalizacao;
    }

    public void setIdModeloEmailFinalizacao(Integer idModeloEmailFinalizacao) {
        this.idModeloEmailFinalizacao = idModeloEmailFinalizacao;
    }

    public Integer getIdModeloEmailAcoes() {
        return idModeloEmailAcoes;
    }

    public void setIdModeloEmailAcoes(Integer idModeloEmailAcoes) {
        this.idModeloEmailAcoes = idModeloEmailAcoes;
    }

    public Integer getIdGrupoNivel1() {
        return idGrupoNivel1;
    }

    public void setIdGrupoNivel1(Integer idGrupoNivel1) {
        this.idGrupoNivel1 = idGrupoNivel1;
    }

    public Integer getIdGrupoExecutor() {
        return idGrupoExecutor;
    }

    public void setIdGrupoExecutor(Integer idGrupoExecutor) {
        this.idGrupoExecutor = idGrupoExecutor;
    }

    public Integer getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(Integer idCalendario) {
        this.idCalendario = idCalendario;
    }

    public String getPermSLATempoACombinar() {
        return permSLATempoACombinar;
    }

    public void setPermSLATempoACombinar(String permSLATempoACombinar) {
        this.permSLATempoACombinar = permSLATempoACombinar;
    }

    public String getPermMudancaSLA() {
        return permMudancaSLA;
    }

    public void setPermMudancaSLA(String permMudancaSLA) {
        this.permMudancaSLA = permMudancaSLA;
    }

    public String getPermMudancaCalendario() {
        return permMudancaCalendario;
    }

    public void setPermMudancaCalendario(String permMudancaCalendario) {
        this.permMudancaCalendario = permMudancaCalendario;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getNomeTipoDemandaServico() {
        return nomeTipoDemandaServico;
    }

    public void setNomeTipoDemandaServico(String nomeTipoDemandaServico) {
        this.nomeTipoDemandaServico = nomeTipoDemandaServico;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idServico == null) ? 0 : idServico.hashCode());
        return result;
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
        ServicoContratoDTO other = (ServicoContratoDTO) obj;
        if (idServico == null) {
            if (other.idServico != null) {
                return false;
            }
        } else if (!idServico.equals(other.idServico)) {
            return false;
        }
        return true;
    }

    public Integer getSituacaoServico() {
        return situacaoServico;
    }

    public void setSituacaoServico(Integer situacaoServico) {
        this.situacaoServico = situacaoServico;
    }

    public boolean getTemSLA() {
        return temSLA;
    }

    public void setTemSLA(boolean temSLA) {
        this.temSLA = temSLA;
    }

    public Double getDentroPrazo() {
        return dentroPrazo;
    }

    public void setDentroPrazo(Double dentroPrazo) {
        this.dentroPrazo = dentroPrazo;
    }

    public Double getForaPrazo() {
        return foraPrazo;
    }

    public void setForaPrazo(Double foraPrazo) {
        this.foraPrazo = foraPrazo;
    }

    public Integer getQtdeDentroPrazo() {
        return qtdeDentroPrazo;
    }

    public void setQtdeDentroPrazo(Integer qtdeDentroPrazo) {
        this.qtdeDentroPrazo = qtdeDentroPrazo;
    }

    public Integer getQtdeForaPrazo() {
        return qtdeForaPrazo;
    }

    public void setQtdeForaPrazo(Integer qtdeForaPrazo) {
        this.qtdeForaPrazo = qtdeForaPrazo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getIdGrupoAprovador() {
        return idGrupoAprovador;
    }

    public void setIdGrupoAprovador(Integer idGrupoAprovador) {
        this.idGrupoAprovador = idGrupoAprovador;
    }

    public Integer getTotalItens() {
        return totalItens;
    }

    public void setTotalItens(Integer totalItens) {
        this.totalItens = totalItens;
    }

    public Integer getTotalPagina() {
        return totalPagina;
    }

    public void setTotalPagina(Integer totalPagina) {
        this.totalPagina = totalPagina;
    }

    public List<FluxoServicoDTO> getListaFluxo() {
        return listaFluxo;
    }

    public void setListaFluxo(List<FluxoServicoDTO> listaFluxo) {
        this.listaFluxo = listaFluxo;
    }

    public List<ServicoDTO> getListaServico() {
        return listaServico;
    }

    public void setListaServico(List<ServicoDTO> listaServico) {
        this.listaServico = listaServico;
    }

    public Double getValorServico() {
        return valorServico;
    }

    public void setValorServico(Double valorServico) {
        this.valorServico = valorServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeCategoriaServico() {
        return nomeCategoriaServico;
    }

    public void setNomeCategoriaServico(String nomeCategoriaServico) {
        this.nomeCategoriaServico = nomeCategoriaServico;
    }

    public String getObservacaoPortal() {
        return observacaoPortal;
    }

    public void setObservacaoPortal(String observacaoPortal) {
        this.observacaoPortal = observacaoPortal;
    }

    public String getColAllLOOKUP_SERVICOATIVOS_DIFERENTE_CONTRATO() {
        return colAllLOOKUP_SERVICOATIVOS_DIFERENTE_CONTRATO;
    }

    public void setColAllLOOKUP_SERVICOATIVOS_DIFERENTE_CONTRATO(String colAllLOOKUP_SERVICOATIVOS_DIFERENTE_CONTRATO) {
        this.colAllLOOKUP_SERVICOATIVOS_DIFERENTE_CONTRATO = colAllLOOKUP_SERVICOATIVOS_DIFERENTE_CONTRATO;
    }

    public String[] getColServicosCheckado() {
        return colServicosCheckado;
    }

    public void setColServicosCheckado(String checkados) {

        if (checkados != null) {
            colServicosCheckado = checkados.split(";");
        } else {
            colServicosCheckado = new String[]{};
        }
    }

    public String getExpandir() {
        return expandir;
    }

    public void setExpandir(String expandir) {
        this.expandir = expandir;
    }

	public Integer getIdInfoCatalogoServico() {
		return idInfoCatalogoServico;
	}

	public void setIdInfoCatalogoServico(Integer idInfoCatalogoServico) {
		this.idInfoCatalogoServico = idInfoCatalogoServico;
	}

	public boolean getPermiteAnexar() {				
		return permiteAnexar;
	}

	public void setPermiteAnexar(boolean permiteAnexar) {
		this.permiteAnexar = permiteAnexar;
	}

	public boolean getExibirColunaAnexar() {
		return exibirColunaAnexar;
	}

	public void setExibirColunaAnexar(boolean exibirColunaAnexar) {
		this.exibirColunaAnexar = exibirColunaAnexar;
	}

	public String getExisteQuestionario() {
		return existeQuestionario;
	}

	public void setExisteQuestionario(String existeQuestionario) {
		this.existeQuestionario = existeQuestionario;
	}

	/**
	 * @return the idQuestionario
	 */
	public Integer getIdQuestionario() {
		return idQuestionario;
	}

	/**
	 * @param idQuestionario the idQuestionario to set
	 */
	public void setIdQuestionario(Integer idQuestionario) {
		this.idQuestionario = idQuestionario;
	}

	/**
	 * @return the listSolicitacaoServicoQuestionario
	 */
	public Collection<SolicitacaoServicoQuestionarioDTO> getListSolicitacaoServicoQuestionario() {
		return listSolicitacaoServicoQuestionario;
	}

	/**
	 * @param listSolicitacaoServicoQuestionario the listSolicitacaoServicoQuestionario to set
	 */
	public void setListSolicitacaoServicoQuestionario(Collection<SolicitacaoServicoQuestionarioDTO> listSolicitacaoServicoQuestionario) {
		this.listSolicitacaoServicoQuestionario = listSolicitacaoServicoQuestionario;
	}

	public String getRespostaObrigatoria() {
		return respostaObrigatoria;
	}

	public void setRespostaObrigatoria(String respostaObrigatoria) {
		this.respostaObrigatoria = respostaObrigatoria;
	}

	


}
