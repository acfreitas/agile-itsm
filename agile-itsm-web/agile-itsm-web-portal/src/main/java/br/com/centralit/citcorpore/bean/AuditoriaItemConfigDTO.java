package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class AuditoriaItemConfigDTO implements IDto {

	private static final long serialVersionUID = 1L;

	
	
	private Integer   idAuditoriaItemConfig;
	private Integer   idItemConfiguracao;
	private Integer   idItemConfiguracaoPai;
	private Integer   idHistoricoIC;
	private Integer   idHistoricoValor;
	private Integer   idUsuario;
	private Integer  idValor;
	private Timestamp dataHoraAlteracao;
	private String    tipoAlteracao;
	
	private String tipoItemConfiguracao;
	private String caracteristica;
	private String valor;
	private String identificacao;
	private String nomeCaracteristica;
	private String dataAlteracaoStr;
	
	private String login;

	
	/**
	 * @return the idAuditoriaItemConfig
	 */
	public Integer getIdAuditoriaItemConfig() {
		return idAuditoriaItemConfig;
	}
	/**
	 * @param idAuditoriaItemConfig the idAuditoriaItemConfig to set
	 */
	public void setIdAuditoriaItemConfig(Integer idAuditoriaItemConfig) {
		this.idAuditoriaItemConfig = idAuditoriaItemConfig;
	}
	/**
	 * @return the idItemConfiguracao
	 */
	public Integer getIdItemConfiguracao() {
		return idItemConfiguracao;
	}
	/**
	 * @param idItemConfiguracao the idItemConfiguracao to set
	 */
	public void setIdItemConfiguracao(Integer idItemConfiguracao) {
		this.idItemConfiguracao = idItemConfiguracao;
	}
	/**
	 * @return the idItemConfiguracaoPai
	 */
	public Integer getIdItemConfiguracaoPai() {
		return idItemConfiguracaoPai;
	}
	/**
	 * @param idItemConfiguracaoPai the idItemConfiguracaoPai to set
	 */
	public void setIdItemConfiguracaoPai(Integer idItemConfiguracaoPai) {
		this.idItemConfiguracaoPai = idItemConfiguracaoPai;
	}
	/**
	 * @return the idHistoricoIC
	 */
	public Integer getIdHistoricoIC() {
		return idHistoricoIC;
	}
	/**
	 * @param idHistoricoIC the idHistoricoIC to set
	 */
	public void setIdHistoricoIC(Integer idHistoricoIC) {
		this.idHistoricoIC = idHistoricoIC;
	}
	/**
	 * @return the idHistoricoValor
	 */
	public Integer getIdHistoricoValor() {
		return idHistoricoValor;
	}
	/**
	 * @param idHistoricoValor the idHistoricoValor to set
	 */
	public void setIdHistoricoValor(Integer idHistoricoValor) {
		this.idHistoricoValor = idHistoricoValor;
	}
	/**
	 * @return the idUsuario
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}
	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	/**
	 * @return the dataHoraAlteracao
	 */
	public Timestamp getDataHoraAlteracao() {
		return dataHoraAlteracao;
	}
	/**
	 * @param dataHoraAlteracao the dataHoraAlteracao to set
	 */
	public void setDataHoraAlteracao(Timestamp dataHoraAlteracao) {
		this.dataHoraAlteracao = dataHoraAlteracao;
	}
	/**
	 * @return the tipoAlteracao
	 */
	public String getTipoAlteracao() {
		return tipoAlteracao;
	}
	/**
	 * @param tipoAlteracao the tipoAlteracao to set
	 */
	public void setTipoAlteracao(String tipoAlteracao) {
		this.tipoAlteracao = tipoAlteracao;
	}
	/**
	 * @return the tipoItemConfiguracao
	 */
	public String getTipoItemConfiguracao() {
		return tipoItemConfiguracao;
	}
	/**
	 * @param tipoItemConfiguracao the tipoItemConfiguracao to set
	 */
	public void setTipoItemConfiguracao(String tipoItemConfiguracao) {
		this.tipoItemConfiguracao = tipoItemConfiguracao;
	}
	/**
	 * @return the caracteristica
	 */
	public String getCaracteristica() {
		return caracteristica;
	}
	/**
	 * @param caracteristica the caracteristica to set
	 */
	public void setCaracteristica(String caracteristica) {
		this.caracteristica = caracteristica;
	}
	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	/**
	 * Alterado para utilizar a nova conversão de datas.
	 * 
	 * @return the dataAlteracaoStr
	 * @author rodrigo.acorse
	 * @since 11/02/2014 14h12
	 * 
	 */
	public String getDataAlteracaoStr() {
		return UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, this.dataHoraAlteracao, null);
	}
	/**
	 * @param dataAlteracaoStr the dataAlteracaoStr to set
	 */
	public void setDataAlteracaoStr(String dataAlteracaoStr) {
		this.dataAlteracaoStr = dataAlteracaoStr;
	}
	public String getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(String identificacao) {
	this.identificacao = UtilStrings.limitarTamanho(identificacao, 400, true);
	}
	public String getNomeCaracteristica() {
		return nomeCaracteristica;
	}
	public void setNomeCaracteristica(String nomeCaracteristica) {
		this.nomeCaracteristica = nomeCaracteristica;
	}
	public Integer getIdValor() {
		return idValor;
	}
	public void setIdValor(Integer idValor) {
		this.idValor = idValor;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	

}