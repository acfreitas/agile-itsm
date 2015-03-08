package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class FormacaoCurriculoDTO implements IDto,Comparable<FormacaoCurriculoDTO> {
	private Integer idFormacao;
	private Integer idTipoFormacao;
	private Integer idSituacao;
	private String instituicao;
	
	private Integer idPessoa;
	
	private String descricaoTipoFormacao;
	private String descricaoSituacao;
	private String descricao;
	private Integer idCurriculo;
	
	
	
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	public Integer getIdFormacao() {
		return idFormacao;
	}
	public void setIdFormacao(Integer idFormacao) {
		this.idFormacao = idFormacao;
	}
	public Integer getIdTipoFormacao() {
		return idTipoFormacao;
	}
	public void setIdTipoFormacao(Integer idTipoFormacao) {
		this.idTipoFormacao = idTipoFormacao;
	}
	public Integer getIdSituacao() {
		return idSituacao;
	}
	public void setIdSituacao(Integer idSituacao) {
		this.idSituacao = idSituacao;
	}
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	public Integer getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}
	public String getDescricaoTipoFormacao() {
		switch (this.getIdTipoFormacao()) {
		case 1: descricaoTipoFormacao = "Ensino Fundamental";
		break;
		case 2: descricaoTipoFormacao = "Ensino Médio";
		break;
		case 3: descricaoTipoFormacao = "Graduação";
		break;
		case 4: descricaoTipoFormacao = "Pós-Graduação";
		break;
		case 5: descricaoTipoFormacao = "Mestrado";
		break;
		case 6: descricaoTipoFormacao = "Doutorado";
		break;
		case 7: descricaoTipoFormacao = "Treinamento";
		}
		return descricaoTipoFormacao;
	}
	public void setDescricaoTipoFormacao(String descricaoTipoFormacao) {
		this.descricaoTipoFormacao = descricaoTipoFormacao;
	}
	public String getDescricaoSituacao() {
		switch (this.getIdSituacao()) {
		case 1: descricaoSituacao = "Cursou";
		break;
		case 2: descricaoSituacao = "Cursando";
		break;
		case 3: descricaoSituacao = "Trancado/Interrompido";
		}
		return descricaoSituacao;
	}
	public void setDescricaoSituacao(String descricaoSituacao) {
		this.descricaoSituacao = descricaoSituacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Override
	public int compareTo(FormacaoCurriculoDTO o) {
		return idTipoFormacao.compareTo(o.getIdTipoFormacao());
		
	}
	
}
