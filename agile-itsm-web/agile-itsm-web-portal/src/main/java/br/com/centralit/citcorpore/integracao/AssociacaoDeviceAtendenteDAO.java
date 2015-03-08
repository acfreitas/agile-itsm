package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AssociacaoDeviceAtendenteDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * DAO para persistência de {@link AssociacaoDeviceAtendenteDTO}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 15/11/2014
 */
public class AssociacaoDeviceAtendenteDAO extends CrudDaoDefaultImpl {

    public AssociacaoDeviceAtendenteDAO() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<AssociacaoDeviceAtendenteDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<AssociacaoDeviceAtendenteDTO> list() throws PersistenceException {
        final List<Order> ordenacao = new ArrayList<>();
        ordenacao.add(new Order("id"));
        ordenacao.add(new Order("idusuario"));
        return super.list(ordenacao);
    }

    @Override
    public Collection<Field> getFields() {
        final List<Field> fields = new ArrayList<>();
        fields.add(new Field("id", "id", true, true, false, true));
        fields.add(new Field("idusuario", "idUsuario", false, false, false, false));
        fields.add(new Field("token", "token", false, false, false, false));
        fields.add(new Field("connection", "connection", false, false, false, false));
        fields.add(new Field("deviceplatform", "devicePlatform", false, false, false, false));
        fields.add(new Field("active", "active", false, false, false, false));
        return fields;
    }

    /**
     * Lista {@link AssociacaoDeviceAtendenteDTO} ativos de mesmo token, conexão, device e usuário
     *
     * @param associacao
     *            {@link AssociacaoDeviceAtendenteDTO} contendo os dados
     * @return {@link AssociacaoDeviceAtendenteDTO} deverá ser apenas um
     * @throws Exception
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @date 17/11/2014
     */
    public List<AssociacaoDeviceAtendenteDTO> listActiveWithSameProperties(final AssociacaoDeviceAtendenteDTO associacao) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT asso.id, ");
        sql.append("       asso.idusuario, ");
        sql.append("       asso.connection, ");
        sql.append("       asso.token, ");
        sql.append("       asso.devicePlatform, ");
        sql.append("       emp.nome AS nomeAtendente ");
        sql.append("FROM   associacaodeviceatendente AS asso ");
        sql.append("       LEFT JOIN usuario usu ");
        sql.append("              ON usu.idusuario = asso.idusuario ");
        sql.append("       LEFT JOIN empregados emp ");
        sql.append("              ON emp.idempregado = usu.idempregado ");
        sql.append("WHERE  asso.idusuario = ? ");
        sql.append("       AND asso.token = ? ");
        sql.append("       AND UPPER(asso.connection) = UPPER(?) ");
        sql.append("       AND asso.active = ?");

        final Object[] params = new Object[] {associacao.getIdUsuario(), associacao.getToken(), associacao.getConnection(), 1};
        final List<?> result = this.execSQL(sql.toString(), params);
        List<AssociacaoDeviceAtendenteDTO> associacoes = new ArrayList<>();
        if (result.size() > 0) {
            final List<String> fields = new ArrayList<>();
            fields.add("id");
            fields.add("idUsuario");
            fields.add("connection");
            fields.add("token");
            fields.add("devicePlatform");
            fields.add("nomeAtendente");
            associacoes = this.listConvertion(this.getBean(), result, fields);
        }

        return associacoes;
    }

    /**
     * Lista {@link AssociacaoDeviceAtendenteDTO} ativas de acordo com o usuário informado
     *
     * @param usuario
     *            usuário para o qual serão listadas as associações
     * @param connection
     *            "conexão" no mobile, que é a URI acessada
     * @return lista de {@link AssociacaoDeviceAtendenteDTO} ativas do usuário
     * @throws Exception
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @date 17/11/2014
     */
    public List<AssociacaoDeviceAtendenteDTO> listActiveAssociationForUser(final UsuarioDTO usuario, final String connection) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT asso.id, ");
        sql.append("       asso.idusuario, ");
        sql.append("       asso.token, ");
        sql.append("       asso.connection, ");
        sql.append("       asso.devicePlatform, ");
        sql.append("       asso.active ");
        sql.append("FROM   associacaodeviceatendente AS asso ");
        sql.append("WHERE  asso.idusuario = ? ");
        sql.append("       AND UPPER(asso.connection) = UPPER(?) ");
        sql.append("       AND asso.active = ?");

        final Object[] params = new Object[] {usuario.getIdUsuario(), connection, 1};
        final List<?> result = this.execSQL(sql.toString(), params);
        List<AssociacaoDeviceAtendenteDTO> associacoes = new ArrayList<>();
        if (result.size() > 0) {
            associacoes = this.listConvertion(this.getBean(), result, (List<Field>) this.getFields());
        }

        return associacoes;
    }

    @Override
    public String getTableName() {
        return "associacaodeviceatendente";
    }

    @Override
    public Class<AssociacaoDeviceAtendenteDTO> getBean() {
        return AssociacaoDeviceAtendenteDTO.class;
    }

}
