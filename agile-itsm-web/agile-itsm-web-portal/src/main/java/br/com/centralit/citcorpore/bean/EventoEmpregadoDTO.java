package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class EventoEmpregadoDTO implements IDto {

    // Atributos relacionados ao banco
    private Integer idEvento;
    private Integer idGrupo;
    private Integer idUnidade;
    private Integer idEmpregado;
    private Integer idItemConfiguracaoPai;

    private String nome;

    public Integer getIdEvento() {
	return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
	this.idEvento = idEvento;
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

    public Integer getIdEmpregado() {
	return idEmpregado;
    }

    public void setIdEmpregado(Integer idEmpregado) {
	this.idEmpregado = idEmpregado;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public Integer getIdItemConfiguracaoPai() {
	return idItemConfiguracaoPai;
    }

    public void setIdItemConfiguracaoPai(Integer idItemConfiguracaoPai) {
	this.idItemConfiguracaoPai = idItemConfiguracaoPai;
    }

}
