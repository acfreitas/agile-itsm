package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.List;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class EventoItemConfigDTO implements IDto {
    private Integer idEvento;
    private Integer idEmpresa;
    private String descricao;
    private String ligarCasoDesl;
    private String usuario;
    private String senha;
    private Date dataInicio;
    private Date dataFim;
    /**
     * Lista dos itens de Execução!
     */
    private List<ItemConfigEventoDTO> lstItemConfigEvento;
    
    private List<EventoGrupoDTO> lstGrupo;
    private List<EventoItemConfigRelDTO> lstItemConfiguracao;
    
    private Integer idItemCfg;
    private String  nomeItemCfg;
    private String linhaComando;
    
 
    /**
     * @return the linhaComando
     */
    public String getLinhaComando() {
        return linhaComando;
    }

    /**
     * @param linhaComando the linhaComando to set
     */
    public void setLinhaComando(String linhaComando) {
        this.linhaComando = linhaComando;
    }

    /**
     * @return the idItemCfg
     */
    public Integer getIdItemCfg() {
        return idItemCfg;
    }

    /**
     * @param idItemCfg the idItemCfg to set
     */
    public void setIdItemCfg(Integer idItemCfg) {
        this.idItemCfg = idItemCfg;
    }

    /**
     * @return the nomeItemCfg
     */
    public String getNomeItemCfg() {
        return nomeItemCfg;
    }

    /**
     * @param nomeItemCfg the nomeItemCfg to set
     */
    public void setNomeItemCfg(String nomeItemCfg) {
        this.nomeItemCfg = nomeItemCfg;
    }

    public Integer getIdEvento() {
	return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
	this.idEvento = idEvento;
    }

    public Integer getIdEmpresa() {
	return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
	this.idEmpresa = idEmpresa;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public String getLigarCasoDesl() {
	return ligarCasoDesl;
    }

    public void setLigarCasoDesl(String ligarCasoDesl) {
	this.ligarCasoDesl = ligarCasoDesl;
    }

    public String getUsuario() {
	return usuario;
    }

    public void setUsuario(String usuario) {
	this.usuario = usuario;
    }

    public String getSenha() {
	return senha;
    }

    public void setSenha(String senha) {
	this.senha = senha;
    }

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

    public List<ItemConfigEventoDTO> getLstItemConfigEvento() {
	return lstItemConfigEvento;
    }

    public void setLstItemConfigEvento(List<ItemConfigEventoDTO> lstItemConfigEvento) {
	this.lstItemConfigEvento = lstItemConfigEvento;
    }


	/**
	 * @return the lstGrupo
	 */
	public List<EventoGrupoDTO> getLstGrupo() {
		return lstGrupo;
	}

	/**
	 * @param lstGrupo the lstGrupo to set
	 */
	public void setLstGrupo(List<EventoGrupoDTO> lstGrupo) {
		this.lstGrupo = lstGrupo;
	}

	/**
	 * @return the lstItemConfiguracao
	 */
	public List<EventoItemConfigRelDTO> getLstItemConfiguracao() {
		return lstItemConfiguracao;
	}

	/**
	 * @param lstItemConfiguracao the lstItemConfiguracao to set
	 */
	public void setLstItemConfiguracao(
			List<EventoItemConfigRelDTO> lstItemConfiguracao) {
		this.lstItemConfiguracao = lstItemConfiguracao;
	}

}
