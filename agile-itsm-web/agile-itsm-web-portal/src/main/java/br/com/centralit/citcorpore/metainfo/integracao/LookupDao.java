package br.com.centralit.citcorpore.metainfo.integracao;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.metainfo.bean.LookupDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class LookupDao extends CrudDaoDefaultImpl {

    public LookupDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
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
    public Collection<LookupDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<LookupDTO> getBean() {
        return LookupDTO.class;
    }

    @Override
    public Collection<LookupDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List execSQL(final String sql, final Object[] parametros) throws PersistenceException {
        if (sql == null || sql.trim().equalsIgnoreCase("")) {
            return null;
        }
        return super.execSQL(sql, parametros);
    }

}
