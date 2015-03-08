package br.com.centralit.citgerencial.integracao;

import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class GerencialGenerateDao extends CrudDaoDefaultImpl {

    public GerencialGenerateDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<?> find(final IDto arg0) throws PersistenceException {
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

    @Override
    public Collection<?> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<?> getBean() {
        return null;
    }

    public Collection executaSQL(final String sql, final Object[] list) throws Exception {
        return this.execSQL(sql, list);
    }

}
