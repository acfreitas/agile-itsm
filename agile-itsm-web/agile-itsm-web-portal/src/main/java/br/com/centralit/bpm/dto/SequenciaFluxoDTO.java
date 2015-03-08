package br.com.centralit.bpm.dto;

import br.com.citframework.dto.IDto;

public class SequenciaFluxoDTO implements IDto {

    private static final long serialVersionUID = 8226164140444325947L;

    private Integer idFluxo;
    private Integer idElementoOrigem;
    private Integer idElementoDestino;
    private Integer idConexaoOrigem;
    private Integer idConexaoDestino;
    private String nome;
    private String condicao;

    private Integer idElemento;
    private String idDesenhoOrigem;
    private String idDesenhoDestino;
    private Double bordaX;
    private Double bordaY;
    private String posicaoAlterada;

    public Integer getIdFluxo() {
        return idFluxo;
    }

    public void setIdFluxo(final Integer idFluxo) {
        this.idFluxo = idFluxo;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(final String condicao) {
        this.condicao = condicao;
    }

    public Integer getIdElementoOrigem() {
        return idElementoOrigem;
    }

    public void setIdElementoOrigem(final Integer idElementoOrigem) {
        this.idElementoOrigem = idElementoOrigem;
    }

    public Integer getIdElementoDestino() {
        return idElementoDestino;
    }

    public void setIdElementoDestino(final Integer idElementoDestino) {
        this.idElementoDestino = idElementoDestino;
    }

    public String getIdDesenhoOrigem() {
        return idDesenhoOrigem;
    }

    public void setIdDesenhoOrigem(final String idDesenhoOrigem) {
        this.idDesenhoOrigem = idDesenhoOrigem;
    }

    public String getIdDesenhoDestino() {
        return idDesenhoDestino;
    }

    public void setIdDesenhoDestino(final String idDesenhoDestino) {
        this.idDesenhoDestino = idDesenhoDestino;
    }

    public Integer getIdConexaoOrigem() {
        return idConexaoOrigem;
    }

    public void setIdConexaoOrigem(final Integer idConexaoOrigem) {
        this.idConexaoOrigem = idConexaoOrigem;
    }

    public Integer getIdConexaoDestino() {
        return idConexaoDestino;
    }

    public void setIdConexaoDestino(final Integer idConexaoDestino) {
        this.idConexaoDestino = idConexaoDestino;
    }

    public Integer getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(final Integer idElemento) {
        this.idElemento = idElemento;
    }

    public Double getBordaX() {
        return bordaX;
    }

    public void setBordaX(final Double bordaX) {
        this.bordaX = bordaX;
    }

    public Double getBordaY() {
        return bordaY;
    }

    public void setBordaY(final Double bordaY) {
        this.bordaY = bordaY;
    }

    public String getPosicaoAlterada() {
        return posicaoAlterada;
    }

    public void setPosicaoAlterada(final String posicaoAlterada) {
        this.posicaoAlterada = posicaoAlterada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

}
