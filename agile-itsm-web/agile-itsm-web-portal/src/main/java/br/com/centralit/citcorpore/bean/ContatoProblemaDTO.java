package br.com.centralit.citcorpore.bean;

import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.dto.IDto;

public class ContatoProblemaDTO implements IDto{
	/**
	 * @author geber.costa
	 */
	private static final long serialVersionUID = 4531312747440713206L;
	private Integer idContatoProblema;
    private String nomeContato;
    private String telefoneContato;
    private String emailContato;
    private String observacao;
    private Integer idLocalidade;
    private String ramal;
    
    /**
     * @return the nomecontato
     */
    public String getNomecontato() {
        return Util.tratarAspasSimples(nomeContato );
    }
    /**
     * @param nomecontato the nomecontato to set
     */
    public void setNomecontato(String nomecontato) {
        this.nomeContato = nomecontato;
    }
    /**
     * @return the telefonecontato
     */
    public String getTelefonecontato() {
        	if(telefoneContato == null)
        		telefoneContato = " ";     	
        return telefoneContato;
    }
    /**
     * @param telefonecontato the telefonecontato to set
     */
    public void setTelefonecontato(String telefonecontato) {
        this.telefoneContato = telefonecontato;
    }
    /**
     * @return the emailcontato
     */
    public String getEmailcontato() {
    	if(emailContato == null)
    		emailContato = " ";    	
        return Util.tratarAspasSimples(this.emailContato);
    }
    /**
     * @param emailcontato the emailcontato to set
     */
    public void setEmailcontato(String emailcontato) {
        this.emailContato = emailcontato;
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
	public Integer getIdContatoProblema() {
		return idContatoProblema;
	}
	public void setIdContatoProblema(Integer idContatoProblema) {
		this.idContatoProblema = idContatoProblema;
	}
    
}
