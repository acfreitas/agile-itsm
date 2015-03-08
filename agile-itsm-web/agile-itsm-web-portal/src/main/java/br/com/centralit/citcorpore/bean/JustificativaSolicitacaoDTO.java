package br.com.centralit.citcorpore.bean;

import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.dto.IDto;

/**
 * @author breno.guimaraes
 * 
 */
public class JustificativaSolicitacaoDTO implements IDto {

    /**
     * 
     */
    private static final long serialVersionUID = -3055161845325828805L;

    private Integer idJustificativa;
    private String descricaoJustificativa;
    private String suspensao;
    private String situacao;
    private String complementoJustificativa;
    
    private String viagem;

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
	public String getSuspensao() {
		return suspensao;
	}
	public void setSuspensao(String suspensao) {
		this.suspensao = suspensao;
	}
	public String getSituacao() {
		return situacao;
	}
	public String getComplementoJustificativa() {
        return complementoJustificativa;
    }
    public void setComplementoJustificativa(String complementoJustificativa) {
        this.complementoJustificativa = complementoJustificativa;
    }
    public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	/**
	 * @return the viagem
	 */
	public String getViagem() {
		return viagem;
	}
	/**
	 * @param viagem the viagem to set
	 */
	public void setViagem(String viagem) {
		this.viagem = viagem;
	}
    
}
