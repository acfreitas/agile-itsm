package br.com.centralit.citcorpore.bean;

import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class ContatoSolicitacaoServicoDTO implements IDto {
    
    
    private Integer idcontatosolicitacaoservico;
    private String nomecontato;
    private String telefonecontato;
    private String emailcontato;
    private String observacao;
    private Integer idLocalidade;
    private String ramal;
    
    
    
    
    /**
     * @return the idcontatosolicitacaoservico
     */
    public Integer getIdcontatosolicitacaoservico() {
        return idcontatosolicitacaoservico;
    }
    /**
     * @param idcontatosolicitacaoservico the idcontatosolicitacaoservico to set
     */
    public void setIdcontatosolicitacaoservico(Integer idcontatosolicitacaoservico) {
        this.idcontatosolicitacaoservico = idcontatosolicitacaoservico;
    }
    /**
     * @return the nomecontato
     */
    public String getNomecontato() {
        return Util.tratarAspasSimples(nomecontato );
    }
    /**
     * @param nomecontato the nomecontato to set
     */
    public void setNomecontato(String nomecontato) {
        this.nomecontato = nomecontato;
    }
    /**
     * @return the telefonecontato
     */
    public String getTelefonecontato() {
        	if(telefonecontato == null)
        		telefonecontato = " ";     	
        return telefonecontato;
    }
    /**
     * @param telefonecontato the telefonecontato to set
     */
    public void setTelefonecontato(String telefonecontato) {
        this.telefonecontato = telefonecontato;
    }
    /**
     * @return the emailcontato
     */
    public String getEmailcontato() {
    	if(emailcontato == null)
    		emailcontato = " ";    	
        return Util.tratarAspasSimples(this.emailcontato);
    }
    /**
     * @param emailcontato the emailcontato to set
     */
    public void setEmailcontato(String emailcontato) {
        this.emailcontato = emailcontato;
    }
    /**
     * @return the localizacaofisica
     */
    public String getObservacao() {
    	if(observacao == null)
    		observacao = " ";
        return observacao;
    }
    /**
     * @param observacao the localizacaofisica to set
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    public String getDadosStr() {
		StringBuilder str = new StringBuilder();
		if (getNomecontato() != null) {
			str.append("Nome: "+getNomecontato() + "\n");
			if (getTelefonecontato() != null)
				str.append("Telefone: "+getTelefonecontato() + "\n");
			if (getEmailcontato() != null)
				str.append("Email: "+getEmailcontato() + "\n");
			if (getObservacao() != null && getObservacao().length() > 0)
				str.append("Observação: "+getObservacao());
		}
		return str.toString();
    }
	/**
	 * @return the idLocalidade
	 */
	public Integer getIdLocalidade() {
		return idLocalidade;
	}
	/**
	 * @param idLocalidade the idLocalidade to set
	 */
	public void setIdLocalidade(Integer idLocalidade) {
		this.idLocalidade = idLocalidade;
	}
	/**
	 * @return the ramal
	 */
	public String getRamal() {
		return ramal;
	}
	/**
	 * @param ramal the ramal to set
	 */
	public void setRamal(String ramal) {
		this.ramal = ramal;
	}
    
    
    
}
