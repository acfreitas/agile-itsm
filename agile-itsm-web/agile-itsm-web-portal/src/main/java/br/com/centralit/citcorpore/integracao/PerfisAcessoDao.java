package br.com.centralit.citcorpore.integracao;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.PerfisAcessoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class PerfisAcessoDao extends CrudDaoDefaultImpl {

    public PerfisAcessoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<PerfisAcessoDTO> find(final IDto arg0) throws PersistenceException {
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
    public Collection<PerfisAcessoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<PerfisAcessoDTO> getBean() {
        return PerfisAcessoDTO.class;
    }

}
