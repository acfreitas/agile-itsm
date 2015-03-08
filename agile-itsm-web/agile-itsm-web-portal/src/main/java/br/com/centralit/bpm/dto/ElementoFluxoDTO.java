package br.com.centralit.bpm.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.citframework.dto.IDto;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ElementoFluxo")
public class ElementoFluxoDTO implements IDto {

    private static final long serialVersionUID = 3162985684668155228L;

    @XmlElement(name = "IdFluxo")
    protected Integer idFluxo;

    @XmlElement(name = "TipoElemento")
    protected String tipoElemento;

    @XmlElement(name = "SubTipo")
    protected String subTipo;

    @XmlElement(name = "IdElemento")
    protected Integer idElemento;

    @XmlElement(name = "Nome")
    protected String nome;

    @XmlElement(name = "Documentacao")
    protected String documentacao;

    @XmlElement(name = "AcaoEntrada")
    protected String acaoEntrada;

    @XmlElement(name = "AcaoSaida")
    protected String acaoSaida;

    @XmlElement(name = "TipoInteracao")
    protected String tipoInteracao;

    @XmlElement(name = "Url")
    protected String url;

    @XmlElement(name = "Visao")
    protected String visao;

    @XmlElement(name = "Interacao")
    protected String interacao;

    @XmlElement(name = "Grupos")
    protected String grupos;

    @XmlElement(name = "Usuarios")
    protected String usuarios;

    @XmlElement(name = "NomeFluxoEncadeado")
    protected String nomeFluxoEncadeado;

    @XmlElement(name = "Script")
    protected String script;

    @XmlElement(name = "TextoEmail")
    protected String textoEmail;

    @XmlElement(name = "ModeloEmail")
    protected String modeloEmail;

    @XmlElement(name = "Intervalo")
    protected Integer intervalo;

    @XmlElement(name = "CondicaoDisparo")
    protected String condicaoDisparo;

    @XmlElement(name = "MultiplasInstancias")
    protected String multiplasInstancias;

    @XmlElement(name = "ContabilizaSLA")
    protected String contabilizaSLA;

    @XmlElement(name = "PercExecucao")
    protected Double percExecucao;

    @XmlElement(name = "DestinatariosEmail")
    protected String destinatariosEmail;

    @XmlElement(name = "PosX")
    private Double posX;

    @XmlElement(name = "PosY")
    private Double posY;

    @XmlElement(name = "Largura")
    private Double largura;

    @XmlElement(name = "Altura")
    private Double altura;

    @XmlElement(name = "Label")
    private String label;

    @XmlElement(name = "Icone")
    private String icone;

    @XmlElement(name = "Imagem")
    private String imagem;

    @XmlElement(name = "LarguraPadrao")
    private Double larguraPadrao;

    @XmlElement(name = "AlturaPadrao")
    private Double alturaPadrao;

    @XmlElement(name = "Borda")
    private boolean borda;

    @XmlElement(name = "Ajustavel")
    private boolean ajustavel;

    @XmlElement(name = "Template")
    private String template;

    private String[] lstPropriedades;
    private List<PropriedadeElementoDTO> propriedades;

    private String idDesenho;

    private List<SequenciaFluxoDTO> sequencias;

    private String propriedades_serializadas;

    public Integer getIdFluxo() {
        return idFluxo;
    }

    public void setIdFluxo(final Integer idFluxo) {
        this.idFluxo = idFluxo;
    }

    public String getTipoElemento() {
        return tipoElemento;
    }

    public void setTipoElemento(final String tipoElemento) {
        this.tipoElemento = tipoElemento;
    }

    public Integer getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(final Integer idElemento) {
        this.idElemento = idElemento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getDocumentacao() {
        return documentacao;
    }

    public void setDocumentacao(final String documentacao) {
        this.documentacao = documentacao;
    }

    public String getAcaoEntrada() {
        return acaoEntrada;
    }

    public void setAcaoEntrada(final String acaoEntrada) {
        this.acaoEntrada = acaoEntrada;
    }

    public String getAcaoSaida() {
        return acaoSaida;
    }

    public void setAcaoSaida(final String acaoSaida) {
        this.acaoSaida = acaoSaida;
    }

    public String getTipoInteracao() {
        return tipoInteracao;
    }

    public void setTipoInteracao(final String tipoInteracao) {
        this.tipoInteracao = tipoInteracao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getVisao() {
        return visao;
    }

    public void setVisao(final String visao) {
        this.visao = visao;
    }

    public String getGrupos() {
        return grupos;
    }

    public void setGrupos(final String grupos) {
        this.grupos = grupos;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(final String usuarios) {
        this.usuarios = usuarios;
    }

    public String getScript() {
        return script;
    }

    public String getNomeFluxoEncadeado() {
        return nomeFluxoEncadeado;
    }

    public void setNomeFluxoEncadeado(final String nomeFluxoEncadeado) {
        this.nomeFluxoEncadeado = nomeFluxoEncadeado;
    }

    public void setScript(final String script) {
        this.script = script;
    }

    public String getSubTipo() {
        return subTipo;
    }

    public void setSubTipo(final String subTipo) {
        this.subTipo = subTipo;
    }

    public String getTextoEmail() {
        return textoEmail;
    }

    public void setTextoEmail(final String textoEmail) {
        this.textoEmail = textoEmail;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(final String icone) {
        this.icone = icone;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(final String imagem) {
        this.imagem = imagem;
    }

    public String[] getLstPropriedades() {
        return lstPropriedades;
    }

    public void setLstPropriedades(final String[] lstPropriedades) {
        this.lstPropriedades = lstPropriedades;
    }

    public List<PropriedadeElementoDTO> getPropriedades() {
        return propriedades;
    }

    public void setPropriedades(final List<PropriedadeElementoDTO> propriedades) {
        this.propriedades = propriedades;
    }

    public List<SequenciaFluxoDTO> getSequencias() {
        return sequencias;
    }

    public void setSequencias(final List<SequenciaFluxoDTO> sequencias) {
        this.sequencias = sequencias;
    }

    public boolean getBorda() {
        return borda;
    }

    public void setBorda(final boolean borda) {
        this.borda = borda;
    }

    public boolean getAjustavel() {
        return ajustavel;
    }

    public void setAjustavel(final boolean ajustavel) {
        this.ajustavel = ajustavel;
    }

    public String getIdDesenho() {
        return idDesenho;
    }

    public void setIdDesenho(final String idDesenho) {
        this.idDesenho = idDesenho;
    }

    public String getInteracao() {
        return interacao;
    }

    public void setInteracao(final String interacao) {
        this.interacao = interacao;
    }

    public String getModeloEmail() {
        return modeloEmail;
    }

    public void setModeloEmail(final String modeloEmail) {
        this.modeloEmail = modeloEmail;
    }

    public Double getPosX() {
        return posX;
    }

    public void setPosX(final Double posX) {
        this.posX = posX;
    }

    public Double getPosY() {
        return posY;
    }

    public void setPosY(final Double posY) {
        this.posY = posY;
    }

    public Double getLarguraPadrao() {
        return larguraPadrao;
    }

    public void setLarguraPadrao(final Double larguraPadrao) {
        this.larguraPadrao = larguraPadrao;
    }

    public Double getAlturaPadrao() {
        return alturaPadrao;
    }

    public void setAlturaPadrao(final Double alturaPadrao) {
        this.alturaPadrao = alturaPadrao;
    }

    public void setLargura(final Double largura) {
        this.largura = largura;
    }

    public void setAltura(final Double altura) {
        this.altura = altura;
    }

    public Double getLargura() {
        return largura;
    }

    public Double getAltura() {
        return altura;
    }

    public String getPropriedades_serializadas() {
        return propriedades_serializadas;
    }

    public void setPropriedades_serializadas(final String propriedades_serializadas) {
        this.propriedades_serializadas = propriedades_serializadas;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(final String template) {
        this.template = template;
    }

    public Integer getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(final Integer intervalo) {
        this.intervalo = intervalo;
    }

    public String getCondicaoDisparo() {
        return condicaoDisparo;
    }

    public void setCondicaoDisparo(final String condicaoDisparo) {
        this.condicaoDisparo = condicaoDisparo;
    }

    public String getMultiplasInstancias() {
        return multiplasInstancias;
    }

    public void setMultiplasInstancias(final String multiplasInstancias) {
        this.multiplasInstancias = multiplasInstancias;
    }

    public String getDestinatariosEmail() {
        return destinatariosEmail;
    }

    public void setDestinatariosEmail(final String destinatariosEmail) {
        this.destinatariosEmail = destinatariosEmail;
    }

    public String getContabilizaSLA() {
        return contabilizaSLA;
    }

    public void setContabilizaSLA(final String contabilizaSLA) {
        this.contabilizaSLA = contabilizaSLA;
    }

    public Double getPercExecucao() {
        return percExecucao;
    }

    public void setPercExecucao(final Double percExecucao) {
        this.percExecucao = percExecucao;
    }

}
