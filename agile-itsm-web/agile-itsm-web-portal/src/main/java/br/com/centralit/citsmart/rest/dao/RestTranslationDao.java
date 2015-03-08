package br.com.centralit.citsmart.rest.dao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestTranslationDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class RestTranslationDao extends CrudDaoDefaultImpl {

    public RestTranslationDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idRestTranslation", "idRestTranslation", true, true, false, false));
        listFields.add(new Field("idRestOperation", "idRestOperation", false, false, false, false));
        listFields.add(new Field("idBusinessObject", "idBusinessObject", false, false, false, false));
        listFields.add(new Field("fromValue", "fromValue", false, false, false, false));
        listFields.add(new Field("toValue", "toValue", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "Rest_Translation";
    }

    @Override
    public Collection<RestTranslationDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<RestTranslationDTO> getBean() {
        return RestTranslationDTO.class;
    }

    @Override
    public Collection<RestTranslationDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

}
