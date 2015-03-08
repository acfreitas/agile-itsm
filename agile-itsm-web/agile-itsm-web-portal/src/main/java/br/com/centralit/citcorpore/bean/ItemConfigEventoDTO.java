package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * Item de Execução
 * 
 */
public class ItemConfigEventoDTO implements IDto {

    private static final long serialVersionUID = 1L;

    // Itens na tabela
    private Integer idItemConfiguracaoEvento;
    private Integer idItemConfiguracao;
    private Integer idBaseItemConfiguracao;
    private Integer idEvento;
    private String tipoExecucao;
    private String gerarQuando;
    private Date data;
    private String hora;
    private String linhaComando;
    private String linhaComandoLinux;

    // Outros Itens
    private String identificacao;
    private String nomeBaseItemConfiguracao;

    public Integer getIdItemConfiguracaoEvento() {
	return idItemConfiguracaoEvento;
    }

    public void setIdItemConfiguracaoEvento(Integer idItemConfiguracaoEvento) {
	this.idItemConfiguracaoEvento = idItemConfiguracaoEvento;
    }

    public Integer getIdItemConfiguracao() {
	return idItemConfiguracao;
    }

    public void setIdItemConfiguracao(Integer idItemConfiguracao) {
	this.idItemConfiguracao = idItemConfiguracao;
    }

    public String getIdentificacao() {
	return identificacao;
    }

    public void setIdentificacao(String identificacao) {
	this.identificacao = identificacao;
    }

    public Integer getIdEvento() {
	return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
	this.idEvento = idEvento;
    }

    public String getTipoExecucao() {
	return tipoExecucao;
    }

    public void setTipoExecucao(String tipoExecucao) {
	this.tipoExecucao = tipoExecucao;
    }

    public String getGerarQuando() {
	return gerarQuando;
    }

    public void setGerarQuando(String gerarQuando) {
	this.gerarQuando = gerarQuando;
    }

    public Date getData() {
	return data;
    }

    public void setData(Date data) {
	this.data = data;
    }

    public String getHora() {
	return hora;
    }

    public void setHora(String hora) {
	this.hora = hora;
    }

    public String getLinhaComando() {
	return linhaComando;
    }

    public void setLinhaComando(String linhaComando) {
	this.linhaComando = linhaComando;
    }

    public Integer getIdBaseItemConfiguracao() {
	return idBaseItemConfiguracao;
    }

    public void setIdBaseItemConfiguracao(Integer idBaseItemConfiguracao) {
	this.idBaseItemConfiguracao = idBaseItemConfiguracao;
    }

    public String getNomeBaseItemConfiguracao() {
	return nomeBaseItemConfiguracao;
    }

    public void setNomeBaseItemConfiguracao(String nomeBaseItemConfiguracao) {
	this.nomeBaseItemConfiguracao = nomeBaseItemConfiguracao;
    }

	/**
	 * @return the linhaComandoLinux
	 */
	public String getLinhaComandoLinux() {
		return linhaComandoLinux;
	}

	/**
	 * @param linhaComandoLinux the linhaComandoLinux to set
	 */
	public void setLinhaComandoLinux(String linhaComandoLinux) {
		this.linhaComandoLinux = linhaComandoLinux;
	}

}
