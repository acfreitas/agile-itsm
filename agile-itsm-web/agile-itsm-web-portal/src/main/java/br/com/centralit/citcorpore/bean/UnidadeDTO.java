package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.List;

import br.com.citframework.dto.IDto;

@SuppressWarnings("rawtypes")
public class UnidadeDTO implements IDto {

    private static final long serialVersionUID = 638687400065001805L;

    private Integer idUnidade;
    private Integer idGrupo;
    private Integer idUnidadePai;
    private Integer idTipoUnidade;
    private Integer idEmpresa;
    private String nome;
    private Date dataInicio;
    private Date dataFim;
    private String descricao;
    private String email;
    private Integer idEndereco;
    private String aceitaEntregaProduto;
    private List servicos;
    private Integer[] idContrato;
    private int nivel;
    private String localidadesSerializadas;
    private List<LocalidadeUnidadeDTO> listaDeLocalidade;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private Integer idCidade;
    private Integer idPais;
    private String cep;
    private Integer idUf;
    private Double latitude;
    private Double longitude;

    public Integer getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(final Integer idUnidade) {
        this.idUnidade = idUnidade;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(final Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdUnidadePai() {
        return idUnidadePai;
    }

    public void setIdUnidadePai(final Integer idUnidadePai) {
        this.idUnidadePai = idUnidadePai;
    }

    public Integer getIdTipoUnidade() {
        return idTipoUnidade;
    }

    public void setIdTipoUnidade(final Integer idTipoUnidade) {
        this.idTipoUnidade = idTipoUnidade;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(final Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeNivel() {
        if (this.getNome() == null) {
            return nome;
        }
        String str = "";
        for (int i = 0; i < this.getNivel(); i++) {
            str += "....";
        }
        return str + nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(final Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(final Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(final int nivel) {
        this.nivel = nivel;
    }

    public List getServicos() {
        return servicos;
    }

    public void setServicos(final List servicos) {
        this.servicos = servicos;
    }

    public Integer[] getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(final Integer[] idContrato) {
        this.idContrato = idContrato;
    }

    public String getLocalidadesSerializadas() {
        return localidadesSerializadas;
    }

    public void setLocalidadesSerializadas(final String localidadesSerializadas) {
        this.localidadesSerializadas = localidadesSerializadas;
    }

    public List<LocalidadeUnidadeDTO> getListaDeLocalidade() {
        return listaDeLocalidade;
    }

    public void setListaDeLocalidade(final List<LocalidadeUnidadeDTO> listaDeLocalidade) {
        this.listaDeLocalidade = listaDeLocalidade;
    }

    public Integer getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(final Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getAceitaEntregaProduto() {
        return aceitaEntregaProduto;
    }

    public void setAceitaEntregaProduto(final String aceitaEntregaProduto) {
        this.aceitaEntregaProduto = aceitaEntregaProduto;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(final String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(final String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(final String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(final String bairro) {
        this.bairro = bairro;
    }

    public Integer getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(final Integer idCidade) {
        this.idCidade = idCidade;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(final Integer idPais) {
        this.idPais = idPais;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(final String cep) {
        this.cep = cep;
    }

    public Integer getIdUf() {
        return idUf;
    }

    public void setIdUf(final Integer idUf) {
        this.idUf = idUf;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

}
