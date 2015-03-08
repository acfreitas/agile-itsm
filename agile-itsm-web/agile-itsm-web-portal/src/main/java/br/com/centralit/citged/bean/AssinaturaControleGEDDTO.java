package br.com.centralit.citged.bean;

import br.com.citframework.dto.IDto;


public class AssinaturaControleGEDDTO implements IDto{
    private static final long serialVersionUID = 1L;
    
	private Integer idAssinatura;
	private Integer idControleGED;
	private String pathAssinatura;
	
	private String pastaControleGed;
	
	public Integer getIdAssinatura() {
        return idAssinatura;
    }
    public void setIdAssinatura(Integer idAssinatura) {
        this.idAssinatura = idAssinatura;
    }
    
	public Integer getIdControleGED() {
		return idControleGED;
	}
	public void setIdControleGED(Integer idControleGED) {
		this.idControleGED = idControleGED;
	}
	
	public String getPathAssinatura(){ return pathAssinatura; }
	public void setPathAssinatura(String pathAssinatura)
	{
	    this.pathAssinatura = pathAssinatura;
	}
	
    public String getPastaControleGed()
    {
        if(pastaControleGed == null) pastaControleGed = "";
        return pastaControleGed;
    }
    public void setPastaControleGed(String pastaControleGed)
    {
        this.pastaControleGed = pastaControleGed;
    }
	
}