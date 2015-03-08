package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.dto.IDto;

/**
 * @author breno.guimaraes
 * 
 */
public class JustificativaParecerDTO implements IDto {

    /**
     * 
     */
    private static final long serialVersionUID = -3055161845325828805L;

    private Integer idJustificativa;
    private String descricaoJustificativa;
    private String aplicavelRequisicao;
    private String aplicavelCotacao;
    private String aplicavelInspecao;
    private String situacao;
	private Date dataInicio;
	private Date dataFim;

    public Integer getIdJustificativa() {
		return idJustificativa;
	}
	public void setIdJustificativa(Integer idJustificativa) {
		this.idJustificativa = idJustificativa;
	}
	public String getDescricaoJustificativa() {
		return Util.tratarAspasSimples(this.descricaoJustificativa);
	}
	public void setDescricaoJustificativa(String descricaoJustificativa) {
		this.descricaoJustificativa = descricaoJustificativa;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
    public String getAplicavelRequisicao() {
        return aplicavelRequisicao;
    }
    public void setAplicavelRequisicao(String aplicavelRequisicao) {
        this.aplicavelRequisicao = aplicavelRequisicao;
    }
    public String getAplicavelCotacao() {
        return aplicavelCotacao;
    }
    public void setAplicavelCotacao(String aplicavelCotacao) {
        this.aplicavelCotacao = aplicavelCotacao;
    }
    public String getAplicavelInspecao() {
        return aplicavelInspecao;
    }
    public void setAplicavelInspecao(String aplicavelInspecao) {
        this.aplicavelInspecao = aplicavelInspecao;
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
}
