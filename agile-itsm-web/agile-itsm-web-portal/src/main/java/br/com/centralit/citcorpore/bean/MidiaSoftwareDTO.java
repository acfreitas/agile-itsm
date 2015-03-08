package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.List;

import br.com.citframework.dto.IDto;

public class MidiaSoftwareDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6560486489318894832L;
	
	private Integer idMidiaSoftware;
	private String nome;
	private String endFisico;
	private String endLogico;
	private String versao;
	private Integer licencas;
	private Integer idMidia;
	private Integer idTipoSoftware;
	private Date dataInicio;
	private Date dataFim;
	private String midiaSoftwareChaveSerializada;
	private List<MidiaSoftwareChaveDTO> midiaSoftwareChaves; 
	private Integer sequenciaLiberacao;
	
	
	
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Integer getIdMidiaSoftware() {
		return idMidiaSoftware;
	}
	public void setIdMidiaSoftware(Integer idMidiaSoftware) {
		this.idMidiaSoftware = idMidiaSoftware;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndFisico() {
		return endFisico;
	}
	public void setEndFisico(String endFisico) {
		this.endFisico = endFisico;
	}
	public String getEndLogico() {
		return endLogico;
	}
	public void setEndLogico(String endLogico) {
		this.endLogico = endLogico;
	}
	public Integer getLicencas() {
		return licencas;
	}
	public void setLicencas(Integer licencas) {
		this.licencas = licencas;
	}
	public Integer getIdMidia() {
		return idMidia;
	}
	public void setIdMidia(Integer idMidia) {
		this.idMidia = idMidia;
	}
	public Integer getIdTipoSoftware() {
		return idTipoSoftware;
	}
	public void setIdTipoSoftware(Integer idTipoSoftware) {
		this.idTipoSoftware = idTipoSoftware;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public String getMidiaSoftwareChaveSerializada() {
		return midiaSoftwareChaveSerializada;
	}
	public void setMidiaSoftwareChaveSerializada(String midiaSoftwareChaveSerializada) {
		this.midiaSoftwareChaveSerializada = midiaSoftwareChaveSerializada;
	}
	public List<MidiaSoftwareChaveDTO> getMidiaSoftwareChaves() {
		return midiaSoftwareChaves;
	}
	public void setMidiaSoftwareChaves(List<MidiaSoftwareChaveDTO> midiaSoftwareChaves) {
		this.midiaSoftwareChaves = midiaSoftwareChaves;
	}
	/**
	 * @return the sequenciaLiberacao
	 */
	public Integer getSequenciaLiberacao() {
		return sequenciaLiberacao;
	}
	/**
	 * @param sequenciaLiberacao the sequenciaLiberacao to set
	 */
	public void setSequenciaLiberacao(Integer sequenciaLiberacao) {
		this.sequenciaLiberacao = sequenciaLiberacao;
	}
}
