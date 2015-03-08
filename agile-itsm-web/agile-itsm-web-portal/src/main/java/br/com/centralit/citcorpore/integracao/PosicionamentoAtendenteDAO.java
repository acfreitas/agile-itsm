package br.com.centralit.citcorpore.integracao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PosicionamentoAtendenteDTO;
import br.com.centralit.citcorpore.bean.result.PosicionamentoAtendenteResultDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.core.DataBase;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

/**
 * DAO para persistência de {@link PosicionamentoAtendenteDTO}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 06/10/2014
 *
 */
public class PosicionamentoAtendenteDAO extends CrudDaoDefaultImpl {

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";

    public PosicionamentoAtendenteDAO() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final List<Field> fields = new ArrayList<>();
        fields.add(new Field("id", "id", true, true, false, true));
        fields.add(new Field("latitude", "latitude", false, false, false, false));
        fields.add(new Field("longitude", "longitude", false, false, false, false));
        fields.add(new Field("dateTime", "dateTime", false, false, false, false));
        fields.add(new Field("idusuario", "idUsuario", false, false, false, false));
        return fields;
    }

    @Override
    public String getTableName() {
        return "posicionamentoatendente";
    }

    @Override
    public Collection<PosicionamentoAtendenteDTO> find(final IDto posicionamento) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<PosicionamentoAtendenteDTO> list() throws PersistenceException {
        final List<Order> ordenacao = new ArrayList<>();
        ordenacao.add(new Order("id"));
        ordenacao.add(new Order("dateTime"));
        return super.list(ordenacao);
    }

    public List<PosicionamentoAtendenteResultDTO> listLastLocationWithSolicitationInfo(final PosicionamentoAtendenteDTO posicionamento) throws PersistenceException {
        final List<Object> parametros = new ArrayList<>();
        final String query = this.getSQLListagem(posicionamento, parametros).toString();
        final Object[] parametrosArray = parametros.toArray();
        final List<?> dados = this.execSQL(query, parametrosArray);
        return this.listConvertion(PosicionamentoAtendenteResultDTO.class, dados, this.getFieldsToReturn());
    }

    private StringBuilder getSQLListagem(final PosicionamentoAtendenteDTO posicionamento, final List<Object> parametros) {
        final DataBase db = DataBase.fromStringId(CITCorporeUtil.SGBD_PRINCIPAL);

        final Integer idContrato = posicionamento.getIdContrato();
        final Integer idGrupo = posicionamento.getIdGrupo();
        final Integer idUnidade = posicionamento.getIdUnidade();
        final Integer idAtendente = posicionamento.getIdAtendente();
        final Timestamp dataTimeInicio = posicionamento.getTimestampInicio();
        final Timestamp dataTimeFim = posicionamento.getTimestampFim();

        final boolean hasIdContrato = idContrato != null && idContrato.intValue() > 0;
        final boolean hasIdGrupo = idGrupo != null && idGrupo.intValue() > 0;
        final boolean hasIdUnidade = idUnidade != null && idUnidade.intValue() > 0;
        final boolean hasIdAtendente = idAtendente != null && idAtendente.intValue() > 0;

        final StringBuilder sql = new StringBuilder();

        sql.append("SELECT DISTINCT sol.idsolicitacaoservico AS numeroSolicitacao, ");
        sql.append("       sol.prazohh, ");
        sql.append("       sol.prazomm, ");
        sql.append("       sol.situacao, ");

        if (db.equals(DataBase.ORACLE)) {
            sql.append("       dbms_lob.SUBSTR");
        } else {
            sql.append("       SUBSTRING");
        }

        sql.append("(sol.descricao, 1, 100)     AS descricao, ");

        sql.append("       pos.idusuario, ");
        sql.append("       pos.latitude, ");
        sql.append("       pos.longitude, ");
        sql.append("       pos.dateTime         AS ultimaVisualizacao, ");
        sql.append("       emp.nome             AS nomeAtendente, ");
        sql.append("       chkin.datahoracheckin ");
        sql.append("FROM   posicionamentoatendente pos ");
        sql.append("       INNER JOIN (SELECT idusuario, ");
        sql.append("                          MAX(datetime) AS maxdatetime ");
        sql.append("                   FROM   posicionamentoatendente ");
        sql.append("                   GROUP  BY idusuario) grouped ");
        sql.append("               ON pos.idusuario = grouped.idusuario ");
        sql.append("       RIGHT JOIN usuario usu ");
        sql.append("               ON usu.idusuario = pos.idusuario ");
        sql.append("       LEFT JOIN empregados emp ");
        sql.append("              ON usu.idempregado = emp.idempregado ");
        sql.append("       LEFT JOIN checkin chkin ");
        sql.append("              ON chkin.idusuario = pos.idusuario ");
        sql.append("       LEFT JOIN solicitacaoservico sol ");
        sql.append("              ON chkin.idsolicitacao = sol.idsolicitacaoservico ");

        if (hasIdContrato) {
            sql.append("       LEFT JOIN unidade unid ");
            sql.append("              ON unid.idunidade = emp.idunidade ");
            sql.append("       LEFT JOIN contratosunidades contunid ");
            sql.append("              ON contunid.idunidade = unid.idunidade ");
            sql.append("       LEFT JOIN contratos con ");
            sql.append("              ON con.idcontrato = contunid.idcontrato ");
        }

        if (hasIdGrupo) {
            sql.append("       LEFT JOIN gruposempregados gemp ");
            sql.append("              ON gemp.idempregado = emp.idempregado ");
        }

        sql.append("WHERE  ");
        if (db.equals(DataBase.ORACLE)) {
            sql.append("       TO_CHAR(pos.datetime, 'YYYY-MM-DD HH24:MI:SS') BETWEEN ? AND ? ");
            parametros.add(UtilDatas.dateToSTRWithFormat(dataTimeInicio, YYYY_MM_DD_HH_MM_SS));
            parametros.add(UtilDatas.dateToSTRWithFormat(dataTimeFim, YYYY_MM_DD_HH_MM_SS));
        } else {
            sql.append("       pos.datetime BETWEEN ? AND ? ");
            parametros.add(dataTimeInicio);
            parametros.add(dataTimeFim);
        }

        sql.append("       AND pos.datetime = grouped.maxdatetime ");

        if (hasIdContrato) {
            sql.append("       AND con.idcontrato = ? ");
            parametros.add(idContrato);
        }

        if (hasIdGrupo) {
            sql.append("       AND gemp.idgrupo = ? ");
            parametros.add(idGrupo);
        }

        if (hasIdUnidade) {
            sql.append("       AND unid.idunidade = ? ");
            parametros.add(idUnidade);
        }

        if (hasIdAtendente) {
            sql.append("       AND emp.idempregado = ? ");
            parametros.add(idAtendente);
        }

        sql.append("ORDER  BY pos.idusuario, ");
        sql.append("          pos.datetime, ");
        sql.append("          sol.idsolicitacaoservico DESC, ");
        sql.append("          chkin.datahoracheckin ");

        return sql;
    }

    @Override
    public Class<PosicionamentoAtendenteDTO> getBean() {
        return PosicionamentoAtendenteDTO.class;
    }

    private List<String> fields;

    private List<String> getFieldsToReturn() {
        if (fields == null) {
            fields = new ArrayList<>();
            fields.add("numeroSolicitacao");
            fields.add("prazoHH");
            fields.add("prazoMM");
            fields.add("situacao");
            fields.add("descricao");
            fields.add("idUsuario");
            fields.add("latitude");
            fields.add("longitude");
            fields.add("ultimaVisualizacao");
            fields.add("nomeAtendente");
        }
        return fields;
    }

}
