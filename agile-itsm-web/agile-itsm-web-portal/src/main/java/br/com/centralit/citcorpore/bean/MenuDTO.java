package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class MenuDTO implements IDto {

	private Integer idMenu;
	private Integer idMenuPai;
	private Integer idMenuFilho;
	private String nome;
	private String nomeFilho;
	private Date dataInicio;
	private Date dataFim;
	private String descricao;
	private Integer ordem;
	private String link;
	private String imagem;
	private String horizontal;
	private Integer idPerfil;
	private Integer idGrupo;
	private Integer idUsuario;
	private String pesquisa;
	private String inclui;
	private String altera;
	private String deleta;
	private String nomePerfil;
	private String menuRapido;
	private String mostrar;

	
	//variáveis auxiliares, não salvas no banco
	private Integer idPerfilAcesso;
	private String grava;
	
	/**
	 * @return valor do atributo idMenu.
	 */
	public Integer getIdMenu() {
		return idMenu;
	}

	/**
	 * Define valor do atributo idMenu.
	 * 
	 * @param idMenu
	 */
	public void setIdMenu(Integer idMenu) {
		this.idMenu = idMenu;
	}

	/**
	 * @return valor do atributo idMenuPai.
	 */
	public Integer getIdMenuPai() {
		return idMenuPai;
	}

	/**
	 * Define valor do atributo idMenuPai.
	 * 
	 * @param idMenuPai
	 */
	public void setIdMenuPai(Integer idMenuPai) {
		this.idMenuPai = idMenuPai;
	}

	/**
	 * @return valor do atributo nome.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Define valor do atributo nome.
	 * 
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
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
	 * @return valor do atributo descricao.
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Define valor do atributo descricao.
	 * 
	 * @param descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return valor do atributo ordem.
	 */
	public Integer getOrdem() {
		return ordem;
	}

	/**
	 * Define valor do atributo ordem.
	 * 
	 * @param ordem
	 */
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	/**
	 * @return valor do atributo link.
	 */
	public String getLink() {
		if(link == null)
			link = "";
		return link;
	}

	/**
	 * Define valor do atributo link.
	 * 
	 * @param link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return valor do atributo imagem.
	 */
	public String getImagem() {
		return imagem;
	}

	/**
	 * Define valor do atributo imagem.
	 * 
	 * @param imagem
	 */
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getHorizontal() {
		return horizontal;
	}

	public void setHorizontal(String horizontal) {
		this.horizontal = horizontal;
	}

	/**
	 * @return valor do atributo idPerfil.
	 */
	public Integer getIdPerfil() {
		return idPerfil;
	}

	/**
	 * Define valor do atributo idPerfil.
	 * 
	 * @param idPerfil
	 */
	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
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
	 * @return valor do atributo pesquisa.
	 */
	public String getPesquisa() {
		return pesquisa;
	}

	/**
	 * Define valor do atributo pesquisa.
	 * 
	 * @param pesquisa
	 */
	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	/**
	 * @return valor do atributo inclui.
	 */
	public String getInclui() {
		return inclui;
	}

	/**
	 * Define valor do atributo inclui.
	 * 
	 * @param inclui
	 */
	public void setInclui(String inclui) {
		this.inclui = inclui;
	}

	/**
	 * @return valor do atributo altera.
	 */
	public String getAltera() {
		return altera;
	}

	/**
	 * Define valor do atributo altera.
	 * 
	 * @param altera
	 */
	public void setAltera(String altera) {
		this.altera = altera;
	}

	/**
	 * @return valor do atributo deleta.
	 */
	public String getDeleta() {
		return deleta;
	}

	/**
	 * Define valor do atributo deleta.
	 * 
	 * @param deleta
	 */
	public void setDeleta(String deleta) {
		this.deleta = deleta;
	}

	public void setIdMenuFilho(Integer idMenuFilho) {
		this.idMenuFilho = idMenuFilho;
	}

	public Integer getIdMenuFilho() {
		return idMenuFilho;
	}

	public void setNomeFilho(String nomeFilho) {
		this.nomeFilho = nomeFilho;
	}

	public String getNomeFilho() {
		return nomeFilho;
	}

	public String getNomePerfil() {
		return nomePerfil;
	}

	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}

	public String getMenuRapido() {
		return menuRapido;
	}

	public void setMenuRapido(String menuRapido) {
		this.menuRapido = menuRapido;
	}

	public String getMostrar() {
		return mostrar;
	}

	public void setMostrar(String mostrar) {
		this.mostrar = mostrar;
	}

	public Integer getIdPerfilAcesso() {
		return idPerfilAcesso;
	}

	public void setIdPerfilAcesso(Integer idPerfilAcesso) {
		this.idPerfilAcesso = idPerfilAcesso;
	}

	public String getGrava() {
		return grava;
	}

	public void setGrava(String grava) {
		this.grava = grava;
	}

}
