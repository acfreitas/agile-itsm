package br.com.centralit.citsmart.rest.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citsmart.rest.bean.RestParameterDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RestParameterDao extends CrudDaoDefaultImpl {

    public RestParameterDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idRestParameter", "idRestParameter", true, true, false, false));
        listFields.add(new Field("identifier", "identifier", false, false, false, false));
        listFields.add(new Field("description", "description", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "Rest_Parameter";
    }

    @Override
    public Collection<RestParameterDTO> list() throws PersistenceException {
        final List<Order> ordenacao = new ArrayList<>();
        ordenacao.add(new Order("description"));
        return super.list(ordenacao);
    }

    @Override
    public Class<RestParameterDTO> getBean() {
        return RestParameterDTO.class;
    }

    @Override
    public Collection<RestParameterDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public RestParameterDTO findByIdentifier(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("identifier", "=", parm));
        ordenacao.add(new Order("idRestParameter"));
        final List<RestParameterDTO> list = (List<RestParameterDTO>) super.findByCondition(condicao, ordenacao);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

}
