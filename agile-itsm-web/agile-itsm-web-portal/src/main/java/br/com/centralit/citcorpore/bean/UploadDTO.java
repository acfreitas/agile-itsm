package br.com.centralit.citcorpore.bean;

import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.citframework.dto.IDto;

public class UploadDTO implements IDto{
	private String id;
	private String nameFile;
	private String descricao;
	private String path;
	private String temporario;
	private String situacao;
	private String notaTecnicaUpload;
	private Integer idControleGED;
	private String versao;
	private String idMudanca;
	private String caminhoRelativo;
	
	/*Atributo criado para implementação do upload por serviço dentro da grid, referente ao idServico*/
	private Integer idLinhaPai;
	
	private ControleGEDDTO controleGEDDto;
	
	public UploadDTO() {
	
	}
	
	public UploadDTO(String nameFile, String path) {
		this.nameFile = nameFile;
		this.path = path;
	}
	
	public Integer getIdControleGED() {
		return idControleGED;
	}
	public void setIdControleGED(Integer idControleGED) {
		this.idControleGED = idControleGED;
	}
	public String getNameFile() {
		return nameFile;
	}
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTemporario() {
		return temporario;
	}
	public void setTemporario(String temporario) {
		this.temporario = temporario;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getNotaTecnicaUpload() {
		return notaTecnicaUpload;
	}
	public void setNotaTecnicaUpload(String notaTecnicaUpload) {
		this.notaTecnicaUpload = notaTecnicaUpload;
	}

	public ControleGEDDTO getControleGEDDto() {
		return controleGEDDto;
	}

	public void setControleGEDDto(ControleGEDDTO controleGEDDto) {
		this.controleGEDDto = controleGEDDto;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getIdMudanca() {
		return idMudanca;
	}

	public void setIdMudanca(String idMudanca) {
		this.idMudanca = idMudanca;
	}

	public String getCaminhoRelativo() {
		return caminhoRelativo;
	}

	public void setCaminhoRelativo(String caminhoRelativo) {
		this.caminhoRelativo = caminhoRelativo;
	}

	/**
	 * @return the idLinhaPai
	 */
	public Integer getIdLinhaPai() {
		return idLinhaPai;
	}

	/**
	 * @param idLinhaPai the idLinhaPai to set
	 */
	public void setIdLinhaPai(Integer idLinhaPai) {
		this.idLinhaPai = idLinhaPai;
	}
	
}
