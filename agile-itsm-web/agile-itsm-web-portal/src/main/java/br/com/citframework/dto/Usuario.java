package br.com.citframework.dto;



public class Usuario implements IDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 209957399940789640L;
	
	private String idUsuario;
	private String ipUsuario;
	private String servidor;
	private Integer idEmpresa;
	private String nomeUsuario;
	private String matricula;
	private String acessos;
	private String locale;
	
	private String[] grupos;
	private Integer idProfissional;
	
	private Integer idUsuarioSistema;
	private String nomeEmpresa;

	public Usuario() {
	}
	
	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getIpUsuario() {
		return ipUsuario;
	}

	public void setIpUsuario(String ipUsuario) {
		this.ipUsuario = ipUsuario;
	}

	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		String newLine = "\r\n";
		if (matricula != null) {
			buffer.append("Matricula: ");
			buffer.append(matricula).append(newLine);
		}
		buffer.append("Nome do usuario: ");
		buffer.append(nomeUsuario).append(newLine);
		return buffer.toString();
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getAcessos() {
		return acessos;
	}

	public void setAcessos(String acessos) {
		this.acessos = acessos;
	}

	public String[] getGrupos() {
		return grupos;
	}

	public void setGrupos(String[] grupos) {
		this.grupos = grupos;
	}

	public Integer getIdProfissional() {
		return idProfissional;
	}

	public void setIdProfissional(Integer idProfissional) {
		this.idProfissional = idProfissional;
	}

	public Integer getIdUsuarioSistema() {
		return idUsuarioSistema;
	}

	public void setIdUsuarioSistema(Integer idUsuarioSistema) {
		this.idUsuarioSistema = idUsuarioSistema;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
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
}