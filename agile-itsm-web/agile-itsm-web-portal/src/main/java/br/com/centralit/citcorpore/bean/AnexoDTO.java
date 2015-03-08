package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class AnexoDTO implements IDto {

	private static final long serialVersionUID = -7252057961936714136L;
	private Integer idAnexo;
	private String nome;
	private String descricao;
	private String extensao;
	private String path;
    private String link;
	private String temporario;
	private Integer idExecucaoAtividade;
	
    public Integer getIdAnexo() {
        return idAnexo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getExtensao() {
        return extensao;
    }
    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getTemporario() {
        return temporario;
    }
    public void setTemporario(String temporario) {
        this.temporario = temporario;
    }
    public Integer getIdExecucaoAtividade() {
        return idExecucaoAtividade;
    }
    public void setIdExecucaoAtividade(Integer idExecucaoAtividade) {
        this.idExecucaoAtividade = idExecucaoAtividade;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setIdAnexo(Integer idAnexo) {
        this.idAnexo = idAnexo;
    }

}