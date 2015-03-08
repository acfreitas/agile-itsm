package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author mario.haysaki
 *
 */
public class RelatorioListaNegraDTO implements IDto {
	
	private Integer idItemConfiguracao;
	private Integer idGrupoItemConfiguracao;
	private String nomeGrupoItemConfiguracao;
	private Integer idSoftwaresListaNegra;
	private String nomeSoftwaresListaNegra;
	private String descricao;
	private Date dataInicio;
	private Date dataFim;
	private String formatoArquivoRelatorio;
	private String localidade;
	
	public Integer getIdItemConfiguracao() {
		return idItemConfiguracao;
	}
	public void setIdItemConfiguracao(Integer idItemConfiguracao) {
		this.idItemConfiguracao = idItemConfiguracao;
	}
	public Integer getIdGrupoItemConfiguracao() {
		return idGrupoItemConfiguracao;
	}
	public void setIdGrupoItemConfiguracao(Integer idGrupoItemConfiguracao) {
		this.idGrupoItemConfiguracao = idGrupoItemConfiguracao;
	}
	public String getNomeGrupoItemConfiguracao() {
		return nomeGrupoItemConfiguracao;
	}
	public void setNomeGrupoItemConfiguracao(String nomeGrupoItemConfiguracao) {
		this.nomeGrupoItemConfiguracao = nomeGrupoItemConfiguracao;
	}
	public Integer getIdSoftwaresListaNegra() {
		return idSoftwaresListaNegra;
	}
	public void setIdSoftwaresListaNegra(Integer idSoftwaresListaNegra) {
		this.idSoftwaresListaNegra = idSoftwaresListaNegra;
	}
	public String getNomeSoftwaresListaNegra() {
		return nomeSoftwaresListaNegra;
	}
	public void setNomeSoftwaresListaNegra(String nomeSoftwaresListaNegra) {
		this.nomeSoftwaresListaNegra = nomeSoftwaresListaNegra;
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getFormatoArquivoRelatorio() {
		return formatoArquivoRelatorio;
	}
	public void setFormatoArquivoRelatorio(String formatoArquivoRelatorio) {
		this.formatoArquivoRelatorio = formatoArquivoRelatorio;
	}
	public String getLocalidade() {
		return localidade;
	}
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	
}
