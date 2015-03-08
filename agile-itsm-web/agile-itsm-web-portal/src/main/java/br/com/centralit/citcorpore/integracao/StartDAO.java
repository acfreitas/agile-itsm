package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.StartDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class StartDAO extends CrudDaoDefaultImpl {

    public StartDAO() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<StartDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idStart", "idStart", true, true, false, false));
        listFields.add(new Field("passoInstalacao", "passoInstalacao", false, false, false, false));
        listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
        listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "start";
    }

    @Override
    public Collection<StartDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<StartDTO> getBean() {
        return StartDTO.class;
    }

}
