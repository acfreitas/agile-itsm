package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MenuDao extends CrudDaoDefaultImpl {

    public MenuDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("IDMENU", "idMenu", true, true, false, false));
        listFields.add(new Field("IDMENUPAI", "idMenuPai", false, false, false, false));
        listFields.add(new Field("NOME", "nome", false, false, false, false));
        listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
        listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
        listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
        listFields.add(new Field("ORDEM", "ordem", false, false, false, false));
        listFields.add(new Field("MENURAPIDO", "menuRapido", false, false, false, false));
        listFields.add(new Field("LINK", "link", false, false, false, false));
        listFields.add(new Field("IMAGEM", "imagem", false, false, false, false));
        listFields.add(new Field("MOSTRAR", "mostrar", false, false, false, false));
        return listFields;
    }

    public void updateNotNull(Collection<MenuDTO> menus) {
        try {
            for (MenuDTO menu : menus) {
                if (menu.getIdMenu() != null) {
                    updateNotNull(menu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDataFim() throws PersistenceException {
        Object[] parametro = new Object[] { UtilDatas.getDataAtual() };

        String sql = "UPDATE " + getTableName() + " SET datafim = ?";
        execUpdate(sql, parametro);
    }

    public void deleteAll() throws PersistenceException {
        String sql = "delete from " + getTableName();
        super.execUpdate(sql, null);
    }

    public void deleteMenu(Integer idMenu) throws PersistenceException {
        String sql = "delete from " + getTableName() + " where idmenu = ?";
        super.execUpdate(sql, new Object[] { idMenu });
    }

    @Override
    public String getTableName() {
        return "MENU";
    }

    @Override
    public Collection list() throws PersistenceException {
        List<Order> list = new ArrayList<>();
        list.add(new Order("nome"));
        return super.list(list);
    }

    @Override
    public Class<MenuDTO> getBean() {
        return MenuDTO.class;
    }

    public Collection<MenuDTO> listarMenusPorPerfil(UsuarioDTO usuario, Integer idMenuPai) throws PersistenceException {
        return listaMenus(usuario, idMenuPai, false);
    }

    public Collection<MenuDTO> listarMenusPorPerfil(UsuarioDTO usuario, Integer idMenuPai, boolean menuRapido)
            throws PersistenceException {
        return listaMenus(usuario, idMenuPai, menuRapido);
    }

    /**
     * Lista os menus por perfil de acesso Metodo reutilizável
     * Otimizado para utilizar comparador de igualdade.
     * @author thyen.chang
     * @since 23/01/2015 Operação Usain Bolt
     */
    public Collection<MenuDTO> listaMenus(UsuarioDTO usuario, Integer idMenuPai, boolean menuRapido) throws PersistenceException {
        if (usuario == null) {
            return null;
        }

        List result = null;
        StringBuilder sql = new StringBuilder();
        List<Integer> parametros = new ArrayList<>();

        sql.append("SELECT distinct m.idmenu, m.idmenupai, m.nome, m.datainicio, m.datafim, m.descricao, m.ordem, m.link, m.imagem, m.mostrar ");
        sql.append("FROM perfilacessomenu a ");
        sql.append("JOIN menu m ON m.idmenu = a.idmenu ");
        sql.append("WHERE m.datafim IS NULL AND (a.pesquisa = 'S' OR a.grava = 'S' OR a.deleta = 'S') ");

        if (menuRapido) {
            sql.append(" AND (m.menuRapido = 'S'  OR m.link = '')");
        }

        if (idMenuPai == null) {
            if (!menuRapido) {
                sql.append("AND m.idmenupai IS NULL ");
            }
        } else {
            sql.append("AND m.idmenupai = ? ");
            parametros.add(idMenuPai);
        }
        
    	getPerfilAcessoUsuarioDAO().setTransactionControler(this.getTransactionControler());
    	getGrupoDAO().setTransactionControler(this.getTransactionControler());
    	getPerfilAcessoGrupoDAO().setTransactionControler(this.getTransactionControler());

        PerfilAcessoUsuarioDTO perfilAcessoEspecifico =  getPerfilAcessoUsuarioDAO().obterPerfilAcessoUsuario(usuario);

        if (perfilAcessoEspecifico != null) {
            sql.append("AND (a.idperfilacesso = ? ");
            parametros.add(perfilAcessoEspecifico.getIdPerfilAcesso());

            Collection<GrupoDTO> gruposDoEmpregado = getGrupoDAO().getGruposByIdEmpregado(usuario.getIdEmpregado());

            if (gruposDoEmpregado != null && !gruposDoEmpregado.isEmpty()) {
                for (GrupoDTO grupo : gruposDoEmpregado) {
                    PerfilAcessoGrupoDTO perfilAcessoGrupo = getPerfilAcessoGrupoDAO().obterPerfilAcessoGrupo(grupo);

                    if (perfilAcessoGrupo != null) {
                        sql.append("OR a.idperfilacesso = ? ");
                        parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
                    }
                }
            }
            sql.append(")");
        } else {
            Collection<GrupoDTO> gruposDoEmpregado = getGrupoDAO().getGruposByIdEmpregado(usuario.getIdEmpregado());

            if (gruposDoEmpregado != null && !gruposDoEmpregado.isEmpty()) {
                boolean aux = true;
                for (GrupoDTO grupo : gruposDoEmpregado) {
                    PerfilAcessoGrupoDTO perfilAcessoGrupo = getPerfilAcessoGrupoDAO().obterPerfilAcessoGrupo(grupo);

                    if (perfilAcessoGrupo != null) {
                        if (aux) {
                            sql.append("AND (a.idperfilacesso = ? ");
                            parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
                            aux = false;
                        } else {
                            sql.append("OR a.idperfilacesso = ? ");
                            parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
                        }
                    }
                }
                if (!aux) {
                    sql.append(")");
                }
            }
        }

        sql.append(" ORDER BY ordem, idmenupai");
        Object[] paramsFinal = parametros.size() == 0 ? null : parametros.toArray();
        List lista;
        try {
            lista = this.execSQL(sql.toString(), paramsFinal);

            List<String> listRetorno = new ArrayList<>();
            listRetorno.add("idMenu");
            listRetorno.add("idMenuPai");
            listRetorno.add("nome");
            listRetorno.add("dataInicio");
            listRetorno.add("dataFim");
            listRetorno.add("descricao");
            listRetorno.add("ordem");
            listRetorno.add("link");
            listRetorno.add("imagem");
            listRetorno.add("mostrar");

            result = engine.listConvertion(MenuDTO.class, lista, listRetorno);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Collection<MenuDTO> listaMenuByUsr(UsuarioDTO usuario) throws PersistenceException {
        List result = null;
        StringBuilder sql = new StringBuilder();
        List<Integer> parametros = new ArrayList<>();

        sql.append("SELECT distinct m.idmenu, m.idmenupai, m.nome, m.datainicio, m.datafim, m.descricao, m.ordem, m.link, m.imagem ");
        sql.append("FROM perfilacessomenu a ");
        sql.append("JOIN menu m ON m.idmenu = a.idmenu ");
        sql.append("WHERE m.datafim IS NULL AND (a.pesquisa <> 'N' OR a.grava <> 'N'  OR a.deleta <> 'N')");

        if (usuario != null) {
        	
        	getPerfilAcessoUsuarioDAO().setTransactionControler(this.getTransactionControler());
        	getGrupoDAO().setTransactionControler(this.getTransactionControler());
        	getPerfilAcessoGrupoDAO().setTransactionControler(this.getTransactionControler());
        	
            PerfilAcessoUsuarioDTO perfilAcessoEspecifico = getPerfilAcessoUsuarioDAO().obterPerfilAcessoUsuario(usuario);

            if (perfilAcessoEspecifico != null) {
                sql.append("AND (a.idperfilacesso = ? ");
                parametros.add(perfilAcessoEspecifico.getIdPerfilAcesso());

                Collection<GrupoDTO> gruposDoEmpregado = getGrupoDAO().getGruposByIdEmpregado(usuario.getIdEmpregado());

                if (gruposDoEmpregado != null && !gruposDoEmpregado.isEmpty()) {
                    for (GrupoDTO grupo : gruposDoEmpregado) {
                        PerfilAcessoGrupoDTO perfilAcessoGrupo = getPerfilAcessoGrupoDAO().obterPerfilAcessoGrupo(grupo);

                        if (perfilAcessoGrupo != null) {
                            sql.append("OR a.idperfilacesso = ? ");
                            parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
                        }
                    }
                }
                sql.append(")");
            } else {
                Collection<GrupoDTO> gruposDoEmpregado = getGrupoDAO().getGruposByIdEmpregado(usuario.getIdEmpregado());

                if (gruposDoEmpregado != null && !gruposDoEmpregado.isEmpty()) {
                    boolean aux = true;
                    for (GrupoDTO grupo : gruposDoEmpregado) {
                        PerfilAcessoGrupoDTO perfilAcessoGrupo = getPerfilAcessoGrupoDAO().obterPerfilAcessoGrupo(grupo);

                        if (perfilAcessoGrupo != null) {
                            if (aux) {
                                sql.append("AND (a.idperfilacesso = ? ");
                                parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
                                aux = false;
                            } else {
                                sql.append("OR a.idperfilacesso = ? ");
                                parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
                            }
                        }
                    }
                    if (!aux) {
                        sql.append(")");
                    }
                }
            }
        }

        sql.append("AND m.link IS NOT NULL");

        sql.append(" ORDER BY ordem, idmenupai");
        Object[] paramsFinal = parametros.size() == 0 ? null : parametros.toArray();
        List lista;
        try {
            lista = this.execSQL(sql.toString(), paramsFinal);

            List<String> listRetorno = new ArrayList<>();
            listRetorno.add("idMenu");
            listRetorno.add("idMenuPai");
            listRetorno.add("nome");
            listRetorno.add("dataInicio");
            listRetorno.add("dataFim");
            listRetorno.add("descricao");
            listRetorno.add("ordem");
            listRetorno.add("link");
            listRetorno.add("imagem");

            result = engine.listConvertion(MenuDTO.class, lista, listRetorno);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Integer getPerfilAcesso(UsuarioDTO usuario) throws PersistenceException {
        Integer retorno = null;
        StringBuilder sqlPerfilUsuario = new StringBuilder();
        sqlPerfilUsuario.append("SELECT idperfil FROM perfilacessousuario WHERE idusuario = ? AND datafim IS NULL");

        StringBuilder sqlPerfilGrupo = new StringBuilder();
        sqlPerfilGrupo.append("SELECT idperfil FROM perfilacessogrupo WHERE idgrupo = ? AND datafim IS NULL");
        Object[] params01 = new Object[] { usuario.getIdUsuario() };
        Object[] params02 = new Object[] { usuario.getIdGrupo() };
        List lista;
        List<String> camposConversao = new ArrayList<>();
        List<PerfilAcessoDTO> result;
        try {
            lista = this.execSQL(sqlPerfilUsuario.toString(), params01);
            if (lista.isEmpty()) {
                lista = this.execSQL(sqlPerfilGrupo.toString(), params02);
            }
            if (!lista.isEmpty()) {
                camposConversao.add("idPerfilAcesso");
                result = (List<PerfilAcessoDTO>) engine.listConvertion(PerfilAcessoDTO.class, lista,
                        camposConversao);
                retorno = result.get(0).getIdPerfilAcesso();
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public Collection listarMenus() throws PersistenceException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT distinct menu.idMenu,menu.idMenuPai,menu.nome,menu.mostrar FROM MENU menu INNER JOIN MENU menuFilho ON menu.idMenu = menuFilho.idMenu ");
        sql.append("WHERE menu.idmenupai IS NULL and menu.datafim IS NULL and menuFilho.datafim is null");
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), null);
        List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idMenu");
        listRetorno.add("idMenuPai");
        listRetorno.add("nome");
        listRetorno.add("mostrar");
        return engine.listConvertion(getBean(), lista, listRetorno);
    }

    public Collection<MenuDTO> listaIdNomeMenus() throws PersistenceException {
        StringBuilder sql = new StringBuilder();
        sql.append("select m.idmenu, m.nome, p.idperfilacesso, p.pesquisa, p.grava, p.deleta from perfilacessomenu p inner join menu m on p.idmenu = m.idmenu ");
        sql.append(" where p.datafim is null ");
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), null);
        List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idMenu");
        listRetorno.add("nome");
        listRetorno.add("idPerfilAcesso");
        listRetorno.add("pesquisa");
        listRetorno.add("grava");
        listRetorno.add("deleta");
        List result = null;
        if (lista != null && !lista.isEmpty()) {
            result = engine.listConvertion(getBean(), lista, listRetorno);
        }
        return result;
    }

    public Collection<MenuDTO> listarMenusFilhoByIdMenuPai(Integer idMenuPai) throws PersistenceException {
        List<Integer> parametro = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select idmenu, link from menu where idmenupai = ? ");
        parametro.add(idMenuPai);
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idMenu");
        listRetorno.add("link");
        List result = null;
        if (lista != null && !lista.isEmpty()) {
            result = engine.listConvertion(getBean(), lista, listRetorno);
        }
        return result;
    }

    public Collection<MenuDTO> listarSubMenus(MenuDTO submenu) throws PersistenceException {
        List<Integer> parametro = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT idMenu, idMenuPai, nome FROM ");
        sql.append(getTableName());
        sql.append(" WHERE idmenupai = ? AND datafim IS NULL ");

        parametro.add(submenu.getIdMenu());
        List lista = new ArrayList<>();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idMenu");
        listRetorno.add("idMenuPai");
        listRetorno.add("nome");
        return engine.listConvertion(getBean(), lista, listRetorno);
    }

    public Collection<MenuDTO> listarMenusPais() throws PersistenceException {
        List<String> parametro = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT idMenu, nome, descricao, ordem, link, imagem, datainicio, menurapido FROM ");
        sql.append(getTableName());
        sql.append(" WHERE idmenupai is null AND dataFim IS NULL ");

        List lista = new ArrayList<>();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idMenu");
        listRetorno.add("nome");
        listRetorno.add("descricao");
        listRetorno.add("ordem");
        listRetorno.add("link");
        listRetorno.add("imagem");
        listRetorno.add("dataInicio");
        listRetorno.add("menuRapido");
        return engine.listConvertion(getBean(), lista, listRetorno);
    }

    public Collection<MenuDTO> listarMenusFilhos(Integer idMenuPai) throws PersistenceException {
        List<Integer> parametro = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT idMenu, idMenuPai, nome, descricao, ordem, link, imagem, datainicio, menurapido FROM ");
        sql.append(getTableName());
        sql.append(" WHERE idmenupai = ? AND dataFim IS NULL ");

        parametro.add(idMenuPai);
        List lista = new ArrayList<>();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idMenu");
        listRetorno.add("idMenuPai");
        listRetorno.add("nome");
        listRetorno.add("descricao");
        listRetorno.add("ordem");
        listRetorno.add("link");
        listRetorno.add("imagem");
        listRetorno.add("dataInicio");
        listRetorno.add("menuRapido");
        return engine.listConvertion(getBean(), lista, listRetorno);
    }

    public Collection<MenuDTO> listarMenuPai(Integer idMenuFilho) throws PersistenceException {
        List<Integer> parametro = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT idMenu, idMenuPai FROM " + getTableName() + " WHERE idmenu = ? AND dataFim IS NULL ");
        parametro.add(idMenuFilho);
        List lista = new ArrayList<>();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idMenu");
        listRetorno.add("idMenuPai");
        List result = null;
        if (lista != null && !lista.isEmpty()) {
            result = engine.listConvertion(getBean(), lista, listRetorno);
        }
        return result;
    }

    /**
     * Método para verificar se caso exista um menu com o mesmo nome
     *
     * @author rodrigo.oliveira
     * @param menuDTO
     * @return Se caso exista menu com o mesmo nome retorna true
     */
    public boolean verificaSeExisteMenu(MenuDTO menuDTO) throws PersistenceException {
        List parametro = new ArrayList<>();
        List list = new ArrayList<>();
        String sql = "SELECT idmenu FROM " + getTableName() + " WHERE nome = ? AND dataFim IS NULL ";
        parametro.add(menuDTO.getNome());
        if (menuDTO.getIdMenu() != null) {
            sql += " AND idmenu <> ? ";
            parametro.add(menuDTO.getIdMenu());
        }

        list = this.execSQL(sql, parametro.toArray());

        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean verificaSeExisteMenuPorLink(MenuDTO menuDTO) throws PersistenceException {
        List parametro = new ArrayList<>();
        List list = new ArrayList<>();
        String sql = "SELECT idmenu FROM " + getTableName() + " WHERE nome = ? AND dataFim IS NULL ";
        parametro.add(menuDTO.getNome());
        if (menuDTO.getIdMenu() != null) {
            sql += " AND idmenu <> ? ";
            parametro.add(menuDTO.getIdMenu());
        }

        list = this.execSQL(sql, parametro.toArray());

        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    public Integer buscarIdMenu(String link) throws PersistenceException {
        List<String> ordenacao = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT idmenu FROM menu where link = ? and dataFim is null");
        List lista = this.execSQL(sql.toString(), new Object[] { link });
        ordenacao.add("idMenu");
        if (lista != null && !lista.isEmpty()) {
            Collection<MenuDTO> result = engine.listConvertion(getBean(), lista, ordenacao);
            for (MenuDTO menu : result) {
                return menu.getIdMenu();
            }
        }
        return null;
    }

    public void alterarMenuPorNome(MenuDTO menuDTO) throws PersistenceException {
        List<Object> parametro = new ArrayList<>();
        String sql = "UPDATE " + getTableName() + " SET idMenuPai = ?, link = ? WHERE nome = ? AND dataFim IS NULL ";
        parametro.add(menuDTO.getIdMenuPai());
        parametro.add(menuDTO.getLink());
        parametro.add(menuDTO.getNome());
        if (menuDTO.getIdMenu() != null) {
            sql += " AND idmenu <> ? ";
            parametro.add(menuDTO.getIdMenu());
        }

        this.execUpdate(sql, parametro.toArray());
    }

    private GrupoDao grupoDAO;
    private PerfilAcessoGrupoDao perfilAcessoGrupoDAO;
    private PerfilAcessoUsuarioDAO perfilAcessoUsuarioDAO;

    private GrupoDao getGrupoDAO() {
        if (grupoDAO == null) {
        	grupoDAO = new GrupoDao();
        }
        return grupoDAO;
    }

    private PerfilAcessoGrupoDao getPerfilAcessoGrupoDAO() {
        if (perfilAcessoGrupoDAO == null) {
        	perfilAcessoGrupoDAO = new PerfilAcessoGrupoDao();
        }
        return perfilAcessoGrupoDAO;
    }

    private PerfilAcessoUsuarioDAO getPerfilAcessoUsuarioDAO() {
        if (perfilAcessoUsuarioDAO == null) {
            perfilAcessoUsuarioDAO = new PerfilAcessoUsuarioDAO();
        }
        return perfilAcessoUsuarioDAO;
    }

    /**
     * Método para retornar um mapa com todos os menus que o usuário pode acessar
     * Mapa<idMenuPai, List<MenusFilhos>
     * @author thyen.chang
     * @since 26/01/2015 - OPERAÇÃO USAIN BOLT
     * @param usuario
     * @return
     * @throws PersistenceException
     */
    public Map<Integer, List<MenuDTO> > listaMenuPorUsuario(UsuarioDTO usuario) throws PersistenceException{
    	if(usuario == null || usuario.getIdUsuario() == null){
    		return null;
    	}
    	List result = null;
        StringBuilder sql = new StringBuilder();
        List<Integer> parametros = new ArrayList<>();

        sql.append("SELECT distinct m.idmenu, m.idmenupai, m.nome, m.datainicio, m.datafim, m.descricao, m.ordem, m.link, m.imagem, m.mostrar ");
        sql.append("FROM perfilacessomenu a ");
        sql.append("JOIN menu m ON m.idmenu = a.idmenu ");
        sql.append("WHERE m.datafim IS NULL AND (a.pesquisa = 'S' OR a.grava = 'S' OR a.deleta = 'S') ");

        sql.append("AND m.idmenupai IS NOT NULL ");
    	getPerfilAcessoUsuarioDAO().setTransactionControler(this.getTransactionControler());
    	getGrupoDAO().setTransactionControler(this.getTransactionControler());
    	getPerfilAcessoGrupoDAO().setTransactionControler(this.getTransactionControler());

        PerfilAcessoUsuarioDTO perfilAcessoEspecifico =  getPerfilAcessoUsuarioDAO().obterPerfilAcessoUsuario(usuario);

        if (perfilAcessoEspecifico != null) {
            sql.append("AND (a.idperfilacesso = ? ");
            parametros.add(perfilAcessoEspecifico.getIdPerfilAcesso());

            Collection<GrupoDTO> gruposDoEmpregado = getGrupoDAO().getGruposByIdEmpregado(usuario.getIdEmpregado());

            if (gruposDoEmpregado != null && !gruposDoEmpregado.isEmpty()) {
                for (GrupoDTO grupo : gruposDoEmpregado) {
                    PerfilAcessoGrupoDTO perfilAcessoGrupo = getPerfilAcessoGrupoDAO().obterPerfilAcessoGrupo(grupo);

                    if (perfilAcessoGrupo != null) {
                        sql.append("OR a.idperfilacesso = ? ");
                        parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
                    }
                }
            }
            sql.append(")");
        } else {
            Collection<GrupoDTO> gruposDoEmpregado = getGrupoDAO().getGruposByIdEmpregado(usuario.getIdEmpregado());

            if (gruposDoEmpregado != null && !gruposDoEmpregado.isEmpty()) {
                boolean aux = true;
                for (GrupoDTO grupo : gruposDoEmpregado) {
                    PerfilAcessoGrupoDTO perfilAcessoGrupo = getPerfilAcessoGrupoDAO().obterPerfilAcessoGrupo(grupo);

                    if (perfilAcessoGrupo != null) {
                        if (aux) {
                            sql.append("AND (a.idperfilacesso = ? ");
                            parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
                            aux = false;
                        } else {
                            sql.append("OR a.idperfilacesso = ? ");
                            parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
                        }
                    }
                }
                if (!aux) {
                    sql.append(")");
                }
            }
        }

        sql.append(" ORDER BY idmenupai DESC, ordem");
        Object[] paramsFinal = parametros.size() == 0 ? null : parametros.toArray();
        List lista;
        try {
            lista = this.execSQL(sql.toString(), paramsFinal);

            List<String> listRetorno = new ArrayList<>();
            listRetorno.add("idMenu");
            listRetorno.add("idMenuPai");
            listRetorno.add("nome");
            listRetorno.add("dataInicio");
            listRetorno.add("dataFim");
            listRetorno.add("descricao");
            listRetorno.add("ordem");
            listRetorno.add("link");
            listRetorno.add("imagem");
            listRetorno.add("mostrar");

            result = engine.listConvertion(MenuDTO.class, lista, listRetorno);
        } catch (PersistenceException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertListToMap(result);
    }
    
    /**
     * Método para converter uma lista com menus para um Mapa<idMenuPai, List<MenusFilhos> >
     * @author thyen.chang
     * @since 26/01/2015 - OPERAÇÃO USAIN BOLT
     * @param listaMenus
     * @return
     */
    private Map<Integer, List<MenuDTO> > convertListToMap(List<MenuDTO> listaMenus){
    	HashMap<Integer, List<MenuDTO> > mapa = new HashMap<Integer, List<MenuDTO> >();
    	for(MenuDTO menuAux : listaMenus){
    		List<MenuDTO> listaMapa = mapa.get(menuAux.getIdMenuPai());
    		if(listaMapa == null || listaMapa.isEmpty()){
    			listaMapa = new ArrayList<MenuDTO>();
    			listaMapa.add(menuAux);
    		} else {
    			listaMapa.add(menuAux);
    		}
    		mapa.put(menuAux.getIdMenuPai(), listaMapa);
    	}
    	return mapa;
    }
    
}
