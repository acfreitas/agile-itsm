package br.com.citframework.integracao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.citframework.util.Constantes;

public class MetaDataDao extends DaoTransactDefaultImpl {

    public MetaDataDao() {
        super(Constantes.getValue("CONEXAO_DEFAULT"), null);
    }

    @Override
    public Class<?> getBean() {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        return null;
    }

    @Override
    public String getTableName() {
        return null;
    }

    public Collection getCamposByTabela(final String nomeTabela) throws Exception {
        final TransactionControler tc = this.getTransactionControler();
        final Connection con = tc.getConnection();
        final DatabaseMetaData dbmd = con.getMetaData();
        final ResultSet rs = dbmd.getColumns(null, null, nomeTabela, null);
        final List<Object> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(rs.getString("COLUMN_NAME"));
        }
        rs.close();
        tc.close();
        return lista;
    }

}
