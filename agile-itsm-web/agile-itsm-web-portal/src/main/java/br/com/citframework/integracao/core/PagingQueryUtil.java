package br.com.citframework.integracao.core;

import org.apache.commons.lang.StringUtils;

import br.com.citframework.util.Assert;

/**
 * Utilit�rio para cria��o de queries que incluam par�metros para pagina��o
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 02/10/2014
 *
 */
public final class PagingQueryUtil {

    private PagingQueryUtil() {}

    private static final String PAGINATION_MYSQL_PATTERN = "LIMIT %s, %s";
    private static final String PAGINATION_POSTGRES_PATTERN = "LIMIT %s OFFSET %s";

    /**
     * Concatena em uma query o trecho para pagina��o, de acordo com o SGBD
     *
     * @param pageable
     *            informa��o da pagina��o
     * @param query
     *            query a ser concatenada a parte para pagina��o
     * @param dataBase
     *            informa��o da base de dados para gera��o do trecho de pagina��o
     * @return
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 02/10/2014
     */
    public static String concatPagingPieceOnQuery(final Pageable pageable, final String query, final DataBase dataBase) {
        Assert.notNull(pageable, "Pageable information must not be null.");
        Assert.notNullAndNotEmpty(query, "Query must not be null or empty.");
        Assert.notNull(dataBase, "Database information must not be null.");
        String result = null;
        switch (dataBase) {
        case ORACLE:
            result = constructsOraclePagingPiece(pageable, query);
            break;
        case MSSQLSERVER:
            result = constructsSQLServerPagingPiece(pageable, query);
            break;
        case POSTGRESQL:
        case MYSQL:
            result = constructsPostgreSQLAndMySQLPagingPiece(pageable, query, dataBase);
            break;
        }
        return result;
    }

    /**
     * Gera o trecho de query para pagina��o para {@code Oracle}
     *
     * @param pageable
     *            informa��o da pagina��o
     * @param query
     *            query a ser concatenada a parte para pagina��o
     * @return query para ser executado, j� com o padr�o de pagina��o para o banco
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @date 02/10/2014
     */
    private static String constructsOraclePagingPiece(final Pageable pageable, final String query) {
        int lastElement = 0;
        int pageNumber = pageable.getPageNumber();
        final int pageSize = pageable.getPageSize();
        if (pageNumber > 1) {
            lastElement = pageSize * pageNumber;
            pageNumber = pageNumber * pageSize - pageSize;
            pageNumber = pageNumber + 1;
        } else {
            lastElement = pageSize;
        }

        final StringBuilder oracle = new StringBuilder();
        oracle.append("SELECT * FROM (");
        oracle.append("    SELECT PAGING.*, ROWNUM PAGING_RN FROM ( ");
        oracle.append(query);
        oracle.append("    ) ");
        oracle.append("    PAGING WHERE (");
        oracle.append("        ROWNUM <= ");
        oracle.append(lastElement);
        oracle.append("    )");
        oracle.append(") ");
        oracle.append("    WHERE (");
        oracle.append("        PAGING_RN >= ");
        oracle.append(pageNumber);
        oracle.append("    ) ");

        return oracle.toString();
    }

    /**
     * Gera o trecho de query para pagina��o para {@code PotgreSQL} e {@code MySQL}
     *
     * @param pageable
     *            informa��o da pagina��o
     * @param query
     *            query a ser concatenada a parte para pagina��o
     * @param dataBase
     *            informa��o da base de dados para gera��o do trecho de pagina��o
     * @return query para ser executado, j� com o padr�o de pagina��o para o banco
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 02/10/2014
     */
    private static String constructsPostgreSQLAndMySQLPagingPiece(final Pageable pageable, final String query, final DataBase dataBase) {
        final int pageNumber = pageable.getPageNumber();
        final int pageSize = pageable.getPageSize();

        int offset;
        if (pageNumber == 0) {
            offset = pageNumber;
        } else {
            offset = pageNumber * pageSize;
        }

        final StringBuilder sql = new StringBuilder(query).append(" ");

        switch (dataBase) {
        case POSTGRESQL:
            sql.append(String.format(PAGINATION_POSTGRES_PATTERN, pageSize, offset));
            break;
        case MYSQL:
            sql.append(String.format(PAGINATION_MYSQL_PATTERN, offset, pageSize));
            break;
        }

        return sql.toString();
    }

    /**
     * Gera o trecho de query para pagina��o para {@code Microsoft SQL Server}
     *
     * @param pageable
     *            informa��o da pagina��o
     * @param query
     *            query a ser concatenada a parte para pagina��o
     * @return query para ser executado, j� com o padr�o de pagina��o para o banco
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @date 02/10/2014
     */
    private static String constructsSQLServerPagingPiece(final Pageable pageable, final String query) {
        final int querySize = query.length();
        final int fromIndex = StringUtils.indexOfIgnoreCase(query, "from");
        final int orderIndex = StringUtils.indexOfIgnoreCase(query, "order", fromIndex);
        final String selectPortion = query.substring(0, fromIndex);
        final String fromPortion = query.substring(fromIndex, orderIndex);
        final String orderPortion = query.substring(orderIndex, querySize - 1);
        return constructsSQLServerPagingPiece(pageable, selectPortion, orderPortion, fromPortion);
    }

    /**
     * TODO FIXME este m�todo foi criado por que anteriormente n�o estava previsto forma��es mais elaboradas de queries.<br>
     *
     * Gera o trecho de query para pagina��o para {@code Microsoft SQL Server}
     *
     * @param pageable
     *            informa��o da pagina��o
     * @param selectPortion
     *            trecho da query contendo apenas os campos a serem filtrados {@code SELECT}
     * @param orderPortion
     *            trecho da query contendo apenas a cl�usula {@code ORDER BY} e os campos para filtro
     * @param fromPortion
     *            trecho da query contendo apenas as cl�sulas {@code FROM} e {@code WHERE}
     * @return query para ser executado, j� com o padr�o de pagina��o para o banco
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 29/01/2015
     */
    public static String constructsSQLServerPagingPiece(final Pageable pageable, final String selectPortion, final String orderPortion, final String fromPortion) {
        int firstElement = pageable.getPageNumber();
        int lastElement = pageable.getPageSize();

        final int pageNumber = pageable.getPageNumber();
        final int pageSize = pageable.getPageSize();

        if (pageNumber > 0) {
            firstElement = pageNumber * pageSize;
            lastElement = firstElement + pageSize;
        }

        final StringBuilder sqlServer = new StringBuilder();
        sqlServer.append(" ;WITH TempTable AS ( ");
        sqlServer.append(selectPortion);
        sqlServer.append(" , ROW_NUMBER() ");
        sqlServer.append(" OVER ( ");
        sqlServer.append(orderPortion);
        sqlServer.append(" ) ");
        sqlServer.append(" AS Row ");
        sqlServer.append(fromPortion);
        sqlServer.append(" ) SELECT * FROM TempTable WHERE Row > ");
        sqlServer.append(firstElement);
        sqlServer.append(" and Row <= ");
        sqlServer.append(lastElement);

        return sqlServer.toString();
    }

}
