package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;


public class TarefaUsuarioDTO extends SolicitacaoServicoDTO {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -250482372797357031L;
	
	private Integer idElementoFluxo;
    private Integer idResponsavelAtual;
    private Timestamp dataHoraCriacaoTarefa;
    private Timestamp dataHoraInicioTarefa;
    private Timestamp dataHoraFinalizacaoTarefa;
    private Timestamp dataHoraExecucaoTarefa;
    private String situacaoTarefa;
    private Integer idTipoFluxo;
    private String tipoAtribuicao;
    
	/**
	 * @return the idElementoFluxo
	 */
	public Integer getIdElementoFluxo() {
		return idElementoFluxo;
	}
	/**
	 * @param idElementoFluxo the idElementoFluxo to set
	 */
	public void setIdElementoFluxo(Integer idElementoFluxo) {
		this.idElementoFluxo = idElementoFluxo;
	}
	/**
	 * @return the idResponsavelAtual
	 */
	public Integer getIdResponsavelAtual() {
		return idResponsavelAtual;
	}
	/**
	 * @param idResponsavelAtual the idResponsavelAtual to set
	 */
	public void setIdResponsavelAtual(Integer idResponsavelAtual) {
		this.idResponsavelAtual = idResponsavelAtual;
	}
	/**
	 * @return the dataHoraCriacaoTarefa
	 */
	public Timestamp getDataHoraCriacaoTarefa() {
		return dataHoraCriacaoTarefa;
	}
	/**
	 * @param dataHoraCriacaoTarefa the dataHoraCriacaoTarefa to set
	 */
	public void setDataHoraCriacaoTarefa(Timestamp dataHoraCriacaoTarefa) {
		this.dataHoraCriacaoTarefa = dataHoraCriacaoTarefa;
	}
	/**
	 * @return the dataHoraInicioTarefa
	 */
	public Timestamp getDataHoraInicioTarefa() {
		return dataHoraInicioTarefa;
	}
	/**
	 * @param dataHoraInicioTarefa the dataHoraInicioTarefa to set
	 */
	public void setDataHoraInicioTarefa(Timestamp dataHoraInicioTarefa) {
		this.dataHoraInicioTarefa = dataHoraInicioTarefa;
	}
	/**
	 * @return the dataHoraFinalizacaoTarefa
	 */
	public Timestamp getDataHoraFinalizacaoTarefa() {
		return dataHoraFinalizacaoTarefa;
	}
	/**
	 * @param dataHoraFinalizacaoTarefa the dataHoraFinalizacaoTarefa to set
	 */
	public void setDataHoraFinalizacaoTarefa(Timestamp dataHoraFinalizacaoTarefa) {
		this.dataHoraFinalizacaoTarefa = dataHoraFinalizacaoTarefa;
	}
	/**
	 * @return the dataHoraExecucaoTarefa
	 */
	public Timestamp getDataHoraExecucaoTarefa() {
		return dataHoraExecucaoTarefa;
	}
	/**
	 * @param dataHoraExecucaoTarefa the dataHoraExecucaoTarefa to set
	 */
	public void setDataHoraExecucaoTarefa(Timestamp dataHoraExecucaoTarefa) {
		this.dataHoraExecucaoTarefa = dataHoraExecucaoTarefa;
	}
	/**
	 * @return the situacaoTarefa
	 */
	public String getSituacaoTarefa() {
		return situacaoTarefa;
	}
	/**
	 * @param situacaoTarefa the situacaoTarefa to set
	 */
	public void setSituacaoTarefa(String situacaoTarefa) {
		this.situacaoTarefa = situacaoTarefa;
	}
	/**
	 * @return the idTipoFluxo
	 */
	public Integer getIdTipoFluxo() {
		return idTipoFluxo;
	}
	/**
	 * @param idTipoFluxo the idTipoFluxo to set
	 */
	public void setIdTipoFluxo(Integer idTipoFluxo) {
		this.idTipoFluxo = idTipoFluxo;
	}
	/**
	 * @return the tipoAtribuicao
	 */
	public String getTipoAtribuicao() {
		return tipoAtribuicao;
	}
	/**
	 * @param tipoAtribuicao the tipoAtribuicao to set
	 */
	public void setTipoAtribuicao(String tipoAtribuicao) {
		this.tipoAtribuicao = tipoAtribuicao;
	}
	
	public TarefaFluxoDTO converteTarefaFluxoDto() {
		this.setPossuiFilho(this.getQtdefilhas() != null && this.getQtdefilhas() > 0);
		this.setDataHoraLimiteStr(this.getDataHoraLimiteStr());
		this.setDataHoraInicioSLA(this.getDataHoraInicioSLA());
		this.setNomeServico(this.getServico());

		if (this.getNomeUnidadeSolicitante() != null && !this.getNomeUnidadeSolicitante().trim().equalsIgnoreCase(""))
			this.setSolicitanteUnidade(this.getSolicitante() + " (" + this.getNomeUnidadeSolicitante() + ")");

		if (this.getNomeUnidadeResponsavel() != null && !this.getNomeUnidadeResponsavel().trim().equalsIgnoreCase(""))
			this.setResponsavel(this.getResponsavel() + " (" + this.getNomeUnidadeResponsavel() + ")");

		TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
		tarefaFluxoDto.setIdItemTrabalho(this.getIdTarefa());
		tarefaFluxoDto.setIdInstancia(this.getIdInstanciaFluxo());
		tarefaFluxoDto.setIdElemento(this.getIdElementoFluxo());
		tarefaFluxoDto.setIdResponsavelAtual(this.getIdResponsavelAtual());
		tarefaFluxoDto.setDataHoraCriacao(this.getDataHoraCriacaoTarefa());
		tarefaFluxoDto.setDataHoraInicio(this.getDataHoraInicioTarefa());
		tarefaFluxoDto.setDataHoraFinalizacao(this.getDataHoraFinalizacaoTarefa());
		tarefaFluxoDto.setDataHoraExecucao(this.getDataHoraExecucaoTarefa());
		tarefaFluxoDto.setSituacao(this.getSituacaoTarefa());
		tarefaFluxoDto.setTipoAtribuicao(this.getTipoAtribuicao());
		tarefaFluxoDto.setIdFluxo(this.getIdFluxo());
		tarefaFluxoDto.setIdTipoFluxo(this.getIdTipoFluxo());
		tarefaFluxoDto.setSomenteAcompanhamento(this.getTipoAtribuicao().equals(TipoAtribuicao.Acompanhamento.name()));
		tarefaFluxoDto.setResponsavelAtual(this.getNomeUsuarioResponsavelAtual() != null ? this.getNomeUsuarioResponsavelAtual() : "");
		tarefaFluxoDto.setDataHoraLimite(this.getDataHoraLimite());
		
		tarefaFluxoDto.setSolicitacaoDto(this);
		
		return tarefaFluxoDto;
	}
    
}

