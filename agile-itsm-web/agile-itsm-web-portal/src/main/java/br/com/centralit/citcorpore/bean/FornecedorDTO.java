package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

public class FornecedorDTO implements IDto {
	
	private static final long serialVersionUID = -5191625829085954557L;
	
	private Integer idFornecedor;
	private String razaoSocial;
	private String nomeFantasia;
	private String cnpj;
	private String email;
	private String observacao;
	private String deleted;
	private String tipoPessoa;
	private String telefone;
    private String fax;
    private Integer idEndereco;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;    
    private Integer idPais;
    private Integer idUf;
    private Integer idCidade;    
    private String cep;
    private String nomeContato;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;    
    private String identificacao;    
    private String responsabilidades;    
    private Integer idTipoRegistro;    
    private Integer idFrequencia;    
    private Integer idFormaContato;    
    private String ativ_responsabilidades;    
    private String gerenciamentodesacordo;    
    
	private String nomeProduto;
	
	private String qualificado;

	private String marca;
    
    public Integer getIdFornecedor() {
    	return idFornecedor;
    }
    
    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }
    
    public String getRazaoSocial() {
        return razaoSocial;
    }
    
    public String getRazaoSocialHTMLEncoded() {
    	return UtilHTML.encodeHTML(UtilStrings.nullToVazio(razaoSocial));
    }
    
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    
    public String getNomeFantasia() {
        return nomeFantasia;
    }
    
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getObservacao() {
        return observacao;
    }
    
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getFax() {
        return fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public Integer getIdEndereco() {
        return idEndereco;
    }
    
    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }
    
    public String getLogradouro() {
        return logradouro;
    }
    
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getComplemento() {
        return complemento;
    }
    
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    
    public String getBairro() {
        return bairro;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    public Integer getIdPais() {
        return idPais;
    }
    
    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }
    
    public Integer getIdUf() {
    	return idUf;
    }
    
    public void setIdUf(Integer idUf) {
    	this.idUf = idUf;
    }
    
    public Integer getIdCidade() {
        return idCidade;
    }
    
    public void setIdCidade(Integer idCidade) {
        this.idCidade = idCidade;
    }
    
    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    
    public String getNomeContato() {
        return nomeContato;
    }
    
    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }
    
    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }
    
    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }
    
    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }
    
    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }
    
    public String getCnpjFormatado() {
        if (cnpj == null)
            return "";
        return UtilFormatacao.formataCnpj(cnpj);
    }
    
    public String getIdentificacao() {
    	identificacao = getCnpjFormatado();
    	
        if (nomeFantasia != null) {
            if (identificacao.length() > 0)
                identificacao += " - ";            
            identificacao += nomeFantasia;
        }        
        return identificacao;
    }
    
    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }
    
	public String getDeleted() {
		return deleted;
	}
	
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	/**
	 * @return the nomeProduto
	 */
	public String getNomeProduto() {
		return nomeProduto;
	}
	/**
	 * @param nomeProduto the nomeProduto to set
	 */
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}
	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	/**
	 * @return the tipoPessoa
	 */
	public String getTipoPessoa() {
		return tipoPessoa;
	}
	
	/**
	 * @param tipoPessoa
	 */
	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

    /**
     * @return the qualificado
     */
    public String getQualificado() {
        return qualificado;
    }

    /**
     * @param qualificado
     */
    public void setQualificado(String qualificado) {
        this.qualificado = qualificado;
    }

	/**
	 * @return the responsabilidades
	 */
	public String getResponsabilidades() {
		return responsabilidades;
	}

	/**
	 * @param responsabilidades
	 */
	public void setResponsabilidades(String responsabilidades) {
		this.responsabilidades = responsabilidades;
	}

	/**
	 * @return the idTipoRegistro
	 */
	public Integer getIdTipoRegistro() {
		return idTipoRegistro;
	}

	/**
	 * @param idTipoRegistro the idTipoRegistro to set
	 */
	public void setIdTipoRegistro(Integer idTipoRegistro) {
		this.idTipoRegistro = idTipoRegistro;
	}

	/**
	 * @return the idFrequencia
	 */
	public Integer getIdFrequencia() {
		return idFrequencia;
	}

	/**
	 * @param idFrequencia the idFrequencia to set
	 */
	public void setIdFrequencia(Integer idFrequencia) {
		this.idFrequencia = idFrequencia;
	}

	/**
	 * @return the idFormaContato
	 */
	public Integer getIdFormaContato() {
		return idFormaContato;
	}

	/**
	 * @param idFormaContato the idFormaContato to set
	 */
	public void setIdFormaContato(Integer idFormaContato) {
		this.idFormaContato = idFormaContato;
	}

	/**
	 * @return the ativ_responsabilidades
	 */
	public String getAtiv_responsabilidades() {
		return ativ_responsabilidades;
	}

	/**
	 * @param ativ_responsabilidades the ativ_responsabilidades to set
	 */
	public void setAtiv_responsabilidades(String ativ_responsabilidades) {
		this.ativ_responsabilidades = ativ_responsabilidades;
	}

	/**
	 * @return the gerenciamentodesacordo
	 */
	public String getGerenciamentodesacordo() {
		return gerenciamentodesacordo;
	}

	/**
	 * @param gerenciamentodesacordo the gerenciamentodesacordo to set
	 */
	public void setGerenciamentodesacordo(String gerenciamentodesacordo) {
		this.gerenciamentodesacordo = gerenciamentodesacordo;
	}
}