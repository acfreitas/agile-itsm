package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class EnderecoDTO implements IDto {

    private static final long serialVersionUID = -8445948392034467703L;

    private Integer idEndereco;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private Integer idCidade;
    private Integer idPais;
    private String cep;
    private String identificacao;
    private String nomeCidade;
    private String siglaUf;
    private String enderecoStr;
    private Integer idUf;
    private Double latitude;
    private Double longitude;

    public Integer getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(final Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(final String logradouro) {
        this.logradouro = logradouro;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(final String identificacao) {
        this.identificacao = identificacao;
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

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(final String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getSiglaUf() {
        return siglaUf;
    }

    public void setSiglaUf(final String siglaUf) {
        this.siglaUf = siglaUf;
    }

    public void setEnderecoStr(final String enderecoStr) {
        this.enderecoStr = enderecoStr;
    }

    public String getEnderecoStr() {
        enderecoStr = "";
        if (identificacao != null) {
            enderecoStr += identificacao + " - ";
        }
        if (logradouro != null) {
            enderecoStr += logradouro;
        }
        if (numero != null) {
            if (enderecoStr.length() > 0) {
                enderecoStr += ", ";
            }
            enderecoStr += numero;
        }
        if (complemento != null) {
            if (enderecoStr.length() > 0) {
                enderecoStr += ", ";
            }
            enderecoStr += complemento;
        }
        if (bairro != null) {
            if (enderecoStr.length() > 0) {
                enderecoStr += ", ";
            }
            enderecoStr += bairro;
        }
        if (nomeCidade != null) {
            if (enderecoStr.length() > 0) {
                enderecoStr += " - ";
            }
            enderecoStr += nomeCidade;
        }
        if (siglaUf != null) {
            if (enderecoStr.length() > 0) {
                enderecoStr += " - ";
            }
            enderecoStr += siglaUf;
        }
        return enderecoStr;
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
