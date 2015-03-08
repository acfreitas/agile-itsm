package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PerfilAcessoMenuDao extends CrudDaoDefaultImpl {

	public PerfilAcessoMenuDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();

		listFields
				.add(new Field("IDMENU", "idMenu", true, false, false, false));
		listFields.add(new Field("IDPERFILACESSO", "idPerfilAcesso", true,
				false, false, false));
		listFields.add(new Field("PESQUISA", "pesquisa", false, false, false,
				false));
		listFields.add(new Field("GRAVA", "grava", false, false, false, false));
		listFields
				.add(new Field("DELETA", "deleta", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false,
				false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false,
				false));

		return listFields;
	}

	@Override
	public void delete(IDto obj) throws PersistenceException {
		PerfilAcessoMenuDTO perfilAcessoMenuDTO = (PerfilAcessoMenuDTO) obj;
		List condicao = new ArrayList();
		condicao.add(new Condition("idPerfilAcesso", "=", perfilAcessoMenuDTO
				.getIdPerfilAcesso()));
		condicao.add(new Condition("idMenu", "=", perfilAcessoMenuDTO
				.getIdMenu()));
		super.deleteByCondition(condicao);
	}

	/**
	 * Exclui PerfisAcessoMenu do perfilAcesso informado.
	 *
	 * @param perfilAcesso
	 * @throws PersistenceException
	 */
	public void excluirPerfisAcessoMenu(PerfilAcessoDTO perfilAcesso)
			throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametros = new ArrayList();

		sql.append("UPDATE perfilacessomenu SET datafim = ? WHERE idperfilacesso = ?");
		parametros.add(UtilDatas.getDataAtual());
		parametros.add(perfilAcesso.getIdPerfilAcesso());

		this.execUpdate(sql.toString(), parametros.toArray());
	}

	@Override
	public String getTableName() {
		return "PERFILACESSOMENU";
	}

	@Override
	public Collection list() throws PersistenceException {

		List ordenacao = new ArrayList();

		ordenacao.add(new Order("idMenu"));

		return super.list(ordenacao);
	}

	@Override
	public Class getBean() {
		return new PerfilAcessoMenuDTO().getClass();
	}

	/**
	 * Retorna PerfilAcessoMenu ativos por idPerfilAcesso.
	 *
	 * @param idPerfilAcesso
	 * @return
	 * @throws Exception
	 */
	public Collection findByPerfilAcesso(Integer idPerfilAcesso)
			throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idPerfilAcesso", "=", idPerfilAcesso));
		condicao.add(new Condition("dataFim", "is", null));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection<PerfilAcessoMenuDTO> restoreMenusAcesso(IDto obj)
			throws PersistenceException {
		PerfilAcessoMenuDTO perfilAcessoMenuDTO = (PerfilAcessoMenuDTO) obj;
		List param = new ArrayList();
		List fields = new ArrayList();
		fields.add("pesquisa");
		fields.add("grava");
		fields.add("deleta");
		param.add(perfilAcessoMenuDTO.getIdMenu());
		param.add(perfilAcessoMenuDTO.getIdPerfilAcesso());
		String sql = "SELECT distinct pesquisa, grava, deleta FROM "
				+ getTableName()
				+ " WHERE idMenu = ? AND idPerfilAcesso = ? AND datafim is null";
		List dados = this.execSQL(sql, param.toArray());
		return this.listConvertion(getBean(), dados, fields);
	}

	public Collection<PerfilAcessoMenuDTO> pesquisaSeJaExisteAcessoMenuPai(
			Integer idPerfilAcesso, Integer idMenu) throws PersistenceException {
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT idMenu FROM "
				+ getTableName()
				+ " WHERE idperfilacesso = ? AND idmenu = ? AND dataFim IS NULL ");
		parametro.add(idPerfilAcesso);
		parametro.add(idMenu);
		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idMenu");
		List result = null;
		if (lista != null && !lista.isEmpty()) {
			result = this.engine.listConvertion(getBean(), lista, listRetorno);
		}
		return result;
	}

	@Override
	public void update(IDto obj) throws PersistenceException {
		PerfilAcessoMenuDTO perfilAcessoMenuDTO = (PerfilAcessoMenuDTO) obj;
		List parametros = new ArrayList();
		parametros.add(UtilDatas.getDataAtual());
		parametros.add(perfilAcessoMenuDTO.getIdPerfilAcesso());

		String sql = "UPDATE perfilacessomenu SET DATAFIM = ? WHERE idPerfilAcesso = ? ";

		this.execUpdate(sql, parametros.toArray());
	}
	
	public List<PerfilAcessoMenuDTO> getPerfilAcessoMenu(PerfilAcessoMenuDTO perfilAcessoMenuDTO) throws PersistenceException{
		List param = new ArrayList();
		List fields = new ArrayList();
		fields.add("pesquisa");
		fields.add("grava");
		fields.add("deleta");
		param.add(perfilAcessoMenuDTO.getIdMenu());
		param.add(perfilAcessoMenuDTO.getIdPerfilAcesso());
		String sql = "SELECT distinct pesquisa, grava, deleta FROM "
				+ getTableName()
				+ " WHERE idMenu = ? AND idPerfilAcesso = ? AND datafim is null";
		List dados = this.execSQL(sql, param.toArray());
		return this.listConvertion(getBean(), dados, fields);
	}
	
	/**
	 * Obtém um Mapa<idMenu, List<PerfilAcessoMenu> > de todos os menus deste usuário
     * 
     * @author thyen.chang
     * @since 28/01/2015 - OPERAÇÃO USAIN BOLT
	 * @param usuario
	 * @return
	 * @throws PersistenceException
	 */
	public Map<Integer, List<PerfilAcessoMenuDTO> > getPerfilAcessoBotoesMenu(UsuarioDTO usuario) throws PersistenceException{
		if(usuario == null || usuario.getIdUsuario() == null){
			return null;
		}
		
		StringBuilder sql = new StringBuilder();
		List parametros = new ArrayList();
		
		sql.append("SELECT DISTINCT pam.idmenu, pam.pesquisa, pam.grava, pam.deleta ");
		sql.append("	FROM usuario u ");
		sql.append("		JOIN perfilacessousuario pau ON pau.idusuario = u.idusuario and u.idusuario = ? ");
		parametros.add(usuario.getIdUsuario());
		sql.append("		JOIN perfilacessomenu pam ON pam.idperfilacesso = pau.idperfil ");
		sql.append("		JOIN menu m ON m.idmenu = pam.idmenu AND m.datafim IS NULL ");	
		sql.append("UNION ");
		sql.append("SELECT DISTINCT pam.idmenu, pam.pesquisa, pam.grava, pam.deleta ");
		sql.append("	FROM usuario u ");
		sql.append("		JOIN perfilacessousuario pau ON pau.idusuario = u.idusuario and u.idusuario = ? ");
		parametros.add(usuario.getIdUsuario());
		sql.append("		JOIN gruposempregados ge ON ge.idempregado = u.idempregado ");
		sql.append("		JOIN perfilacessogrupo pag ON pag.idgrupo = ge.idgrupo ");
		sql.append("		JOIN perfilacessomenu pam ON pag.idperfil = pam.idperfilacesso ");
		sql.append("		JOIN menu m ON m.idmenu = pam.idmenu AND m.datafim IS NULL ");	
		List retorno = this.execSQL(sql.toString(), parametros.toArray());
		
		List fields = new ArrayList();
		
		fields.add("idMenu");
		fields.add("pesquisa");
		fields.add("grava");
		fields.add("deleta");
		
		return getMapaPerfilDeAcessoBylist(this.listConvertion(getBean(), retorno, fields));
	}
	
	/**
	 * Converte a lista de permissão que o usuário tem para acessar cada menu em um Mapa
     * 
     * @author thyen.chang
     * @since 28/01/2015 - OPERAÇÃO USAIN BOLT
	 * @param listaPerfilAcesso
	 * @return
	 */
	private Map<Integer, List<PerfilAcessoMenuDTO> > getMapaPerfilDeAcessoBylist(List<PerfilAcessoMenuDTO> listaPerfilAcesso){
		Map<Integer, List<PerfilAcessoMenuDTO> > mapaPerfilAcesso = new HashMap<Integer, List<PerfilAcessoMenuDTO>>();
		for(PerfilAcessoMenuDTO perfilAcessoMenu : listaPerfilAcesso){
			List<PerfilAcessoMenuDTO> listaMenu = mapaPerfilAcesso.get(perfilAcessoMenu.getIdMenu());
			if(listaMenu == null){
				listaMenu = new ArrayList<PerfilAcessoMenuDTO>();
			} 
			listaMenu.add(perfilAcessoMenu);
			mapaPerfilAcesso.put(perfilAcessoMenu.getIdMenu(), listaMenu);
		}
		return mapaPerfilAcesso;
	}

}
