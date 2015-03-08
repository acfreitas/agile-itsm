package br.com.centralit.citcorpore.bean;

import javax.naming.directory.DirContext;
import javax.naming.ldap.LdapContext;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class ServidorContextoDTO implements IDto {
	
	private String ldpaFiltro;
	private String ldpaAtributo;
	private String grupoPadrao;
	private String perfilAcesso;
	private String numeroColaboradores;
	private DirContext dirContext;
	private String baseDominio;
	private LdapContext ldapContext;
	private String servidor;
	private boolean ativo;
	private String subDominio;
	
	public String getLdpaFiltro() {
		return ldpaFiltro;
	}
	public void setLdpaFiltro(String ldpaFiltro) {
		this.ldpaFiltro = ldpaFiltro;
	}
	public String getLdpaAtributo() {
		return ldpaAtributo;
	}
	public void setLdpaAtributo(String ldpaAtributo) {
		this.ldpaAtributo = ldpaAtributo;
	}
	public String getGrupoPadrao() {
		return grupoPadrao;
	}
	public void setGrupoPadrao(String grupoPadrao) {
		this.grupoPadrao = grupoPadrao;
	}
	public String getPerfilAcesso() {
		return perfilAcesso;
	}
	public void setPerfilAcesso(String perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
	}
	public String getNumeroColaboradores() {
		return numeroColaboradores;
	}
	public void setNumeroColaboradores(String numeroColaboradores) {
		this.numeroColaboradores = numeroColaboradores;
	}
	public DirContext getDirContext() {
		return dirContext;
	}
	public void setDirContext(DirContext dirContext) {
		this.dirContext = dirContext;
	}
	public String getBaseDominio() {
		return baseDominio;
	}
	public void setBaseDominio(String baseDominio) {
		this.baseDominio = baseDominio;
	}
	public LdapContext getLdapContext() {
		return ldapContext;
	}
	public void setLdapContext(LdapContext ldapContext) {
		this.ldapContext = ldapContext;
	}
	public String getServidor() {
		return servidor;
	}
	public void setServidor(String servidor) {
		this.servidor = servidor;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public String getSubDominio() {
		return subDominio;
	}
	public void setSubDominio(String subDominio) {
		this.subDominio = subDominio;
	}
	
}
