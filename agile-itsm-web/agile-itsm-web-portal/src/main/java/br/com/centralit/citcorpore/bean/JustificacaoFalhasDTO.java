package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class JustificacaoFalhasDTO implements IDto {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // Variáveis do banco
    private Integer idHistoricoTentativa;
    private Integer idJustificacaoFalha;
    private Integer idItemConfiguracao;
    private Integer idBaseItemConfiguracao;
    private Integer idEvento;
    private Integer idEmpregado;
    private String descricao;
    private Date data;
    private String hora;

    // Variáveis de controle específico da aplicação
    private String listaItensSerializado;
    private Integer idGrupo;
    private Integer idUnidade;
    private Date dataInicial;
    private Date dataFinal;

    public Integer getIdJustificacaoFalha() {
	return idJustificacaoFalha;
    }

    public void setIdJustificacaoFalha(Integer idJustificacaoFalha) {
	this.idJustificacaoFalha = idJustificacaoFalha;
    }

    public Integer getIdItemConfiguracao() {
	return idItemConfiguracao;
    }

    public void setIdItemConfiguracao(Integer idItemConfiguracao) {
	this.idItemConfiguracao = idItemConfiguracao;
    }

    public Integer getIdBaseItemConfiguracao() {
	return idBaseItemConfiguracao;
    }

    public void setIdBaseItemConfiguracao(Integer idBaseItemConfiguracao) {
	this.idBaseItemConfiguracao = idBaseItemConfiguracao;
    }

    public Integer getIdEvento() {
	return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
	this.idEvento = idEvento;
    }

    public Integer getIdEmpregado() {
	return idEmpregado;
    }

    public void setIdEmpregado(Integer idEmpregado) {
	this.idEmpregado = idEmpregado;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
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

    public String getListaItensSerializado() {
	return listaItensSerializado;
    }

    public void setListaItensSerializado(String listaItensSerializado) {
	this.listaItensSerializado = listaItensSerializado;
    }

    public Integer getIdGrupo() {
	return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
	this.idGrupo = idGrupo;
    }

    public Integer getIdUnidade() {
	return idUnidade;
    }

    public void setIdUnidade(Integer idUnidade) {
	this.idUnidade = idUnidade;
    }

    public Date getDataInicial() {
	return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
	this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
	return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
	this.dataFinal = dataFinal;
    }

    public Integer getIdHistoricoTentativa() {
	return idHistoricoTentativa;
    }

    public void setIdHistoricoTentativa(Integer idHistoricoTentativa) {
	this.idHistoricoTentativa = idHistoricoTentativa;
    }

}
