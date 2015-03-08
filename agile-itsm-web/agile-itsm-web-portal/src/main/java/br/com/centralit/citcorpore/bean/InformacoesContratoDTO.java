package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class InformacoesContratoDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3130191640380469166L;
	private Integer idContrato;
	private String numeroContrato;
	private String aba;
	private String subForm;
	
	private String conteudoDivImprimir;
	
	private String situacao;
	
	private Integer idImagem;
	private String tipo;
	
	private Integer idServicoContrato;
	private String funcaoListarOS;
	private String funcaoListarFatura;
	
	private Date dataOS1;
	private Date dataOS2;
	
	private Date dataFatura1;
	private Date dataFatura2;
	
	private Date dataInicioAtividade;
	private Date dataFimAtividade;
	
	private Integer idOS;
	private Integer idOSPai;
	
	private Integer idFatura;
	private Integer idFaturaApuracaoANS;
	private Integer idAcordoNivelServicoContrato;
	private Integer idAcordoNivelServico;
	private String tituloSLA;
	
	private String nomeContrato;
	


	private Integer idAcordoServicoContrato;	
	
	private Integer[] idServicoCopiarPara;
	
	public Integer getIdAcordoNivelServicoContrato() {
	    return idAcordoNivelServicoContrato;
	}

	
	public void setIdAcordoNivelServicoContrato(Integer idAcordoNivelServicoContrato) {
	    this.idAcordoNivelServicoContrato = idAcordoNivelServicoContrato;
	}

	public Integer getIdFaturaApuracaoANS() {
	    return idFaturaApuracaoANS;
	}

	public void setIdFaturaApuracaoANS(Integer idFaturaApuracaoANS) {
	    this.idFaturaApuracaoANS = idFaturaApuracaoANS;
	}

	public Integer getIdFatura() {
	    return idFatura;
	}
	
	public void setIdFatura(Integer idFatura) {
	    this.idFatura = idFatura;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public String getNumeroContrato() {
		return numeroContrato;
	}
	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}
	public String getAba() {
		return aba;
	}
	public void setAba(String aba) {
		this.aba = aba;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getSubForm() {
		return subForm;
	}
	public void setSubForm(String subForm) {
		this.subForm = subForm;
	}
	public Integer getIdImagem() {
		return idImagem;
	}
	public void setIdImagem(Integer idImagem) {
		this.idImagem = idImagem;
	}
	public String getConteudoDivImprimir() {
		return conteudoDivImprimir;
	}
	public void setConteudoDivImprimir(String conteudoDivImprimir) {
		this.conteudoDivImprimir = conteudoDivImprimir;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getIdServicoContrato() {
		return idServicoContrato;
	}
	public void setIdServicoContrato(Integer idServicoContrato) {
		this.idServicoContrato = idServicoContrato;
	}
	public String getFuncaoListarOS() {
		return funcaoListarOS;
	}
	public void setFuncaoListarOS(String funcaoListarOS) {
		this.funcaoListarOS = funcaoListarOS;
	}
	public Date getDataOS1() {
		return dataOS1;
	}
	public void setDataOS1(Date dataOS1) {
		this.dataOS1 = dataOS1;
	}
	public Date getDataOS2() {
		return dataOS2;
	}
	public void setDataOS2(Date dataOS2) {
		this.dataOS2 = dataOS2;
	}
	public Integer getIdOS() {
		return idOS;
	}
	public void setIdOS(Integer idOS) {
		this.idOS = idOS;
	}
	public String getFuncaoListarFatura() {
		return funcaoListarFatura;
	}
	public void setFuncaoListarFatura(String funcaoListarFatura) {
		this.funcaoListarFatura = funcaoListarFatura;
	}
	public Date getDataFatura1() {
		return dataFatura1;
	}
	public void setDataFatura1(Date dataFatura1) {
		this.dataFatura1 = dataFatura1;
	}
	public Date getDataFatura2() {
		return dataFatura2;
	}
	public void setDataFatura2(Date dataFatura2) {
		this.dataFatura2 = dataFatura2;
	}


	public Integer getIdAcordoNivelServico() {
	    return idAcordoNivelServico;
	}


	public void setIdAcordoNivelServico(Integer idAcordoNivelServico) {
	    this.idAcordoNivelServico = idAcordoNivelServico;
	}


	public Integer[] getIdServicoCopiarPara() {
	    return idServicoCopiarPara;
	}


	public void setIdServicoCopiarPara(Integer[] idServicoCopiarPara) {
	    this.idServicoCopiarPara = idServicoCopiarPara;
	}


	/**
	 * @return the dataInicioAtividade
	 */
	public Date getDataInicioAtividade() {
		return dataInicioAtividade;
	}


	/**
	 * @param dataInicioAtividade the dataInicioAtividade to set
	 */
	public void setDataInicioAtividade(Date dataInicioAtividade) {
		this.dataInicioAtividade = dataInicioAtividade;
	}


	/**
	 * @return the dataFimAtividade
	 */
	public Date getDataFimAtividade() {
		return dataFimAtividade;
	}


	/**
	 * @param dataFimAtividade the dataFimAtividade to set
	 */
	public void setDataFimAtividade(Date dataFimAtividade) {
		this.dataFimAtividade = dataFimAtividade;
	}

	public String getTituloSLA() {
		return tituloSLA;
	}


	public void setTituloSLA(String tituloSLA) {
		this.tituloSLA = tituloSLA;
	}


	/**
	 * @return the idOSPai
	 */
	public Integer getIdOSPai() {
		return idOSPai;
	}


	/**
	 * @param idOSPai the idOSPai to set
	 */
	public void setIdOSPai(Integer idOSPai) {
		this.idOSPai = idOSPai;
	}


	public Integer getIdAcordoServicoContrato() {
		return idAcordoServicoContrato;
	}


	public void setIdAcordoServicoContrato(Integer idAcordoServicoContrato) {
		this.idAcordoServicoContrato = idAcordoServicoContrato;
	}
	
	public String getNomeContrato() {
		return nomeContrato;
	}


	public void setNomeContrato(String nomeContrato) {
		this.nomeContrato = nomeContrato;
	}


}
