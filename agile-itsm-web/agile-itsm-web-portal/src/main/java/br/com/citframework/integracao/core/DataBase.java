package br.com.citframework.integracao.core;

import org.apache.commons.lang.StringUtils;

/**
 * Enumerado de SBGDs que são suportados
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 02/10/2014
 *
 */
public enum DataBase {

    MYSQL(1, "mysql", "MySQL"),
    MSSQLSERVER(2, "sqlserver", "Microsoft SQL Server"),
    ORACLE(3, "oracle", "Oracle Database"),
    POSTGRESQL(4, "postgres", "PostgreSQL");

    private Integer id;
    private String stringId;
    private String description;

    private DataBase(final int id, final String stringId, final String description) {
        this.id = id;
        this.stringId = stringId;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getStringId() {
        return stringId;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Recupera um {@link DataBase} de acordo com o id
     *
     * @param id
     *            identificador numérico/sequencial do SGBD
     * @return {@link DataBase} de acordo com o id. {@link IllegalArgumentException}, caso contrário
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 02/10/2014
     */
    public static DataBase fromId(final Integer id) {
        final DataBase[] values = DataBase.values();
        for (final DataBase value : values) {
            if (id.equals(value.getId())) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("DataBase not found for Id '%s'", id));
    }

    /**
     * Recupera um {@link DataBase} de acordo com o stringId
     *
     * @param stringId
     *            nome identificador do SGBD
     * @return {@link DataBase} de acordo com o stringId. {@link IllegalArgumentException}, caso contrário
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 02/10/2014
     */
    public static DataBase fromStringId(final String stringId) {
        final DataBase[] values = DataBase.values();
        for (final DataBase value : values) {
            if (StringUtils.equalsIgnoreCase(stringId, value.getStringId())) {
                return value;
            }
        }
        throw new IllegalArgumentException(String.format("DataBase not found for stringId '%s'", stringId));
    }

}
