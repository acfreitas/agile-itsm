package br.com.centralit.citcorpore.bean;

import java.util.List;

import br.com.citframework.dto.IDto;

/**
 * @author Maycon.Fernandes
 * 
 */
@SuppressWarnings("serial")
public class InventarioDTO implements IDto {

    private Integer idInventario;
    private String ip;
    private String mask;
    private String mac;
    
    private Integer idContrato;
    @SuppressWarnings("rawtypes")
    private List<NetMapDTO> ipInvetariar;
    private java.sql.Date date;

    /**
     * @return valor do atributo ipInvetariar.
     */
    @SuppressWarnings("rawtypes")
    public List<NetMapDTO> getIpInvetariar() {
	return ipInvetariar;
    }

    /**
     * Define valor do atributo ipInvetariar.
     * 
     * @param ipInvetariar
     */
    @SuppressWarnings("rawtypes")
    public void setIpInvetariar(List<NetMapDTO> ipInvetariar) {
	this.ipInvetariar = ipInvetariar;
    }

    /**
     * @return valor do atributo idInventario.
     */
    public Integer getIdInventario() {
	return idInventario;
    }

    /**
     * Define valor do atributo idInventario.
     * 
     * @param idInventario
     */
    public void setIdInventario(Integer idInventario) {
	this.idInventario = idInventario;
    }

    /**
     * @return valor do atributo ip.
     */
    public String getIp() {
	return ip;
    }

    /**
     * Define valor do atributo ip.
     * 
     * @param ip
     */
    public void setIp(String ip) {
	this.ip = ip;
    }

    /**
     * @return valor do atributo mask.
     */
    public String getMask() {
	return mask;
    }

    /**
     * Define valor do atributo mask.
     * 
     * @param mask
     */
    public void setMask(String mask) {
	this.mask = mask;
    }

    /**
     * @return valor do atributo mac.
     */
    public String getMac() {
	return mac;
    }

    /**
     * Define valor do atributo mac.
     * 
     * @param mac
     */
    public void setMac(String mac) {
	this.mac = mac;
    }

    /**
     * @return valor do atributo date.
     */
    public java.sql.Date getDate() {
	return date;
    }

    /**
     * Define valor do atributo date.
     * 
     * @param date
     */
    public void setDate(java.sql.Date date) {
	this.date = date;
    }

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

}