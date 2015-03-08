package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

public class LinhaBaseProjetoDTO implements IDto {
	public static final String ATIVO = "A";
	public static final String EMEXECUCAO = "E";
	public static final String INATIVO = "I";
	
	private Integer idLinhaBaseProjeto;
	private Integer idProjeto;
	private java.sql.Date dataLinhaBase;
	private String horaLinhaBase;
	private String situacao;
	private java.sql.Date dataUltAlteracao;
	private String horaUltAlteracao;
	private String usuarioUltAlteracao;
	
	private String justificativaMudanca;
	private java.sql.Date dataSolMudanca;
	private String horaSolMudanca;
	private String usuarioSolMudanca;	
	
	private Integer idLinhaBaseProjetoUpdate;
	
	private Collection colTarefas;

	public Integer getIdLinhaBaseProjeto(){
		return this.idLinhaBaseProjeto;
	}
	public void setIdLinhaBaseProjeto(Integer parm){
		this.idLinhaBaseProjeto = parm;
	}

	public Integer getIdProjeto(){
		return this.idProjeto;
	}
	public void setIdProjeto(Integer parm){
		this.idProjeto = parm;
	}

	public java.sql.Date getDataLinhaBase(){
		return this.dataLinhaBase;
	}
	public void setDataLinhaBase(java.sql.Date parm){
		this.dataLinhaBase = parm;
	}

	public String getHoraLinhaBase(){
		return this.horaLinhaBase;
	}
	public void setHoraLinhaBase(String parm){
		this.horaLinhaBase = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}

	public java.sql.Date getDataUltAlteracao(){
		return this.dataUltAlteracao;
	}
	public void setDataUltAlteracao(java.sql.Date parm){
		this.dataUltAlteracao = parm;
	}

	public String getHoraUltAlteracao(){
		return this.horaUltAlteracao;
	}
	public void setHoraUltAlteracao(String parm){
		this.horaUltAlteracao = parm;
	}

	public String getUsuarioUltAlteracao(){
		return this.usuarioUltAlteracao;
	}
	public void setUsuarioUltAlteracao(String parm){
		this.usuarioUltAlteracao = parm;
	}
	public Collection getColTarefas() {
		return colTarefas;
	}
	public void setColTarefas(Collection colTarefas) {
		this.colTarefas = colTarefas;
	}
	public Integer getIdLinhaBaseProjetoUpdate() {
		return idLinhaBaseProjetoUpdate;
	}
	public void setIdLinhaBaseProjetoUpdate(Integer idLinhaBaseProjetoUpdate) {
		this.idLinhaBaseProjetoUpdate = idLinhaBaseProjetoUpdate;
	}
	public java.sql.Date getDataSolMudanca() {
		return dataSolMudanca;
	}
	public void setDataSolMudanca(java.sql.Date dataSolMudanca) {
		this.dataSolMudanca = dataSolMudanca;
	}
	public String getHoraSolMudanca() {
		return horaSolMudanca;
	}
	public void setHoraSolMudanca(String horaSolMudanca) {
		this.horaSolMudanca = horaSolMudanca;
	}
	public String getUsuarioSolMudanca() {
		return usuarioSolMudanca;
	}
	public void setUsuarioSolMudanca(String usuarioSolMudanca) {
		this.usuarioSolMudanca = usuarioSolMudanca;
	}
	public String getJustificativaMudanca() {
		return justificativaMudanca;
	}
	public void setJustificativaMudanca(String justificativaMudanca) {
		this.justificativaMudanca = justificativaMudanca;
	}

}
