package br.com.centralit.citcorpore.bean;

/**
 * @author breno.guimaraes Entidade de relacionamento entre as tabelas
 *         JustificacaoFalhas, Evento e HistoricoTentativas.
 */
public class JustificacaoEventoHistoricoDTO {
    private String ip;
    private Integer idItemConfiguracao;
    private Integer idBaseItemConfiguracao;
    private String identificacaoItemConfiguracao;
    private String descricaoTentativa;
    private Integer idEvento;
    private String nomeGrupo;
    private String nomeUnidade;
    private Integer idEmpregado;
    private Integer idHistoricoTentativa;

    public String getIp() {
	return ip;
    }

    public void setIp(String ip) {
	this.ip = ip;
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

    public String getIdentificacaoItemConfiguracao() {
	return identificacaoItemConfiguracao;
    }

    public void setIdentificacaoItemConfiguracao(String identificacaoItemConfiguracao) {
	this.identificacaoItemConfiguracao = identificacaoItemConfiguracao;
    }

    public String getDescricaoTentativa() {
	return descricaoTentativa;
    }

    public void setDescricaoTentativa(String descricaoTentativa) {
	this.descricaoTentativa = descricaoTentativa;
    }

    public Integer getIdEvento() {
	return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
	this.idEvento = idEvento;
    }

    public String getNomeGrupo() {
	return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
	this.nomeGrupo = nomeGrupo;
    }

    public String getNomeUnidade() {
	return nomeUnidade;
    }

    public void setNomeUnidade(String nomeUnidade) {
	this.nomeUnidade = nomeUnidade;
    }

    public Integer getIdEmpregado() {
	return idEmpregado;
    }

    public void setIdEmpregado(Integer idEmpregado) {
	this.idEmpregado = idEmpregado;
    }

    public Integer getIdHistoricoTentativa() {
	return idHistoricoTentativa;
    }

    public void setIdHistoricoTentativa(Integer idHistoricoTentativa) {
	this.idHistoricoTentativa = idHistoricoTentativa;
    }

}
