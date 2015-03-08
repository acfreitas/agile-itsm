package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author mario.haysaki
 *
 */
public class RelatorioGruposUsuarioDTO implements IDto{ 
	
	private static final long serialVersionUID = 1L;
	private Integer idGrupo;
	private String nomeGrupo;
	private Integer idColaborador;
	private String nomeColaborador;
	private String formatoArquivoRelatorio;
	
	public Integer getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}
	public Integer getIdColaborador() {
		return idColaborador;
	}
	public void setIdColaborador(Integer idColaborador) {
		this.idColaborador = idColaborador;
	}
	public String getNomeGrupo() {
		return nomeGrupo;
	}
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
	public String getNomeColaborador() {
		return nomeColaborador;
	}
	public void setNomeColaborador(String nomeColaborador) {
		this.nomeColaborador = nomeColaborador;
	}
	public String getFormatoArquivoRelatorio() {
		return formatoArquivoRelatorio;
	}
	public void setFormatoArquivoRelatorio(String formatoArquivoRelatorio) {
		this.formatoArquivoRelatorio = formatoArquivoRelatorio;
	}

}
