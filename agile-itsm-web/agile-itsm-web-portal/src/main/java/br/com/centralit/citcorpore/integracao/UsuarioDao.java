package br.com.centralit.citcorpore.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

public class UsuarioDao extends CrudDaoDefaultImpl {

    public UsuarioDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Class<UsuarioDTO> getBean() {
        return UsuarioDTO.class;
    }

    @Override
    public Collection<UsuarioDTO> find(final IDto dto) throws PersistenceException {
        final List<Order> order = new ArrayList<>();
        order.add(new Order("nomeUsuario"));
        return super.find(dto, order);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("IDUSUARIO", "idUsuario", true, true, false, false));
        listFields.add(new Field("IDUNIDADE", "idUnidade", false, false, false, false));
        listFields.add(new Field("IDEMPREGADO", "idEmpregado", false, false, false, false));
        listFields.add(new Field("IDEMPRESA", "idEmpresa", false, false, false, false));
        listFields.add(new Field("NOME", "nomeUsuario", false, false, false, false));
        listFields.add(new Field("LOGIN", "login", false, false, false, false));
        listFields.add(new Field("SENHA", "senha", false, false, false, false));
        listFields.add(new Field("STATUS", "status", false, false, false, false));
        listFields.add(new Field("LDAP", "ldap", false, false, false, false));
        listFields.add(new Field("ULTIMOACESSOPORTAL", "ultimoAcessoPortal", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "USUARIO";
    }

    @Override
    public void updateNotNull(final IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }

    /**
     * Retorna lista de status de usu·rio.
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public UsuarioDTO listStatus(final UsuarioDTO obj) throws PersistenceException {
        final List<String> parametro = new ArrayList<>();
        final List<String> fields = new ArrayList<>();
        List<?> list = new ArrayList<>();
        final String sql = "select idEmpregado, nome, status, login from " + this.getTableName() + "  where  nome = ? AND status = 'A' ";
        parametro.add(obj.getNomeUsuario());
        list = this.execSQL(sql, parametro.toArray());
        fields.add("idEmpregado");
        fields.add("nomeUsuario");
        fields.add("status");
        if (list != null && !list.isEmpty()) {
            return (UsuarioDTO) this.listConvertion(this.getBean(), list, fields).get(0);
        }
        return null;
    }

    /**
     * Retorna lista de login por usu·rio.
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public UsuarioDTO listLogin(final UsuarioDTO obj) throws PersistenceException {
        final List<String> parametro = new ArrayList<>();
        final List<String> fields = new ArrayList<>();
        List<?> list = new ArrayList<>();
        final String sql = "select login from " + this.getTableName() + " where status = 'A' AND login = ? ";
        parametro.add(obj.getLogin());
        list = this.execSQL(sql, parametro.toArray());
        fields.add("login");
        if (list.isEmpty()) {
            return null;
        }
        return (UsuarioDTO) this.listConvertion(this.getBean(), list, fields).get(0);
    }

    /**
     * Retorna de login que j· existe
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public UsuarioDTO listUsuarioExistente(final UsuarioDTO obj) throws PersistenceException {
        final List<Object> parametro = new ArrayList<>();
        final List<String> fields = new ArrayList<>();
        List<?> list = new ArrayList<>();
        final String sql = "select login from " + this.getTableName() + " where status = 'A' AND login = ? AND idusuario<> ? ";
        parametro.add(obj.getLogin());
        parametro.add(obj.getIdUsuario());
        list = this.execSQL(sql, parametro.toArray());
        fields.add("login");
        if (list.isEmpty()) {
            return null;
        }
        return (UsuarioDTO) this.listConvertion(this.getBean(), list, fields).get(0);
    }

    /**
     * Restaura Usu·rio por Login.
     *
     * @param login
     * @return
     * @throws Exception
     */
    public UsuarioDTO restoreByLogin(final String login) throws PersistenceException {
        final List<Order> ordem = new ArrayList<>();
        ordem.add(new Order("login"));
        ordem.add(new Order("status"));
        final UsuarioDTO usuario = new UsuarioDTO();
        usuario.setLogin(login);
        final List<UsuarioDTO> col = (List<UsuarioDTO>) super.find(usuario, ordem);
        if (col == null || col.size() == 0) {
            return null;
        }
        return col.get(0);
    }

    public UsuarioDTO restoreByID(final Integer id) throws PersistenceException {
        final List<Order> ordem = new ArrayList<>();
        ordem.add(new Order("idUsuario"));
        ordem.add(new Order("status"));
        final UsuarioDTO usuario = new UsuarioDTO();
        usuario.setIdUsuario(id);
        final List<UsuarioDTO> col = (List<UsuarioDTO>) super.find(usuario, ordem);
        if (col == null || col.size() == 0) {
            return null;
        }
        return col.get(0);
    }

    public UsuarioDTO restoreByLoginSenha(final String login, final String senha) throws PersistenceException {

        final StringBuilder sql = new StringBuilder();
        final List<Object> listFields = new ArrayList<>();
        List list = new ArrayList<>();

        final List<String> parametro = new ArrayList<>();

        sql.append("select usr.idUsuario, usr.idUnidade, usr.idEmpregado, usr.idEmpresa, usr.nome as nomeUsuario, usr.login, usr.senha, usr.status, usr.ldap, usr.ultimoAcessoPortal, e.email from usuario usr ");
        sql.append("inner join empregados e on usr.idempregado = e.idempregado ");
        sql.append("where usr.login = ? and usr.senha = ? ");

        parametro.add(login);
        parametro.add(senha);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listFields.add("idUsuario");
        listFields.add("idUnidade");
        listFields.add("idEmpregado");
        listFields.add("idEmpresa");
        listFields.add("nomeUsuario");
        listFields.add("login");
        listFields.add("senha");
        listFields.add("status");
        listFields.add("ldap");
        listFields.add("ultimoAcessoPortal");
        listFields.add("email");

        if (list != null && !list.isEmpty()) {
            return (UsuarioDTO) this.listConvertion(this.getBean(), list, listFields).get(0);
        }
        return null;
    }

    /**
     * Restorna usu·rio por id do colaborador.
     *
     * @param idEmpregado
     * @return
     * @throws Exception
     */
    public UsuarioDTO restoreByIdEmpregado(final Integer idEmpregado) throws PersistenceException {
        final List<Order> ordem = new ArrayList<>();
        ordem.add(new Order("idEmpregado"));
        final UsuarioDTO usuario = new UsuarioDTO();
        usuario.setIdEmpregado(idEmpregado);
        final List<UsuarioDTO> col = (List<UsuarioDTO>) this.find(usuario);
        if (col == null || col.size() == 0) {
            return null;
        }
        return col.get(0);
    }

    public UsuarioDTO restoreAtivoByIdEmpregado(final Integer idEmpregado) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idEmpregado", "=", idEmpregado));
        condicao.add(new Condition("status", "=", "A"));
        ordenacao.add(new Order("idUsuario", Order.DESC));
        final List<UsuarioDTO> col = (List<UsuarioDTO>) super.findByCondition(condicao, ordenacao);
        if (col == null || col.size() == 0) {
            return null;
        }
        return col.get(0);
    }

    /**
     * Restorna usu·rio por id do colaborador.
     *
     * @param idEmpregado
     * @return
     * @throws Exception
     */
    public UsuarioDTO restoreByIdEmpregadosDeUsuarios(final Integer idEmpregado) throws PersistenceException {
        final List<Integer> parametro = new ArrayList<>();
        final List<String> fields = new ArrayList<>();
        List<?> list = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();
        sql.append("select idUsuario from " + this.getTableName() + "  where  idEmpregado = ? and status like 'A'");
        parametro.add(idEmpregado);
        list = this.execSQL(sql.toString(), parametro.toArray());
        fields.add("idUsuario");
        if (list != null && !list.isEmpty()) {
            return (UsuarioDTO) this.listConvertion(this.getBean(), list, fields).get(0);
        }
        return null;
    }

    @Override
    public Collection<UsuarioDTO> list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("nomeUsuario"));
        return super.list(list);
    }

    public boolean listSeVazio() throws PersistenceException {
        final String sql = "select count(*) from usuario";
        final List<String> parametro = new ArrayList<>();

        final List<?> lista = this.execSQL(sql, parametro.toArray());
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("seguencia");
        final List<?> result = engine.listConvertion(this.getBean(), lista, listRetorno);
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO = (UsuarioDTO) result.get(0);
        if (usuarioDTO.getSeguencia() > 0) {
            return true;
        }
        return false;
    }

    public UsuarioDTO findById(final Integer id) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idusuario", "=", id));
        ordenacao.add(new Order("nomeUsuario"));
        final Collection<UsuarioDTO> col = super.findByCondition(condicao, ordenacao);
        if (col == null || col.size() == 0) {
            return null;
        }
        return ((List<UsuarioDTO>) col).get(0);

    }

    public Collection<UsuarioDTO> listAtivos() throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("status", "=", "A"));
        ordenacao.add(new Order("nomeUsuario"));
        return super.findByCondition(condicao, ordenacao);
    }

    /**
     * Restaura Usu·rio por nome do usuario.
     *
     * @param nomeUsuario
     * @return
     * @throws Exception
     * @author thays.araujo
     */
    public UsuarioDTO restoreByNomeUsuario(final String nomeUsuario) throws PersistenceException {
        final List<String> parametro = new ArrayList<>();
        final List<String> fields = new ArrayList<>();
        final String sql = "select idEmpregado, nome, status, login from " + this.getTableName() + "  where nome = ? AND status = 'A' ";

        parametro.add(nomeUsuario);
        final List<?> list = this.execSQL(sql, parametro.toArray());

        fields.add("idEmpregado");
        fields.add("nomeUsuario");
        fields.add("status");

        if (list != null && !list.isEmpty()) {
            return (UsuarioDTO) this.listConvertion(this.getBean(), list, fields).get(0);
        }
        return null;
    }

    /**
     * Realiza consulta por nome atraves do AutoComplete
     *
     * @param razaoSocial
     * @return
     * @throws Exception
     */
    public List<UsuarioDTO> consultarUsuarioPorNomeAutoComplete(String nome) throws PersistenceException {
        if (nome == null) {
            nome = "";
        }

        String texto = Normalizer.normalize(nome, Normalizer.Form.NFD);
        texto = texto.replaceAll("[^\\p{ASCII}]", "");
        texto = texto.replaceAll("·‡„‚ÈÍÌÛÙı˙¸Á¡¿√¬… Õ”‘’⁄‹«¥`^''-+=", "aaaaeeiooouucAAAAEEIOOOUUC ");
        texto = "%" + texto.toUpperCase() + "%";

        final Object[] objs = new Object[] {texto};

        final StringBuilder sql = new StringBuilder("select ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER)) {
            sql.append("TOP 10 ");
        }

        sql.append("idusuario, nome ");
        sql.append("from usuario ");
        sql.append("where ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE)) {
            sql.append("ROWNUM <= 10 AND ");
        }

        sql.append("upper(nome) like upper(?) AND status='A' ");
        sql.append("order by nome");

        if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL)
                || CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL)) {
            sql.append(" LIMIT 10");
        }

        final List<?> list = this.execSQL(sql.toString(), objs);

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idUsuario");
        listRetorno.add("nomeUsuario");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    /**
     * Retorna a quantidade de usu·rios ativos no sistema
     *
     * @return Long
     * @throws Exception
     * @author renato.jesus
     */
    public Long retornaQuantidadeUsuariosAtivos() throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) total FROM usuario WHERE status='A'");

        final List lista = this.execSQL(sql.toString(), null);

        Long qtdeUsu = 0L;

        if (lista != null && !lista.isEmpty() && lista.get(0) != null) {
            // Pega o primeiro item da lista e converte para Array, depois pega a primeira posiÁ„o que È o Long
            qtdeUsu = (Long) ((Object[]) lista.get(0))[0];
        }

        return qtdeUsu;
    }

}
