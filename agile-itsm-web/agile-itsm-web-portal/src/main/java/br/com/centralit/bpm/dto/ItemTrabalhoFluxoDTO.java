package br.com.centralit.bpm.dto;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.DateTimeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ItemTrabalhoFluxo")
public class ItemTrabalhoFluxoDTO implements IDto {

    private static final long serialVersionUID = -7116950974247300660L;

    @XmlElement(name = "IdItemTrabalho")
    protected Integer idItemTrabalho;

    @XmlElement(name = "IdInstancia")
    private Integer idInstancia;

    @XmlElement(name = "IdElemento")
    private Integer idElemento;

    @XmlElement(name = "IdResponsavelAtual")
    private Integer idResponsavelAtual;

    @XmlElement(name = "DataHoraCriacao")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private Timestamp dataHoraCriacao;

    @XmlElement(name = "DataHoraInicio")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private Timestamp dataHoraInicio;

    @XmlElement(name = "DataHoraFinalizacao")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private Timestamp dataHoraFinalizacao;

    @XmlElement(name = "DataHoraExecucao")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private Timestamp dataHoraExecucao;

    @XmlElement(name = "Situacao")
    private String situacao;

    @XmlElement(name = "DescrSituacao")
    private String descrSituacao;

    @XmlElement(name = "ResponsavelAtual")
    private String responsavelAtual;

    @XmlElement(name = "Compartilhamento")
    private String compartilhamento;

    @XmlElement(name = "ElementoFluxo")
    private ElementoFluxoDTO elementoFluxoDto;

    private Integer idFluxo;

    public Integer getIdItemTrabalho() {
        return idItemTrabalho;
    }

    public void setIdItemTrabalho(final Integer idItemTrabalho) {
        this.idItemTrabalho = idItemTrabalho;
    }

    public Integer getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(final Integer idInstancia) {
        this.idInstancia = idInstancia;
    }

    public Integer getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(final Integer idElemento) {
        this.idElemento = idElemento;
    }

    public Integer getIdResponsavelAtual() {
        return idResponsavelAtual;
    }

    public void setIdResponsavelAtual(final Integer idResponsavelAtual) {
        this.idResponsavelAtual = idResponsavelAtual;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(final String situacao) {
        this.situacao = situacao;
        try {
            if (this.situacao != null) {
                descrSituacao = SituacaoItemTrabalho.valueOf(this.situacao.trim()).getDescricao();
            }
        } catch (final Exception e) {

        }
    }

    public ElementoFluxoDTO getElementoFluxoDto() {
        return elementoFluxoDto;
    }

    public void setElementoFluxoDto(final ElementoFluxoDTO elementoFluxoDto) {
        this.elementoFluxoDto = elementoFluxoDto;
    }

    public Timestamp getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void setDataHoraCriacao(final Timestamp dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }

    public String getResponsavelAtual() {
        return responsavelAtual;
    }

    public void setResponsavelAtual(final String responsavelAtual) {
        this.responsavelAtual = responsavelAtual;
    }

    public Timestamp getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(final Timestamp dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Timestamp getDataHoraFinalizacao() {
        return dataHoraFinalizacao;
    }

    public void setDataHoraFinalizacao(final Timestamp dataHoraFinalizacao) {
        this.dataHoraFinalizacao = dataHoraFinalizacao;
    }

    public String getCompartilhamento() {
        return compartilhamento;
    }

    public void setCompartilhamento(final String compartilhamento) {
        this.compartilhamento = compartilhamento;
    }

    public Timestamp getDataHoraExecucao() {
        return dataHoraExecucao;
    }

    public void setDataHoraExecucao(final Timestamp dataHoraExecucao) {
        this.dataHoraExecucao = dataHoraExecucao;
    }

    public String getDescrSituacao() {
        return descrSituacao;
    }

    public void setDescrSituacao(final String descrSituacao) {
        this.descrSituacao = descrSituacao;
    }

    public Integer getIdFluxo() {
        return idFluxo;
    }

    public void setIdFluxo(final Integer idFluxo) {
        this.idFluxo = idFluxo;
    }

}
