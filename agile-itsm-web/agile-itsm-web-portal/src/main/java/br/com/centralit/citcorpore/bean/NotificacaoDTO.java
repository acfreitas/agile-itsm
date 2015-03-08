package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.ArrayList;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class NotificacaoDTO implements IDto {

	private Integer idNotificacao;

	private String titulo;

	private String tipoNotificacao;

	private Date dataInicio;

	private Date dataFim;

	private String usuariosSerializados;

	private String gruposSerializados;

	private ArrayList<NotificacaoUsuarioDTO> listaDeUsuario;

	private ArrayList<NotificacaoGrupoDTO> listaDeGrupo;
	
	private ArrayList<NotificacaoServicoDTO> listaDeServico;

	private Integer idBaseConhecimento;

	private Integer idPasta;
	
	private String origemNotificacao;
	
	private String servicosLancados;
	
	private String nomeTipoNotificacao;
	
	private Integer idNotificacaoExcluir;
	
	private Integer idContratoNotificacao;
	
	private Integer idContrato;
	
	/**
	 * @return the idNotificacao
	 */
	public Integer getIdNotificacao() {
		return idNotificacao;
	}

	/**
	 * @param idNotificacao
	 *            the idNotificacao to set
	 */
	public void setIdNotificacao(Integer idNotificacao) {
		this.idNotificacao = idNotificacao;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the tipoNotificacao
	 */
	public String getTipoNotificacao() {
		return tipoNotificacao;
	}

	/**
	 * @param tipoNotificacao
	 *            the tipoNotificacao to set
	 */
	public void setTipoNotificacao(String tipoNotificacao) {
		this.tipoNotificacao = tipoNotificacao;
	}

	/**
	 * @return the dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio
	 *            the dataInicio to set
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim
	 *            the dataFim to set
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return the usuariosSerializados
	 */
	public String getUsuariosSerializados() {
		return usuariosSerializados;
	}

	/**
	 * @param usuariosSerializados
	 *            the usuariosSerializados to set
	 */
	public void setUsuariosSerializados(String usuariosSerializados) {
		this.usuariosSerializados = usuariosSerializados;
	}

	/**
	 * @return the gruposSerializados
	 */
	public String getGruposSerializados() {
		return gruposSerializados;
	}

	/**
	 * @param gruposSerializados
	 *            the gruposSerializados to set
	 */
	public void setGruposSerializados(String gruposSerializados) {
		this.gruposSerializados = gruposSerializados;
	}

	/**
	 * @return the listaDeUsuario
	 */
	public ArrayList<NotificacaoUsuarioDTO> getListaDeUsuario() {
		return listaDeUsuario;
	}

	/**
	 * @param listaDeUsuario
	 *            the listaDeUsuario to set
	 */
	public void setListaDeUsuario(ArrayList<NotificacaoUsuarioDTO> listaDeUsuario) {
		this.listaDeUsuario = listaDeUsuario;
	}

	/**
	 * @return the litaDeGrupo
	 */
	public ArrayList<NotificacaoGrupoDTO> getListaDeGrupo() {
		return listaDeGrupo;
	}

	/**
	 * @param litaDeGrupo
	 *            the litaDeGrupo to set
	 */
	public void setListaDeGrupo(ArrayList<NotificacaoGrupoDTO> listaDeGrupo) {
		this.listaDeGrupo = listaDeGrupo;
	}

	/**
	 * @return the idBaseConhecimento
	 */
	public Integer getIdBaseConhecimento() {
		return idBaseConhecimento;
	}

	/**
	 * @param idBaseConhecimento
	 *            the idBaseConhecimento to set
	 */
	public void setIdBaseConhecimento(Integer idBaseConhecimento) {
		this.idBaseConhecimento = idBaseConhecimento;
	}

	/**
	 * @return the idPasta
	 */
	public Integer getIdPasta() {
		return idPasta;
	}

	/**
	 * @param idPasta
	 *            the idPasta to set
	 */
	public void setIdPasta(Integer idPasta) {
		this.idPasta = idPasta;
	}

	public String getOrigemNotificacao() {
		return origemNotificacao;
	}
	
	public void setOrigemNotificacao(String origemNotificacao) {
		this.origemNotificacao = origemNotificacao;
	}
	
	public String getNomeTipoNotificacao() {
		return nomeTipoNotificacao;
	}

	public void setNomeTipoNotificacao(String nomeTipoNotificacao) {
		this.nomeTipoNotificacao = nomeTipoNotificacao;
	}

	public Integer getIdNotificacaoExcluir() {
		return idNotificacaoExcluir;
	}

	public void setIdNotificacaoExcluir(Integer idNotificacaoExcluir) {
		this.idNotificacaoExcluir = idNotificacaoExcluir;
	}
	
	public String getServicosLancados() {
		return servicosLancados;
	}

	public void setServicosLancados(String servicosLancados) {
		this.servicosLancados = servicosLancados;
	}

	public Integer getIdContratoNotificacao() {
		return idContratoNotificacao;
	}

	public void setIdContratoNotificacao(Integer idContratoNotificacao) {
		this.idContratoNotificacao = idContratoNotificacao;
	}

	public ArrayList<NotificacaoServicoDTO> getListaDeServico() {
		return listaDeServico;
	}

	public void setListaDeServico(ArrayList<NotificacaoServicoDTO> listaDeServico) {
		this.listaDeServico = listaDeServico;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

}
