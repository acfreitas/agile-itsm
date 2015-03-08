package br.com.centralit.citsmart.rest.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citsmart.rest.bean.RestPermissionDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RestPermissionDao extends CrudDaoDefaultImpl {

    public RestPermissionDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idRestOperation", "idRestOperation", true, false, false, false));
        listFields.add(new Field("idGroup", "idGroup", true, false, false, false));
        listFields.add(new Field("status", "status", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "Rest_Permission";
    }

    @Override
    public Collection<RestPermissionDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<RestPermissionDTO> getBean() {
        return RestPermissionDTO.class;
    }

    @Override
    public Collection<RestPermissionDTO> find(final IDto arg0) throws PersistenceException {
        final RestPermissionDTO permissionDto = (RestPermissionDTO) arg0;
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        if (permissionDto.getIdRestOperation() != null) {
            condicao.add(new Condition("idRestOperation", "=", permissionDto.getIdRestOperation()));
        }
        if (permissionDto.getIdGroup() != null) {
            condicao.add(new Condition("idGroup", "=", permissionDto.getIdGroup()));
        }
        ordenacao.add(new Order("idRestOperation"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<RestPermissionDTO> findByIdOperation(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idRestOperation", "=", parm));
        ordenacao.add(new Order("idGroup"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdOperation(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idRestOperation", "=", parm));
        super.deleteByCondition(condicao);
    }

    public Collection<RestPermissionDTO> findByIdGroup(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idGroup", "=", parm));
        ordenacao.add(new Order("idRestOperation"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdGroup(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idRestUser", "=", parm));
        super.deleteByCondition(condicao);
    }

}
