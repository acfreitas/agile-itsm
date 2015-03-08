package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citquestionario.bean.ExecutaSQLDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class ExecutaSQLDao extends CrudDaoDefaultImpl {

    public ExecutaSQLDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<ExecutaSQLDTO> find(final IDto arg0) throws PersistenceException {
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
    public Collection<ExecutaSQLDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<ExecutaSQLDTO> getBean() {
        return ExecutaSQLDTO.class;
    }

    public Collection executaSQL(final String sql) throws PersistenceException {
        if (sql != null) {
            new ArrayList<>();
            final List lista = this.execSQL(sql, null);

            final List listRetorno = new ArrayList<>();
            listRetorno.add("value");
            listRetorno.add("description");

            engine.listConvertion(this.getBean(), lista, listRetorno);
        }
        return null;
    }

}
