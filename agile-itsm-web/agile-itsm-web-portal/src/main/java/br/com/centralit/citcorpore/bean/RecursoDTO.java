package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class RecursoDTO implements IDto {
	public static String NAGIOS_NATIVE = "1";
	public static String NAGIOS_CENTREON = "2";
	
	private Integer idRecurso;
	private Integer idGrupoRecurso;
	private Integer idRecursoPai;
	private String nomeRecurso;
	private java.sql.Date dataInicio;
	private java.sql.Date dataFim;
	private String tipoAtualizacao;
	private Integer idNagiosConexao;
	private String hostName;
	private String serviceName;
	private String horaInicioFunc;
	private String horaFimFunc;	
	private Integer idCalendario;
	private String deleted;
	
	private String statusAberturaInc;
	private Integer idSolicitante;
	private String emailAberturaInc;	
	private String descricaoAbertInc;
	private String impacto;
	private String urgencia;
	private Integer idGrupo;
	private Integer idOrigem;
	private Integer idContrato;
	private Integer idServicoContrato;
	private Integer idEventoMonitoramento;
	private Integer idServico;
	private Integer idItemConfiguracaoPai;
	private Integer idItemConfiguracao;
	private String statusAlerta;
	private String emailsAlerta;
	private String descricaoAlerta;
	
	private Collection colFaixasValores;

	public Integer getIdRecurso(){
		return this.idRecurso;
	}
	public void setIdRecurso(Integer parm){
		this.idRecurso = parm;
	}

	public Integer getIdGrupoRecurso(){
		return this.idGrupoRecurso;
	}
	public void setIdGrupoRecurso(Integer parm){
		this.idGrupoRecurso = parm;
	}

	public Integer getIdRecursoPai(){
		return this.idRecursoPai;
	}
	public void setIdRecursoPai(Integer parm){
		this.idRecursoPai = parm;
	}

	public String getNomeRecurso(){
		return this.nomeRecurso;
	}
	public void setNomeRecurso(String parm){
		this.nomeRecurso = parm;
	}

	public java.sql.Date getDataInicio(){
		return this.dataInicio;
	}
	public void setDataInicio(java.sql.Date parm){
		this.dataInicio = parm;
	}

	public java.sql.Date getDataFim(){
		return this.dataFim;
	}
	public void setDataFim(java.sql.Date parm){
		this.dataFim = parm;
	}

	public String getTipoAtualizacao(){
		return this.tipoAtualizacao;
	}
	public void setTipoAtualizacao(String parm){
		this.tipoAtualizacao = parm;
	}

	public String getDeleted(){
		return this.deleted;
	}
	public void setDeleted(String parm){
		this.deleted = parm;
	}
	public Collection getColFaixasValores() {
	    return colFaixasValores;
	}
	public void setColFaixasValores(Collection colFaixasValores) {
	    this.colFaixasValores = colFaixasValores;
	}
	public Integer getIdNagiosConexao() {
		return idNagiosConexao;
	}
	public void setIdNagiosConexao(Integer idNagiosConexao) {
		this.idNagiosConexao = idNagiosConexao;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getHoraInicioFunc() {
		return horaInicioFunc;
	}
	public void setHoraInicioFunc(String horaInicioFunc) {
		this.horaInicioFunc = horaInicioFunc;
	}
	public String getHoraFimFunc() {
		return horaFimFunc;
	}
	public void setHoraFimFunc(String horaFimFunc) {
		this.horaFimFunc = horaFimFunc;
	}
	public Integer getIdCalendario() {
		return idCalendario;
	}
	public void setIdCalendario(Integer idCalendario) {
		this.idCalendario = idCalendario;
	}
	public String getStatusAberturaInc() {
		return statusAberturaInc;
	}
	public void setStatusAberturaInc(String statusAberturaInc) {
		this.statusAberturaInc = statusAberturaInc;
	}
	public Integer getIdSolicitante() {
		return idSolicitante;
	}
	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}
	public String getEmailAberturaInc() {
		return emailAberturaInc;
	}
	public void setEmailAberturaInc(String emailAberturaInc) {
		this.emailAberturaInc = emailAberturaInc;
	}
	public String getDescricaoAbertInc() {
		return descricaoAbertInc;
	}
	public void setDescricaoAbertInc(String descricaoAbertInc) {
		this.descricaoAbertInc = descricaoAbertInc;
	}
	public String getImpacto() {
		return impacto;
	}
	public void setImpacto(String impacto) {
		this.impacto = impacto;
	}
	public String getUrgencia() {
		return urgencia;
	}
	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}
	public Integer getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}
	public Integer getIdOrigem() {
		return idOrigem;
	}
	public void setIdOrigem(Integer idOrigem) {
		this.idOrigem = idOrigem;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public Integer getIdServicoContrato() {
		return idServicoContrato;
	}
	public void setIdServicoContrato(Integer idServicoContrato) {
		this.idServicoContrato = idServicoContrato;
	}
	public Integer getIdItemConfiguracaoPai() {
		return idItemConfiguracaoPai;
	}
	public void setIdItemConfiguracaoPai(Integer idItemConfiguracaoPai) {
		this.idItemConfiguracaoPai = idItemConfiguracaoPai;
	}
	public Integer getIdItemConfiguracao() {
		return idItemConfiguracao;
	}
	public void setIdItemConfiguracao(Integer idItemConfiguracao) {
		this.idItemConfiguracao = idItemConfiguracao;
	}
	public String getStatusAlerta() {
		return statusAlerta;
	}
	public void setStatusAlerta(String statusAlerta) {
		this.statusAlerta = statusAlerta;
	}
	public String getEmailsAlerta() {
		return emailsAlerta;
	}
	public void setEmailsAlerta(String emailsAlerta) {
		this.emailsAlerta = emailsAlerta;
	}
	public String getDescricaoAlerta() {
		return descricaoAlerta;
	}
	public void setDescricaoAlerta(String descricaoAlerta) {
		this.descricaoAlerta = descricaoAlerta;
	}
	public Integer getIdServico() {
		return idServico;
	}
	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}
	public Integer getIdEventoMonitoramento() {
		return idEventoMonitoramento;
	}
	public void setIdEventoMonitoramento(Integer idEventoMonitoramento) {
		this.idEventoMonitoramento = idEventoMonitoramento;
	}

}
