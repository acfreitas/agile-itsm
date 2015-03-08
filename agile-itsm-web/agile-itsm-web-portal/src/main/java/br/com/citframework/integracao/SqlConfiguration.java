package br.com.citframework.integracao;

import java.io.Serializable;
import java.util.List;

public class SqlConfiguration implements Serializable {

    private static final long serialVersionUID = -5645039604033764091L;

    private String sql;
    private Object[] parametros;
    private List camposRetorno;

    public SqlConfiguration(final String sql, final Object[] parametros, final List camposRetorno) {
        this.sql = sql;
        this.parametros = parametros;
        this.camposRetorno = camposRetorno;
    }

    public SqlConfiguration(final String sql, final Object[] parametros) {
        this.sql = sql;
        this.parametros = parametros;
    }

    public List getCamposRetorno() {
        return camposRetorno;
    }

    public void setCamposRetorno(final List camposRetorno) {
        this.camposRetorno = camposRetorno;
    }

    public Object[] getParametros() {
        return parametros;
    }

    public void setParametros(final Object[] parametros) {
        this.parametros = parametros;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(final String sql) {
        this.sql = sql;
    }

}
