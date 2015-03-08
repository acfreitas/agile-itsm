package br.com.centralit.citcorpore.bean;

import java.util.List;

import br.com.citframework.dto.IDto;

public class AcordoServicoContratoDTO implements IDto {

	private static final long serialVersionUID = 7878341453446988862L;
	
	private Integer idAcordoServicoContrato;
	private Integer idAcordoNivelServico;
	private Integer idServicoContrato;
	private java.sql.Date dataCriacao;
	private java.sql.Date dataInicio;
	private java.sql.Date dataFim;
	private java.sql.Date dataUltAtualiz;
	private Integer idRecurso;
	private String deleted;
	private String habilitado;

	private Integer idContrato;
	private List<ServicoContratoDTO> listaServicoContrato;

	public Integer getIdAcordoServicoContrato(){
		return this.idAcordoServicoContrato;
	}
	public void setIdAcordoServicoContrato(Integer parm){
		this.idAcordoServicoContrato = parm;
	}

	public Integer getIdAcordoNivelServico(){
		return this.idAcordoNivelServico;
	}
	public void setIdAcordoNivelServico(Integer parm){
		this.idAcordoNivelServico = parm;
	}

	public Integer getIdServicoContrato(){
		return this.idServicoContrato;
	}
	public void setIdServicoContrato(Integer parm){
		this.idServicoContrato = parm;
	}

	public java.sql.Date getDataCriacao(){
		return this.dataCriacao;
	}
	public void setDataCriacao(java.sql.Date parm){
		this.dataCriacao = parm;
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

	public String getDeleted(){
		return this.deleted;
	}
	public void setDeleted(String parm){
		this.deleted = parm;
	}
	public java.sql.Date getDataUltAtualiz() {
		return dataUltAtualiz;
	}
	public void setDataUltAtualiz(java.sql.Date dataUltAtualiz) {
		this.dataUltAtualiz = dataUltAtualiz;
	}
	public Integer getIdRecurso() {
		return idRecurso;
	}
	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public String getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}
	public List<ServicoContratoDTO> getListaServicoContrato() {
		return listaServicoContrato;
	}
	public void setListaServicoContrato(List<ServicoContratoDTO> listaServicoContrato) {
		this.listaServicoContrato = listaServicoContrato;
	}

}
