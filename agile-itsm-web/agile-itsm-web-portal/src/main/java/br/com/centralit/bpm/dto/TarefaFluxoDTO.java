package br.com.centralit.bpm.dto;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.DateTimeAdapter;
import br.com.citframework.util.DtoAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "TarefaFluxo")
public class TarefaFluxoDTO extends ItemTrabalhoFluxoDTO {

    private static final long serialVersionUID = 6660231793397607997L;

    private Date dataCriacao;

    @XmlElement(name = "NomeFluxo")
    private String nomeFluxo;

    private Double percentualExecucao;

    @XmlElement(name = "IdVisao")
    private Integer idVisao;

    @XmlElement(name = "Executar")
    private String executar;

    @XmlElement(name = "Delegar")
    private String delegar;

    @XmlElement(name = "Suspender")
    private String suspender;

    @XmlElement(name = "Reativar")
    private String reativar;

    @XmlElement(name = "AlterarSLA")
    private String alterarSLA;

    @XmlElement(name = "SomenteAcompanhamento")
    private boolean somenteAcompanhamento;

    @XmlElement(name = "Automatica")
    private boolean automatica;

    @XmlElement(name = "DataHoraLimite")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private Timestamp dataHoraLimite;

    @XmlElement(name = "SolicitacaoServico")
    @XmlJavaTypeAdapter(DtoAdapter.class)
    private IDto solicitacaoDto;

    @XmlElement(name = "RequisicaoMudanca")
    @XmlJavaTypeAdapter(DtoAdapter.class)
    private IDto requisicaoMudancaDto;

    @XmlElement(name = "RequisicaoLiberacao")
    @XmlJavaTypeAdapter(DtoAdapter.class)
    private IDto requisicaoLiberacaoDto;

    private String elementoFluxo_serialize;
    private String solicitacao_serialize;

    private String problema_serialize;

    @XmlElement(name = "Problema")
    @XmlJavaTypeAdapter(DtoAdapter.class)
    private IDto problemaDto;

    private String tipoAtribuicao;
    private Integer idTipoFluxo;

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(final Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getNomeFluxo() {
        return nomeFluxo;
    }

    public void setNomeFluxo(final String nomeFluxo) {
        this.nomeFluxo = nomeFluxo;
    }

    public Double getPercentualExecucao() {
        return percentualExecucao;
    }

    public void setPercentualExecucao(final Double percentualExecucao) {
        this.percentualExecucao = percentualExecucao;
    }

    public Integer getIdVisao() {
        return idVisao;
    }

    public void setIdVisao(final Integer idVisao) {
        this.idVisao = idVisao;
    }

    public String getExecutar() {
        return executar;
    }

    public void setExecutar(final String executar) {
        this.executar = executar;
    }

    public String getDelegar() {
        return delegar;
    }

    public void setDelegar(final String delegar) {
        this.delegar = delegar;
    }

    public String getSuspender() {
        return suspender;
    }

    public void setSuspender(final String suspender) {
        this.suspender = suspender;
    }

    public Timestamp getDataHoraLimite() {
        return dataHoraLimite;
    }

    public void setDataHoraLimite(final Timestamp dataHoraLimite) {
        this.dataHoraLimite = dataHoraLimite;
    }

    public String getReativar() {
        return reativar;
    }

    public void setReativar(final String reativar) {
        this.reativar = reativar;
    }

    public String getAlterarSLA() {
        return alterarSLA;
    }

    public void setAlterarSLA(final String alterarSLA) {
        this.alterarSLA = alterarSLA;
    }

    public boolean isSomenteAcompanhamento() {
        return somenteAcompanhamento;
    }

    public void setSomenteAcompanhamento(final boolean somenteAcompanhamento) {
        this.somenteAcompanhamento = somenteAcompanhamento;
    }

    public boolean isAutomatica() {
        return automatica;
    }

    public void setAutomatica(final boolean automatica) {
        this.automatica = automatica;
    }

    public String getElementoFluxo_serialize() {
        return elementoFluxo_serialize;
    }

    public void setElementoFluxo_serialize(final String elementoFluxo_serialize) {
        this.elementoFluxo_serialize = elementoFluxo_serialize;
    }

    public String getSolicitacao_serialize() {
        return solicitacao_serialize;
    }

    public void setSolicitacao_serialize(final String solicitacao_serialize) {
        this.solicitacao_serialize = solicitacao_serialize;
    }

    public String getProblema_serialize() {
        return problema_serialize;
    }

    public void setProblema_serialize(final String problema_serialize) {
        this.problema_serialize = problema_serialize;
    }

    public IDto getSolicitacaoDto() {
        return solicitacaoDto;
    }

    public void setSolicitacaoDto(final IDto solicitacaoDto) {
        this.solicitacaoDto = solicitacaoDto;
    }

    public IDto getRequisicaoMudancaDto() {
        return requisicaoMudancaDto;
    }

    public void setRequisicaoMudancaDto(final IDto requisicaoMudancaDto) {
        this.requisicaoMudancaDto = requisicaoMudancaDto;
    }

    public IDto getRequisicaoLiberacaoDto() {
        return requisicaoLiberacaoDto;
    }

    public void setRequisicaoLiberacaoDto(final IDto requisicaoLiberacaoDto) {
        this.requisicaoLiberacaoDto = requisicaoLiberacaoDto;
    }

    public IDto getProblemaDto() {
        return problemaDto;
    }

    public void setProblemaDto(final IDto problemaDto) {
        this.problemaDto = problemaDto;
    }

    public String getTipoAtribuicao() {
        return tipoAtribuicao;
    }

    public void setTipoAtribuicao(final String tipoAtribuicao) {
        this.tipoAtribuicao = tipoAtribuicao;
    }

	/**
	 * @return the idTipoFluxo
	 */
	public Integer getIdTipoFluxo() {
		return idTipoFluxo;
	}

	/**
	 * @param idTipoFluxo the idTipoFluxo to set
	 */
	public void setIdTipoFluxo(Integer idTipoFluxo) {
		this.idTipoFluxo = idTipoFluxo;
	}

}
