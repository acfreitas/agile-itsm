package br.com.citframework.integracao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.LogDados;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({"rawtypes", "unchecked"})
public class LogDadosDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "logdados";

    @Override
    public synchronized IDto create(final IDto obj) throws PersistenceException {
        LogDados logDados = new LogDados();

        if (obj != null) {

            logDados = (LogDados) obj;

            final StringBuilder sql = new StringBuilder();

            final List parametro = new ArrayList<>();

            sql.append("INSERT INTO logdados (dtatualizacao, operacao, dados, idusuario, localorigem, nometabela, datalog) VALUES (?, ?, ?, ?, ?, ?, ?)");

            parametro.add(UtilDatas.getDataAtual());
            parametro.add(logDados.getOperacao());
            parametro.add(logDados.getDados());
            parametro.add(logDados.getIdUsuario());
            parametro.add(logDados.getLocalOrigem());
            parametro.add(logDados.getNomeTabela());
            parametro.add(logDados.getDataLog());

            this.execUpdate(sql.toString(), parametro.toArray());
        }

        return logDados;
    }

    @Override
    public synchronized IDto createWithID(final IDto obj) throws PersistenceException {
        return super.createWithID(obj);
    }

    private static final Integer LIMIT = 50;

    public LogDadosDao(final Usuario usuario) {
        super(Constantes.getValue("DATABASE_ALIAS"), usuario);
    }

    @Override
    public Collection find(final IDto dto) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final List<Field> lista = new ArrayList<>();
        lista.add(new Field("idlog", "idlog", true, true, false, false));
        lista.add(new Field("dtAtualizacao", "dtAtualizacao", false, false, false, false));
        lista.add(new Field("operacao", "operacao", false, false, false, false));
        lista.add(new Field("dados", "dados", false, false, false, false));
        lista.add(new Field("idUsuario", "idUsuario", false, false, false, false));
        lista.add(new Field("localOrigem", "localOrigem", false, false, false, false));
        lista.add(new Field("nomeTabela", "nomeTabela", false, false, false, false));
        lista.add(new Field("dataLog", "dataLog", false, false, false, false));
        return lista;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List<Order> ordenacao = new ArrayList<>();
        ordenacao.add(new Order("dtAtualizacao"));
        return this.list(ordenacao);
    }

    public Collection<LogDados> listAllLogs() throws Exception {
        final StringBuilder sql = new StringBuilder();
        final List parametro = new ArrayList<>();
        final List fields = new ArrayList<>();
        List list = new ArrayList<>();
        String orderBy = "";
        orderBy = "order by idlog DESC ";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {

            sql.append(" select nome, nometabela, operacao, dados, datalog from (select ROW_NUMBER() OVER(" + orderBy + ") as RowNum, ");
            sql.append(" usuario.nome, logdados.nometabela, logdados.operacao, logdados.dados, logdados.datalog " + "FROM  " + this.getTableName() + " ");
            sql.append(" logdados INNER JOIN usuario usuario ON usuario.idUsuario = logdados.idUsuario " + "WHERE dtAtualizacao BETWEEN ? AND ? ");

            sql.append(")  as table2 where table2.RowNum between 0 and " + LIMIT + " ");
        } else {
            sql.append("SELECT usuario.nome, logdados.nometabela, logdados.operacao, logdados.dados, logdados.datalog " + "FROM  " + this.getTableName()
                    + " logdados INNER JOIN usuario usuario ON usuario.idUsuario = logdados.idUsuario " + "WHERE dtAtualizacao BETWEEN ? AND ? ");
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append(" AND ROWNUM <= " + LIMIT + " " + orderBy + "");
        } else {
            if (!CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
                sql.append(" " + orderBy + " LIMIT " + LIMIT);
            }
        }

        list = this.execSQL(sql.toString(), parametro.toArray());
        fields.add("nomeUsuario");
        fields.add("nomeTabela");
        fields.add("operacao");
        fields.add("dados");
        fields.add("dataLog");
        if (list != null && !list.isEmpty()) {
            return this.listConvertion(this.getBean(), list, fields);
        } else {
            return null;
        }
    }

    public Collection<LogDados> listLogs(final LogDados log) throws Exception {
        final List parametro = new ArrayList<>();
        final List fields = new ArrayList<>();
        List list = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();
        String orderBy = "";
        orderBy = "order by idlog DESC ";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" select nome, nometabela, operacao, dados, datalog from (select ROW_NUMBER() OVER(" + orderBy + ") as RowNum, ");
            sql.append(" usuario.nome, logdados.nometabela, logdados.operacao, logdados.dados, logdados.datalog " + "FROM  " + this.getTableName() + " ");
            sql.append(" logdados INNER JOIN usuario usuario ON usuario.idUsuario = logdados.idUsuario " + "WHERE dtAtualizacao BETWEEN ? AND ? ");
            if (log.getIdUsuario() != null && !log.getIdUsuario().equals("")) {
                sql.append(" AND logdados.idUsuario = " + log.getIdUsuario());
            }
            sql.append(")  as table2 where table2.RowNum between 0 and " + LIMIT + " ");
        } else {
            sql.append("SELECT usuario.nome, logdados.nometabela, logdados.operacao, logdados.dados, logdados.datalog " + "FROM  " + this.getTableName()
                    + " logdados INNER JOIN usuario usuario ON usuario.idUsuario = logdados.idUsuario " + "WHERE dtAtualizacao BETWEEN ? AND ? ");
        }

        if (log.getNomeTabela() != null && !log.getNomeTabela().equals("")) {
            sql.append(" AND upper(nomeTabela) = '" + log.getNomeTabela().trim() + "'");
        }

        if (!CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            if (log.getIdUsuario() != null) {
                sql.append(" AND logdados.idUsuario = " + log.getIdUsuario());
            }

        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append(" AND ROWNUM <= " + LIMIT + " " + orderBy + "");
        } else {
            if (!CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
                sql.append(" " + orderBy + " LIMIT " + LIMIT);
            }

        }

        parametro.add(log.getDataInicio());
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            parametro.add(log.getDataFim());
        } else {
            // As linhas abaixo foram necessarias para a hora nao ser considerada String no banco de dados PostgreSQL.
            final String DATA = log.getDataFim().toString() + " 23:59:59";
            final String pattern = "yyyy-MM-dd hh:mm:ss";
            final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            final java.util.Date d = sdf.parse(DATA);
            final java.sql.Timestamp sqlDate = new java.sql.Timestamp(d.getTime());
            parametro.add(sqlDate);
        }
        list = this.execSQL(sql.toString(), parametro.toArray());
        fields.add("nomeUsuario");
        fields.add("nomeTabela");
        fields.add("operacao");
        fields.add("dados");
        fields.add("dataLog");
        if (list != null && !list.isEmpty()) {
            return this.listConvertion(this.getBean(), list, fields);
        } else {
            return null;
        }
    }

    public Collection<LogDados> listNomeTabela() throws Exception {
        final List parametro = new ArrayList<>();
        final List fields = new ArrayList<>();
        List list = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("SELECT DISTINCT UPPER(NOMETABELA) FROM " + this.getTableName() + " ORDER BY UPPER(NOMETABELA)");
        } else {
            sql.append("SELECT DISTINCT UPPER(nometabela) FROM " + this.getTableName() + " ORDER BY UPPER(nomeTabela) ASC");
        }
        list = this.execSQL(sql.toString(), parametro.toArray());
        fields.add("nomeTabela");
        if (list != null && !list.isEmpty()) {
            return this.listConvertion(this.getBean(), list, fields);
        } else {
            return null;
        }
    }

    @Override
    public Class<LogDados> getBean() {
        return LogDados.class;
    }

}
