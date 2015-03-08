/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

/**
 * @author valdoilo
 * 
 */
public class PesquisaSatisfacaoDTO implements IDto {

	private static final long serialVersionUID = -5106909887258593160L;

	private String comentario;

	private String Contrato;

	private Date dataFim;

	private Timestamp dataHoraFim;

	private Timestamp dataHoraInicio;

	private Date dataInicio;

	private String frame;

	private String hash;

	private Integer idContrato;

	private Integer idPesquisaSatisfacao;

	private Integer idSolicitacaoServico;

	private Integer idSolicitante;

	private String nomeSolicitante;

	private Integer nota;

	private String operador;

	private String valorNota;
	
	private String tipoRelatorio;
	
	private String locale;
	
	private Integer idResponsavelAtual;
	
	private String nomeResponsavelAtual;
	
	/**
	 * Valor de elementos a serem retornados na pesquisa
	 * 
	 * @author thyen.chang
	 */
	private Integer valorTopList;
	
	public Integer getValorTopList() {
		return valorTopList;
	}

	public void setValorTopList(Integer valorTopList) {
		this.valorTopList = valorTopList;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	public String getContrato() {
		return Contrato;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public Timestamp getDataHoraFim() {
		return dataHoraFim;
	}

	public Timestamp getDataHoraInicio() {
		return dataHoraInicio;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public String getFrame() {
		return frame;
	}

	public String getHash() {
		return hash;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	/**
	 * @return the idPesquisaSatisfacao
	 */
	public Integer getIdPesquisaSatisfacao() {
		return idPesquisaSatisfacao;
	}

	/**
	 * @return the idSolicitacaoServico
	 */
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}

	public Integer getIdSolicitante() {
		return idSolicitante;
	}

	public String getNomeSolicitante() {
		return nomeSolicitante;
	}

	/**
	 * @return the nota
	 */
	public Integer getNota() {
		return nota;
	}

	public String getOperador() {
		return operador;
	}

	public String getValorNota() {
		return valorNota;
	}

	/**
	 * @param comentario
	 *            the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public void setContrato(String contrato) {
		Contrato = contrato;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void setDataHoraFim(Timestamp dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public void setDataHoraInicio(Timestamp dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	/**
	 * @param idPesquisaSatisfacao
	 *            the idPesquisaSatisfacao to set
	 */
	public void setIdPesquisaSatisfacao(Integer idPesquisaSatisfacao) {
		this.idPesquisaSatisfacao = idPesquisaSatisfacao;
	}

	/**
	 * @param idSolicitacaoServico
	 *            the idSolicitacaoServico to set
	 */
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}

	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}

	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}

	/**
	 * @param nota
	 *            the nota to set
	 */
	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public void setValorNota(String valorNota) {
		this.valorNota = valorNota;
	}

	public String getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(String tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}


	public Integer getIdResponsavelAtual() {
		return idResponsavelAtual;
	}

	public void setIdResponsavelAtual(Integer idResponsavelAtual) {
		this.idResponsavelAtual = idResponsavelAtual;
	}

	/**
	 * @return the nomeResponsavelAtual
	 */
	public String getNomeResponsavelAtual() {
		return nomeResponsavelAtual;
	}

	/**
	 * @param nomeResponsavelAtual the nomeResponsavelAtual to set
	 */
	public void setNomeResponsavelAtual(String nomeResponsavelAtual) {
		this.nomeResponsavelAtual = nomeResponsavelAtual;
	}
	
	

}
