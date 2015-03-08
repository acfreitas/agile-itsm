package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.AtitudeCandidatoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AtitudeCandidatoDao extends CrudDaoDefaultImpl {

    public AtitudeCandidatoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idEntrevista", "idEntrevista", true, false, false, false));
        listFields.add(new Field("idAtitudeOrganizacional", "idAtitudeOrganizacional", true, false, false, false));
        listFields.add(new Field("avaliacao", "avaliacao", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "RH_AtitudeCandidato";
    }

    @Override
    public Collection<AtitudeCandidatoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<AtitudeCandidatoDTO> getBean() {
        return AtitudeCandidatoDTO.class;
    }

    @Override
    public Collection<AtitudeCandidatoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection<AtitudeCandidatoDTO> findByIdEntrevista(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idEntrevista", "=", parm));
        ordenacao.add(new Order("idAtitudeOrganizacional"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdEntrevista(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idEntrevista", "=", parm));
        super.deleteByCondition(condicao);
    }

}
