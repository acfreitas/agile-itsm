/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author David.Lopes
 * 
 */
public class AnexoMudancaDTO implements IDto {

    private static final long serialVersionUID = 808193377991557947L;

    private Integer idAnexoMudanca;

    private Integer idMudanca;

    private Date dataInicio;

    private Date dataFim;

    private String nomeAnexo;

    private String descricao;

    private String link;

    private String extensao;

    private String textoDocumento;

    public Integer getIdAnexoMudanca() {
	return idAnexoMudanca;
    }

    public void setIdAnexoMudanca(Integer idAnexoMudanca) {
	this.idAnexoMudanca = idAnexoMudanca;
    }

    public Integer getIdMudanca() {
	return idMudanca;
    }

    public void setIdMudanca(Integer idMudanca) {
	this.idMudanca = idMudanca;
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

    public String getNomeAnexo() {
	return nomeAnexo;
    }

    public void setNomeAnexo(String nomeAnexo) {
	this.nomeAnexo = nomeAnexo;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public String getLink() {
	return link;
    }

    public void setLink(String link) {
	this.link = link;
    }

    public String getExtensao() {
	return extensao;
    }

    public void setExtensao(String extensao) {
	this.extensao = extensao;
    }

    public String getTextoDocumento() {
	return textoDocumento;
    }

    public void setTextoDocumento(String textoDocumento) {
	this.textoDocumento = textoDocumento;
    }

}
