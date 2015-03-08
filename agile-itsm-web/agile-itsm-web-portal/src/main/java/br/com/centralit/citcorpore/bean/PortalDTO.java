package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class PortalDTO implements IDto{

    /**
     *
     */
    private static final long serialVersionUID = 638687400065001805L;
    private Integer idPortal;
    private Integer idItem;
    private Double posicaoX;
    private Double posicaoY;
    private Integer idUsuario;
    private Double largura;
    private Double altura;
    private Date data;
    /*private Timestamp hora;*/
    private Integer idServico;
    private String nomeServico;
    private Integer coluna;
    private Integer idPost;
    private Integer idCatalogoServico;
    private String filtroCatalogo;
    private Double valorTotalServico;
    private Integer idContratoUsuario;
    private String observacaoPortal;
    private String finalizaCompra;
    private String anexarArquivos;

    public Integer getIdItem() {
        return idItem;
    }
    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }
    public Double getPosicaoX() {
        return posicaoX;
    }
    public void setPosicaoX(Double posicaoX) {
        this.posicaoX = posicaoX;
    }
    public Double getPosicaoY() {
        return posicaoY;
    }
    public void setPosicaoY(Double posicaoY) {
        this.posicaoY = posicaoY;
    }
    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Double getLargura() {
        return largura;
    }
    public void setLargura(Double largura) {
        this.largura = largura;
    }
    public Double getAltura() {
        return altura;
    }
    public void setAltura(Double altura) {
        this.altura = altura;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    /*public Timestamp getHora() {
		return hora;
	}
	public void setHora(Timestamp hora) {
		this.hora = hora;
	}*/
    public Integer getIdPortal() {
        return idPortal;
    }
    public void setIdPortal(Integer idPortal) {
        this.idPortal = idPortal;
    }
    public Integer getIdServico() {
        return idServico;
    }
    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }
    public String getNomeServico() {
        return nomeServico;
    }
    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }
    public Integer getColuna() {
        return coluna;
    }
    public void setColuna(Integer coluna) {
        this.coluna = coluna;
    }
    public Integer getIdPost() {
        return idPost;
    }
    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }
    public Integer getIdCatalogoServico() {
        return idCatalogoServico;
    }
    public void setIdCatalogoServico(Integer idCatalogoServico) {
        this.idCatalogoServico = idCatalogoServico;
    }
    public String getFiltroCatalogo() {
        return filtroCatalogo;
    }
    public void setFiltroCatalogo(String filtroCatalogo) {
        this.filtroCatalogo = filtroCatalogo;
    }
    public Double getValorTotalServico() {
        return valorTotalServico;
    }
    public void setValorTotalServico(Double valorTotalServico) {
        this.valorTotalServico = valorTotalServico;
    }
    public Integer getIdContratoUsuario() {
        return idContratoUsuario;
    }
    public void setIdContratoUsuario(Integer idContratoUsuario) {
        this.idContratoUsuario = idContratoUsuario;
    }
    public String getObservacaoPortal() {
        return observacaoPortal;
    }
    public void setObservacaoPortal(String observacaoPortal) {
        this.observacaoPortal = observacaoPortal;
    }
    public String getFinalizaCompra() {
        return finalizaCompra;
    }
    public void setFinalizaCompra(String finalizaCompra) {
        this.finalizaCompra = finalizaCompra;
    }
	public String getAnexarArquivos() {
		return anexarArquivos;
	}
	public void setAnexarArquivos(String anexarArquivos) {
		this.anexarArquivos = anexarArquivos;
	}

    
    
}
