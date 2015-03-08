package br.com.centralit.citcorpore.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.citframework.dto.IDto;

/**
 * 
 * @author flavio.santana
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MidiaSoftwareChave") 
public class MidiaSoftwareChaveDTO implements IDto {

	private static final long serialVersionUID = 1L;
	private Integer idMidiaSoftwareChave;
	private Integer idMidiaSoftware;
	private String chave;
	private Integer qtdPermissoes;
	
	public Integer getIdMidiaSoftwareChave() {
		return idMidiaSoftwareChave;
	}
	public Integer getIdMidiaSoftware() {
		return idMidiaSoftware;
	}
	public String getChave() {
		return chave;
	}
	public void setIdMidiaSoftwareChave(Integer idMidiaSoftwareChave) {
		this.idMidiaSoftwareChave = idMidiaSoftwareChave;
	}
	public void setIdMidiaSoftware(Integer idMidiaSoftware) {
		this.idMidiaSoftware = idMidiaSoftware;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public Integer getQtdPermissoes() {
		return qtdPermissoes;
	}
	public void setQtdPermissoes(Integer qtdPermissoes) {
		this.qtdPermissoes = qtdPermissoes;
	}
	

}
