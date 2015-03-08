package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

/**
 * @author ygor.magalhaes
 *
 */
public class HistoricoAcaoCurriculoDTO implements IDto {

	private Integer idHistoricoAcaoCurriculo;
	private Integer idCurriculo;
	private Integer idJustificativaAcaoCurriculo;
	private Integer idUsuario;
	private String complementoJustificativa;
	private Timestamp dataHora;
	private String acao;
	
	private String tela;
	private String indiceTriagem;
	
	public Integer getIdHistoricoAcaoCurriculo() {
		return idHistoricoAcaoCurriculo;
	}
	public void setIdHistoricoAcaoCurriculo(Integer idHistoricoAcaoCurriculo) {
		this.idHistoricoAcaoCurriculo = idHistoricoAcaoCurriculo;
	}
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	public Integer getIdJustificativaAcaoCurriculo() {
		return idJustificativaAcaoCurriculo;
	}
	public void setIdJustificativaAcaoCurriculo(Integer idJustificativaAcaoCurriculo) {
		this.idJustificativaAcaoCurriculo = idJustificativaAcaoCurriculo;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getComplementoJustificativa() {
		return complementoJustificativa;
	}
	public void setComplementoJustificativa(String complementoJustificativa) {
		this.complementoJustificativa = complementoJustificativa;
	}
	public Timestamp getDataHora() {
		return dataHora;
	}
	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getTela() {
		return tela;
	}
	public void setTela(String tela) {
		this.tela = tela;
	}
	public String getIndiceTriagem() {
		return indiceTriagem;
	}
	public void setIndiceTriagem(String indiceTriagem) {
		this.indiceTriagem = indiceTriagem;
	}

}