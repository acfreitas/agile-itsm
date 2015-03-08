package br.com.centralit.citsmart.rest.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citsmart.rest.bean.RestDomainDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RestDomainDao extends CrudDaoDefaultImpl {

    public RestDomainDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idRestOperation", "idRestOperation", true, false, false, false));
        listFields.add(new Field("idRestParameter", "idRestParameter", true, false, false, false));
        listFields.add(new Field("value", "value", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "Rest_Domain";
    }

    @Override
    public Collection<RestDomainDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<RestDomainDTO> getBean() {
        return RestDomainDTO.class;
    }

    @Override
    public Collection<RestDomainDTO> find(final IDto arg0) throws PersistenceException {
        final RestDomainDTO domainDto = (RestDomainDTO) arg0;
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        if (domainDto.getIdRestOperation() != null) {
            condicao.add(new Condition("idRestOperation", "=", domainDto.getIdRestOperation()));
        }
        if (domainDto.getIdRestParameter() != null) {
            condicao.add(new Condition("idRestParameter", "=", domainDto.getIdRestParameter()));
        }
        ordenacao.add(new Order("idRestParameter"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<RestDomainDTO> findByIdRestOperation(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idRestOperation", "=", parm));
        ordenacao.add(new Order("idRestParameter"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdRestOperation(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idRestOperation", "=", parm));
        super.deleteByCondition(condicao);
    }

}
