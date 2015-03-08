package br.com.centralit.citgerencial.util;

import br.com.citframework.dto.IDto;

public class UploadFileDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4210606336617625912L;
	private String arquivo;
	private String caminhoRelativoArquivo;
	private String caminhoRealArquivo;
	public String getArquivo() {
		return arquivo;
	}
	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}
	public String getCaminhoRealArquivo() {
		return caminhoRealArquivo;
	}
	public void setCaminhoRealArquivo(String caminhoRealArquivo) {
		this.caminhoRealArquivo = caminhoRealArquivo;
	}
	public String getCaminhoRelativoArquivo() {
		return caminhoRelativoArquivo;
	}
	public void setCaminhoRelativoArquivo(String caminhoRelativoArquivo) {
		this.caminhoRelativoArquivo = caminhoRelativoArquivo;
	}
}
