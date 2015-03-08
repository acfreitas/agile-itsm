package br.com.centralit.citsmart.rest.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citsmart.rest.bean.RestLogDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RestLogDao extends CrudDaoDefaultImpl {

    public RestLogDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idRestLog", "idRestLog", true, true, false, false));
        listFields.add(new Field("idRestExecution", "idRestExecution", false, false, false, false));
        listFields.add(new Field("dateTime", "dateTime", false, false, false, false));
        listFields.add(new Field("status", "status", false, false, false, false));
        listFields.add(new Field("resultData", "resultData", false, false, false, false));
        listFields.add(new Field("resultClass", "resultClass", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "Rest_Log";
    }

    @Override
    public Collection<RestLogDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<RestLogDTO> getBean() {
        return RestLogDTO.class;
    }

    @Override
    public Collection<RestLogDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection<RestLogDTO> findByIdRestExecution(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idRestExecution", "=", parm));
        ordenacao.add(new Order("idRestLog"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdRestExecution(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idRestExecution", "=", parm));
        super.deleteByCondition(condicao);
    }

}
