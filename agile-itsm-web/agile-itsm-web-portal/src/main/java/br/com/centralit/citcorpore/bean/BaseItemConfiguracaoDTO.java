/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author valdoilo.damasceno
 * 
 */
public class BaseItemConfiguracaoDTO implements IDto {

    private static final long serialVersionUID = 7291574008258558256L;

    private Integer id;

    private String nome;

    private Integer idTipoItemConfiguracao;

    private String executavel;

    private Date dataInicio;

    private Date dataFim;

    private String  tipoexecucao;
    
    private TipoItemConfiguracaoDTO tipoItemConfiguracao;
    
    private String comando;
    
    private Integer idBaseItemConfiguracaoPai;
    

    /**
     * @return valor do atributo comando
     */
    public String getComando() {
        return comando;
    }

    /**
     * Define valor do atributo comando
     * 
     * @param comando 
     */
    public void setComando(String comando) {
        this.comando = comando;
    }

    /**
     * 
     * @return valor do atributo id.
     */
    public Integer getId() {
	return id;
    }

    /**
     * Define valor do atributo id.
     * 
     * @param id
     */
    public void setId(Integer id) {
	this.id = id;
    }

    /**
     * @return valor do atributo idTipoItemConfiguracao.
     */
    public Integer getIdTipoItemConfiguracao() {
	return idTipoItemConfiguracao;
    }

    /**
     * Define valor do atributo idTipoItemConfiguracao.
     * 
     * @param idTipoItemConfiguracao
     */
    public void setIdTipoItemConfiguracao(Integer idTipoItemConfiguracao) {
	this.idTipoItemConfiguracao = idTipoItemConfiguracao;
    }

    /**
     * @return valor do atributo nome.
     */
    public String getNome() {
	return nome;
    }

    /**
     * Define valor do atributo nome.
     * 
     * @param nome
     */
    public void setNome(String nome) {
	this.nome = nome;
    }

    /**
     * @return valor do atributo executavel.
     */
    public String getExecutavel() {
	return executavel;
    }

    /**
     * Define valor do atributo executavel.
     * 
     * @param executavel
     */
    public void setExecutavel(String executavel) {
	this.executavel = executavel;
    }

    /**
     * @return valor do atributo dataInicio.
     */
    public Date getDataInicio() {
	return dataInicio;
    }

    /**
     * Define valor do atributo dataInicio.
     * 
     * @param dataInicio
     */
    public void setDataInicio(Date dataInicio) {
	this.dataInicio = dataInicio;
    }

    /**
     * @return valor do atributo dataFim.
     */
    public Date getDataFim() {
	return dataFim;
    }

    /**
     * Define valor do atributo dataFim.
     * 
     * @param dataFim
     */
    public void setDataFim(Date dataFim) {
	this.dataFim = dataFim;
    }

    /**
     * @return valor do atributo tipoItemConfiguracao.
     */
    public TipoItemConfiguracaoDTO getTipoItemConfiguracao() {
	return tipoItemConfiguracao;
    }

    /**
     * Define valor do atributo tipoItemConfiguracao.
     * 
     * @param tipoItemConfiguracao
     */
    public void setTipoItemConfiguracao(TipoItemConfiguracaoDTO tipoItemConfiguracao) {
	this.tipoItemConfiguracao = tipoItemConfiguracao;
    }

	/**
	 * @return the tipoexecucao
	 */
	public String getTipoexecucao() {
		return tipoexecucao;
	}

	/**
	 * @param tipoexecucao the tipoexecucao to set
	 */
	public void setTipoexecucao(String tipoexecucao) {
		this.tipoexecucao = tipoexecucao;
	}

	/**
	 * @return the idBaseItemConfiguracaoPai
	 */
	public Integer getIdBaseItemConfiguracaoPai() {
		return idBaseItemConfiguracaoPai;
	}

	/**
	 * @param idBaseItemConfiguracaoPai the idBaseItemConfiguracaoPai to set
	 */
	public void setIdBaseItemConfiguracaoPai(Integer idBaseItemConfiguracaoPai) {
		this.idBaseItemConfiguracaoPai = idBaseItemConfiguracaoPai;
	}
}
