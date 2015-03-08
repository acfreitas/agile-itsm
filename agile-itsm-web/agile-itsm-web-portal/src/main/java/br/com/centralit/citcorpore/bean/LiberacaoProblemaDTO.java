package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class LiberacaoProblemaDTO implements IDto {
	private Integer idLiberacao;
	private Integer idProblema;
	private String status;
	
	private String titulo;
	private Integer idHistoricoLiberacao;
	
	public Integer getIdLiberacao() {
		return idLiberacao;
	}

	public void setIdLiberacao(Integer idLiberacao) {
		this.idLiberacao = idLiberacao;
	}

	public String getTitulo() {
        return titulo;
    }
	
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
	public Integer getIdProblema() {
		return idProblema;
	}
	
	public void setIdProblema(Integer idProblema) {
		this.idProblema = idProblema;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIdHistoricoLiberacao() {
		return idHistoricoLiberacao;
	}

	public void setIdHistoricoLiberacao(Integer idHistoricoLiberacao) {
		this.idHistoricoLiberacao = idHistoricoLiberacao;
	}
	
	
    
}
