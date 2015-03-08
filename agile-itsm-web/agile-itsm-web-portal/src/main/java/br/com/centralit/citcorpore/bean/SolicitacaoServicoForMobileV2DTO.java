/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

/**
 * @author maycon.silva
 *
 */
public class SolicitacaoServicoForMobileV2DTO {

		private Integer idsolicitacaoIndividual;
		private Integer idSolicitacaoServico;
		private Integer idTarefa;
		private Integer idCalendario;
	    private String situacao;
	    private Timestamp dataHoraLimite;
	    private Integer prazoHH;
	    private Integer prazoMM;
	    private Timestamp dataHoraSolicitacao;
	    private Integer prazoCapturaHH;
	    private Integer prazoCapturaMM;
	    private Timestamp dataHoraInicio;
	    private String dataHoraFim;
	   	private String slaACombinar;
	   	private Integer prazohhAnterior;
	   	private Integer prazommAnterior;
	   	private Integer tempoDecorridoHH;
	   	private Integer tempoDecorridoMM;
	   	private Timestamp dataHoraSuspensao;
	   	private Timestamp dataHoraReativacao;
	   	private Timestamp dataHoraCaptura;
	   	private Integer tempoCapturaHH;
	   	private Integer	tempoCapturaMM;
	   	private Integer tempoAtrasoHH;
	   	private Integer tempoAtrasoMM;
	   	private Integer tempoAtendimentoHH;
	   	private Integer tempoAtendimentoMM;
	   	private Timestamp dataHoraInicioSLA;
	   	private String situacaoSLA;
	   	private Timestamp dataHoraSuspensaoSLA;
	   	private Timestamp dataHoraReativacaoSLA;
	   	private Double latitude;
	   	private Double longitude;
	   	private String nomeElementoFluxo;
		private String servico;
		private String  aprovacao;
		/**
		 * @return the idsolicitacaoIndividual
		 */
		public Integer getIdsolicitacaoIndividual() {
			return idsolicitacaoIndividual;
		}
		/**
		 * @param idsolicitacaoIndividual the idsolicitacaoIndividual to set
		 */
		public void setIdsolicitacaoIndividual(Integer idsolicitacaoIndividual) {
			this.idsolicitacaoIndividual = idsolicitacaoIndividual;
		}
		/**
		 * @return the idSolicitacaoServico
		 */
		public Integer getIdSolicitacaoServico() {
			return idSolicitacaoServico;
		}
		/**
		 * @param idSolicitacaoServico the idSolicitacaoServico to set
		 */
		public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
			this.idSolicitacaoServico = idSolicitacaoServico;
		}
		/**
		 * @return the idTarefa
		 */
		public Integer getIdTarefa() {
			return idTarefa;
		}
		/**
		 * @param idTarefa the idTarefa to set
		 */
		public void setIdTarefa(Integer idTarefa) {
			this.idTarefa = idTarefa;
		}
		/**
		 * @return the idCalendario
		 */
		public Integer getIdCalendario() {
			return idCalendario;
		}
		/**
		 * @param idCalendario the idCalendario to set
		 */
		public void setIdCalendario(Integer idCalendario) {
			this.idCalendario = idCalendario;
		}
		/**
		 * @return the situacao
		 */
		public String getSituacao() {
			return situacao;
		}
		/**
		 * @param situacao the situacao to set
		 */
		public void setSituacao(String situacao) {
			this.situacao = situacao;
		}
		/**
		 * @return the dataHoraLimite
		 */
		public Timestamp getDataHoraLimite() {
			return dataHoraLimite;
		}
		/**
		 * @param dataHoraLimite the dataHoraLimite to set
		 */
		public void setDataHoraLimite(Timestamp dataHoraLimite) {
			this.dataHoraLimite = dataHoraLimite;
		}
		/**
		 * @return the prazoHH
		 */
		public Integer getPrazoHH() {
			return prazoHH;
		}
		/**
		 * @param prazoHH the prazoHH to set
		 */
		public void setPrazoHH(Integer prazoHH) {
			this.prazoHH = prazoHH;
		}
		/**
		 * @return the prazoMM
		 */
		public Integer getPrazoMM() {
			return prazoMM;
		}
		/**
		 * @param prazoMM the prazoMM to set
		 */
		public void setPrazoMM(Integer prazoMM) {
			this.prazoMM = prazoMM;
		}
		/**
		 * @return the dataHoraSolicitacao
		 */
		public Timestamp getDataHoraSolicitacao() {
			return dataHoraSolicitacao;
		}
		/**
		 * @param dataHoraSolicitacao the dataHoraSolicitacao to set
		 */
		public void setDataHoraSolicitacao(Timestamp dataHoraSolicitacao) {
			this.dataHoraSolicitacao = dataHoraSolicitacao;
		}
		/**
		 * @return the prazoCapturaHH
		 */
		public Integer getPrazoCapturaHH() {
			return prazoCapturaHH;
		}
		/**
		 * @param prazoCapturaHH the prazoCapturaHH to set
		 */
		public void setPrazoCapturaHH(Integer prazoCapturaHH) {
			this.prazoCapturaHH = prazoCapturaHH;
		}
		/**
		 * @return the prazoCapturaMM
		 */
		public Integer getPrazoCapturaMM() {
			return prazoCapturaMM;
		}
		/**
		 * @param prazoCapturaMM the prazoCapturaMM to set
		 */
		public void setPrazoCapturaMM(Integer prazoCapturaMM) {
			this.prazoCapturaMM = prazoCapturaMM;
		}
		/**
		 * @return the dataHoraInicio
		 */
		public Timestamp getDataHoraInicio() {
			return dataHoraInicio;
		}
		/**
		 * @param dataHoraInicio the dataHoraInicio to set
		 */
		public void setDataHoraInicio(Timestamp dataHoraInicio) {
			this.dataHoraInicio = dataHoraInicio;
		}
		/**
		 * @return the dataHoraFim
		 */
		public String getDataHoraFim() {
			return dataHoraFim;
		}
		/**
		 * @param dataHoraFim the dataHoraFim to set
		 */
		public void setDataHoraFim(String dataHoraFim) {
			this.dataHoraFim = dataHoraFim;
		}
		/**
		 * @return the slaACombinar
		 */
		public String getSlaACombinar() {
			return slaACombinar;
		}
		/**
		 * @param slaACombinar the slaACombinar to set
		 */
		public void setSlaACombinar(String slaACombinar) {
			this.slaACombinar = slaACombinar;
		}
		/**
		 * @return the prazohhAnterior
		 */
		public Integer getPrazohhAnterior() {
			return prazohhAnterior;
		}
		/**
		 * @param prazohhAnterior the prazohhAnterior to set
		 */
		public void setPrazohhAnterior(Integer prazohhAnterior) {
			this.prazohhAnterior = prazohhAnterior;
		}
		/**
		 * @return the prazommAnterior
		 */
		public Integer getPrazommAnterior() {
			return prazommAnterior;
		}
		/**
		 * @param prazommAnterior the prazommAnterior to set
		 */
		public void setPrazommAnterior(Integer prazommAnterior) {
			this.prazommAnterior = prazommAnterior;
		}
		/**
		 * @return the tempoDecorridoHH
		 */
		public Integer getTempoDecorridoHH() {
			return tempoDecorridoHH;
		}
		/**
		 * @param tempoDecorridoHH the tempoDecorridoHH to set
		 */
		public void setTempoDecorridoHH(Integer tempoDecorridoHH) {
			this.tempoDecorridoHH = tempoDecorridoHH;
		}
		/**
		 * @return the tempoDecorridoMM
		 */
		public Integer getTempoDecorridoMM() {
			return tempoDecorridoMM;
		}
		/**
		 * @param tempoDecorridoMM the tempoDecorridoMM to set
		 */
		public void setTempoDecorridoMM(Integer tempoDecorridoMM) {
			this.tempoDecorridoMM = tempoDecorridoMM;
		}
		/**
		 * @return the dataHoraSuspensao
		 */
		public Timestamp getDataHoraSuspensao() {
			return dataHoraSuspensao;
		}
		/**
		 * @param dataHoraSuspensao the dataHoraSuspensao to set
		 */
		public void setDataHoraSuspensao(Timestamp dataHoraSuspensao) {
			this.dataHoraSuspensao = dataHoraSuspensao;
		}
		/**
		 * @return the dataHoraReativacao
		 */
		public Timestamp getDataHoraReativacao() {
			return dataHoraReativacao;
		}
		/**
		 * @param dataHoraReativacao the dataHoraReativacao to set
		 */
		public void setDataHoraReativacao(Timestamp dataHoraReativacao) {
			this.dataHoraReativacao = dataHoraReativacao;
		}
		/**
		 * @return the dataHoraCaptura
		 */
		public Timestamp getDataHoraCaptura() {
			return dataHoraCaptura;
		}
		/**
		 * @param dataHoraCaptura the dataHoraCaptura to set
		 */
		public void setDataHoraCaptura(Timestamp dataHoraCaptura) {
			this.dataHoraCaptura = dataHoraCaptura;
		}
		/**
		 * @return the tempoCapturaHH
		 */
		public Integer getTempoCapturaHH() {
			return tempoCapturaHH;
		}
		/**
		 * @param tempoCapturaHH the tempoCapturaHH to set
		 */
		public void setTempoCapturaHH(Integer tempoCapturaHH) {
			this.tempoCapturaHH = tempoCapturaHH;
		}
		/**
		 * @return the tempoCapturaMM
		 */
		public Integer getTempoCapturaMM() {
			return tempoCapturaMM;
		}
		/**
		 * @param tempoCapturaMM the tempoCapturaMM to set
		 */
		public void setTempoCapturaMM(Integer tempoCapturaMM) {
			this.tempoCapturaMM = tempoCapturaMM;
		}
		/**
		 * @return the tempoAtrasoHH
		 */
		public Integer getTempoAtrasoHH() {
			return tempoAtrasoHH;
		}
		/**
		 * @param tempoAtrasoHH the tempoAtrasoHH to set
		 */
		public void setTempoAtrasoHH(Integer tempoAtrasoHH) {
			this.tempoAtrasoHH = tempoAtrasoHH;
		}
		/**
		 * @return the tempoAtrasoMM
		 */
		public Integer getTempoAtrasoMM() {
			return tempoAtrasoMM;
		}
		/**
		 * @param tempoAtrasoMM the tempoAtrasoMM to set
		 */
		public void setTempoAtrasoMM(Integer tempoAtrasoMM) {
			this.tempoAtrasoMM = tempoAtrasoMM;
		}
		/**
		 * @return the tempoAtendimentoHH
		 */
		public Integer getTempoAtendimentoHH() {
			return tempoAtendimentoHH;
		}
		/**
		 * @param tempoAtendimentoHH the tempoAtendimentoHH to set
		 */
		public void setTempoAtendimentoHH(Integer tempoAtendimentoHH) {
			this.tempoAtendimentoHH = tempoAtendimentoHH;
		}
		/**
		 * @return the tempoAtendimentoMM
		 */
		public Integer getTempoAtendimentoMM() {
			return tempoAtendimentoMM;
		}
		/**
		 * @param tempoAtendimentoMM the tempoAtendimentoMM to set
		 */
		public void setTempoAtendimentoMM(Integer tempoAtendimentoMM) {
			this.tempoAtendimentoMM = tempoAtendimentoMM;
		}
		/**
		 * @return the dataHoraInicioSLA
		 */
		public Timestamp getDataHoraInicioSLA() {
			return dataHoraInicioSLA;
		}
		/**
		 * @param dataHoraInicioSLA the dataHoraInicioSLA to set
		 */
		public void setDataHoraInicioSLA(Timestamp dataHoraInicioSLA) {
			this.dataHoraInicioSLA = dataHoraInicioSLA;
		}
		/**
		 * @return the situacaoSLA
		 */
		public String getSituacaoSLA() {
			return situacaoSLA;
		}
		/**
		 * @param situacaoSLA the situacaoSLA to set
		 */
		public void setSituacaoSLA(String situacaoSLA) {
			this.situacaoSLA = situacaoSLA;
		}
		/**
		 * @return the dataHoraSuspensaoSLA
		 */
		public Timestamp getDataHoraSuspensaoSLA() {
			return dataHoraSuspensaoSLA;
		}
		/**
		 * @param dataHoraSuspensaoSLA the dataHoraSuspensaoSLA to set
		 */
		public void setDataHoraSuspensaoSLA(Timestamp dataHoraSuspensaoSLA) {
			this.dataHoraSuspensaoSLA = dataHoraSuspensaoSLA;
		}
		/**
		 * @return the dataHoraReativacaoSLA
		 */
		public Timestamp getDataHoraReativacaoSLA() {
			return dataHoraReativacaoSLA;
		}
		/**
		 * @param dataHoraReativacaoSLA the dataHoraReativacaoSLA to set
		 */
		public void setDataHoraReativacaoSLA(Timestamp dataHoraReativacaoSLA) {
			this.dataHoraReativacaoSLA = dataHoraReativacaoSLA;
		}
		/**
		 * @return the latitude
		 */
		public Double getLatitude() {
			return latitude;
		}
		/**
		 * @param latitude the latitude to set
		 */
		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}
		/**
		 * @return the longitude
		 */
		public Double getLongitude() {
			return longitude;
		}
		/**
		 * @param longitude the longitude to set
		 */
		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}
		/**
		 * @return the nomeElementoFluxo
		 */
		public String getNomeElementoFluxo() {
			return nomeElementoFluxo;
		}
		/**
		 * @param nomeElementoFluxo the nomeElementoFluxo to set
		 */
		public void setNomeElementoFluxo(String nomeElementoFluxo) {
			this.nomeElementoFluxo = nomeElementoFluxo;
		}
		/**
		 * @return the servico
		 */
		public String getServico() {
			return servico;
		}
		/**
		 * @param servico the servico to set
		 */
		public void setServico(String servico) {
			this.servico = servico;
		}
		/**
		 * @return the aprovacao
		 */
		public String getAprovacao() {
			return aprovacao;
		}
		/**
		 * @param aprovacao the aprovacao to set
		 */
		public void setAprovacao(String aprovacao) {
			this.aprovacao = aprovacao;
		}		
		
		

}
