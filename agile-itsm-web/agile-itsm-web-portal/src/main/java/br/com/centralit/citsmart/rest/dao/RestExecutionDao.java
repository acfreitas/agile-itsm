package br.com.centralit.citsmart.rest.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RestExecutionDao extends CrudDaoDefaultImpl {

    public RestExecutionDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idRestExecution", "idRestExecution", true, true, false, false));
        listFields.add(new Field("idRestOperation", "idRestOperation", false, false, false, false));
        listFields.add(new Field("idUser", "idUser", false, false, false, false));
        listFields.add(new Field("inputDateTime", "inputDateTime", false, false, false, false));
        listFields.add(new Field("inputClass", "inputClass", false, false, false, false));
        listFields.add(new Field("inputData", "inputData", false, false, false, false));
        listFields.add(new Field("status", "status", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "Rest_Execution";
    }

    @Override
    public Collection<RestExecutionDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<RestExecutionDTO> getBean() {
        return RestExecutionDTO.class;
    }

    @Override
    public Collection<RestExecutionDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection<RestExecutionDTO> findByIdRestOperation(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idRestOperation", "=", parm));
        ordenacao.add(new Order("idRestExecution"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdRestOperation(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idRestOperation", "=", parm));
        super.deleteByCondition(condicao);
    }

    @Override
    public void updateNotNull(final IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }

}
