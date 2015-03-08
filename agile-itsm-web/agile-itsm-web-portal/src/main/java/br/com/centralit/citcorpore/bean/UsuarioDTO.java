package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.DateAdapter;
import br.com.citframework.util.DateTimeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Usuario") 
public class UsuarioDTO implements IDto, Comparable<UsuarioDTO> {

	private static final long serialVersionUID = 638687400065001805L;

	private Integer idUsuario;
	private Integer idUnidade;
	private Integer idEmpregado;
	private Integer idPerfilAcessoUsuario;

	@XmlElement(name = "dataInicio")
	@XmlJavaTypeAdapter(DateAdapter.class)	
	private Date dataInicio;

	@XmlElement(name = "dataFim")
	@XmlJavaTypeAdapter(DateAdapter.class)	
	private Date dataFim;

	private Integer idGrupo;
	private Integer idEmpresa;
	private String login;
	private String nomeUsuario;
	private String senha;
	private String senhaNovamente;
	private String status;
	private String[] grupos;
	private String ldap;
	
	//Grupo
	private String nomeGrupo;
	private String grupoSerialize;

	@XmlElement(name = "ultimoAcessoPortal")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)	
	private Timestamp ultimoAcessoPortal;
	
	private String locale;

	private Integer seguencia;

	private Collection<GrupoDTO> colGrupos;
	
	private Collection<GrupoEmpregadoDTO> colGrupoEmpregado;
	
	private String email;
	
	//Campo transiente
	private String acessoCitsmart;
	
	

	/**
	 * @return valor do atributo idUsuario.
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Define valor do atributo idUsuario.
	 * 
	 * @param idUsuario
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return valor do atributo idUnidade.
	 */
	public Integer getIdUnidade() {
		return idUnidade;
	}

	/**
	 * Define valor do atributo idUnidade.
	 * 
	 * @param idUnidade
	 */
	public void setIdUnidade(Integer idUnidade) {
		this.idUnidade = idUnidade;
	}

	/**
	 * @return valor do atributo idEmpregado.
	 */
	public Integer getIdEmpregado() {
		return idEmpregado;
	}

	/**
	 * Define valor do atributo idEmpregado.
	 * 
	 * @param idEmpregado
	 */
	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}

	/**
	 * @return valor do atributo idPerfilAcessoUsuario.
	 */
	public Integer getIdPerfilAcessoUsuario() {
		return idPerfilAcessoUsuario;
	}

	/**
	 * Define valor do atributo idPerfilAcessoUsuario.
	 * 
	 * @param idPerfilAcessoUsuario
	 */
	public void setIdPerfilAcessoUsuario(Integer idPerfilAcessoUsuario) {
		this.idPerfilAcessoUsuario = idPerfilAcessoUsuario;
	}

	/**
	 * @return valor do atributo dataInicio.
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * Define valor do atributo dataInicio.
	 * 
	 * @param dataInicio
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return valor do atributo dataFim.
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * Define valor do atributo dataFim.
	 * 
	 * @param dataFim
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return valor do atributo idGrupo.
	 */
	public Integer getIdGrupo() {
		return idGrupo;
	}

	/**
	 * Define valor do atributo idGrupo.
	 * 
	 * @param idGrupo
	 */
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 * @return valor do atributo idEmpresa.
	 */
	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * Define valor do atributo idEmpresa.
	 * 
	 * @param idEmpresa
	 */
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	/**
	 * @return valor do atributo login.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Define valor do atributo login.
	 * 
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return valor do atributo nomeUsuario.
	 */
	public String getNomeUsuario() {

		return Util.tratarAspasSimples(this.nomeUsuario);
	}

	/**
	 * Define valor do atributo nomeUsuario.
	 * 
	 * @param nomeUsuario
	 */
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	/**
	 * @return valor do atributo senha.
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Define valor do atributo senha.
	 * 
	 * @param senha
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return valor do atributo status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Define valor do atributo status.
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return valor do atributo grupos.
	 */
	public String[] getGrupos() {
		return grupos;
	}

	/**
	 * Define valor do atributo grupos.
	 * 
	 * @param grupos
	 */
	public void setGrupos(String[] grupos) {
		this.grupos = grupos;
	}

	/**
	 * @return valor do atributo seguencia.
	 */
	public Integer getSeguencia() {
		return seguencia;
	}

	/**
	 * Define valor do atributo seguencia.
	 * 
	 * @param seguencia
	 */
	public void setSeguencia(Integer seguencia) {
		this.seguencia = seguencia;
	}

	public Collection<GrupoDTO> getColGrupos() {
		return colGrupos;
	}

	public void setColGrupos(Collection<GrupoDTO> colGrupos) {
		this.colGrupos = colGrupos;
	}

	public String getSenhaNovamente() {
		return senhaNovamente;
	}

	public void setSenhaNovamente(String senhaNovamente) {
		this.senhaNovamente = senhaNovamente;
	}

	public String getLdap() {
		return ldap;
	}

	public void setLdap(String ldap) {
		this.ldap = ldap;
	}

	public Timestamp getUltimoAcessoPortal() {
		return ultimoAcessoPortal;
	}

	public void setUltimoAcessoPortal(Timestamp ultimoAcessoPortal) {
		this.ultimoAcessoPortal = ultimoAcessoPortal;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

	public String getGrupoSerialize() {
		return grupoSerialize;
	}

	public void setGrupoSerialize(String grupoSerialize) {
		this.grupoSerialize = grupoSerialize;
	}

	public Collection<GrupoEmpregadoDTO> getColGrupoEmpregado() {
		return colGrupoEmpregado;
	}

	public void setColGrupoEmpregado(Collection<GrupoEmpregadoDTO> colGrupoEmpregado) {
		this.colGrupoEmpregado = colGrupoEmpregado;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int compareTo(UsuarioDTO o) {
		return nomeUsuario.compareTo(o.getNomeUsuario());
	}

	/**
	 * @return the acessoCitsmart
	 */
	public String getAcessoCitsmart() {
		return acessoCitsmart;
	}

	/**
	 * @param acessoCitsmart the acessoCitsmart to set
	 */
	public void setAcessoCitsmart(String acessoCitsmart) {
		this.acessoCitsmart = acessoCitsmart;
	}

}
