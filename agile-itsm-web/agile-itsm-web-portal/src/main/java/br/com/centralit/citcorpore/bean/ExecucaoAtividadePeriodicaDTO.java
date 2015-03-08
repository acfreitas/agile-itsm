package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class ExecucaoAtividadePeriodicaDTO implements IDto {
	private Integer idExecucaoAtividadePeriodica;
	private Integer idAtividadePeriodica;
	private Integer idProgramacaoAtividade;
	private java.sql.Date dataProgramada;
	private String horaProgramada;
	private String situacao;
	private String detalhamento;
	private String usuario;
	private Integer idEmpregado;
	private java.sql.Date dataExecucao;
	private String horaExecucao;
	private java.sql.Date dataRegistro;
	private String horaRegistro;
	private Integer idMotivoSuspensao;
	private String complementoMotivoSuspensao;
	
	private Collection colArquivosUpload;
	
    private Collection colAnexos;
    
	public Integer getIdExecucaoAtividadePeriodica(){
		return this.idExecucaoAtividadePeriodica;
	}
	public void setIdExecucaoAtividadePeriodica(Integer parm){
		this.idExecucaoAtividadePeriodica = parm;
	}

	public Integer getIdAtividadePeriodica(){
		return this.idAtividadePeriodica;
	}
	public void setIdAtividadePeriodica(Integer parm){
		this.idAtividadePeriodica = parm;
	}

	public java.sql.Date getDataProgramada(){
		return this.dataProgramada;
	}
	public void setDataProgramada(java.sql.Date parm){
		this.dataProgramada = parm;
	}

	public String getHoraProgramada(){
		return this.horaProgramada;
	}
	public void setHoraProgramada(String parm){
		this.horaProgramada = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}
	public String getSituacaoDescr(){
	    if (this.situacao == null){
		return "";
	    }
	    if (this.situacao.equalsIgnoreCase("E")){
		return "Em Execução";
	    }
	    if (this.situacao.equalsIgnoreCase("S")){
		return "Suspenso";
	    }
	    if (this.situacao.equalsIgnoreCase("F")){
		return "Executado";
	    }	    
		return this.situacao;
	}	

	public String getDetalhamento(){
		return this.detalhamento;
	}
	public void setDetalhamento(String parm){
		this.detalhamento = parm;
	}

	public String getUsuario(){
		return this.usuario;
	}
	public void setUsuario(String parm){
		this.usuario = parm;
	}

	public Integer getIdEmpregado(){
		return this.idEmpregado;
	}
	public void setIdEmpregado(Integer parm){
		this.idEmpregado = parm;
	}

	public java.sql.Date getDataExecucao(){
		return this.dataExecucao;
	}
	public void setDataExecucao(java.sql.Date parm){
		this.dataExecucao = parm;
	}

	public String getHoraExecucao(){
		return this.horaExecucao;
	}
	public void setHoraExecucao(String parm){
		this.horaExecucao = parm;
	}

	public java.sql.Date getDataRegistro(){
		return this.dataRegistro;
	}
	public void setDataRegistro(java.sql.Date parm){
		this.dataRegistro = parm;
	}

	public String getHoraRegistro(){
		return this.horaRegistro;
	}
	public void setHoraRegistro(String parm){
		this.horaRegistro = parm;
	}
    public Integer getIdProgramacaoAtividade() {
        return idProgramacaoAtividade;
    }
    public void setIdProgramacaoAtividade(Integer idProgramacaoAtividade) {
        this.idProgramacaoAtividade = idProgramacaoAtividade;
    }
    public Collection getColAnexos() {
        return colAnexos;
    }
    public void setColAnexos(Collection colAnexos) {
        this.colAnexos = colAnexos;
    }
    public Integer getIdMotivoSuspensao() {
        return idMotivoSuspensao;
    }
    public void setIdMotivoSuspensao(Integer idMotivoSuspensao) {
        this.idMotivoSuspensao = idMotivoSuspensao;
    }
    public String getComplementoMotivoSuspensao() {
        return complementoMotivoSuspensao;
    }
    public void setComplementoMotivoSuspensao(String complementoMotivoSuspensao) {
        this.complementoMotivoSuspensao = complementoMotivoSuspensao;
    }
    public Collection getColArquivosUpload() {
        return colArquivosUpload;
    }
    public void setColArquivosUpload(Collection colArquivosUpload) {
        this.colArquivosUpload = colArquivosUpload;
    }

}
