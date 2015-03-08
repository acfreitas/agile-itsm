package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CargoHabilidadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CargoHabilidadeDao extends CrudDaoDefaultImpl {

    public CargoHabilidadeDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idDescricaoCargo", "idDescricaoCargo", true, false, false, false));
        listFields.add(new Field("idHabilidade", "idHabilidade", true, false, false, false));
        listFields.add(new Field("obrigatorio", "obrigatorio", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_CargoHabilidade";
    }

    @Override
    public Class getBean() {
        return CargoHabilidadeDTO.class;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("idHabilidade"));
        return super.list(list);
    }

    public Collection findByIdDescricaoCargo(final Integer idDescricaoCargo) throws PersistenceException {
        final List lstCondicao = new ArrayList();
        final List lstOrder = new ArrayList();

        lstCondicao.add(new Condition("idDescricaoCargo", "=", idDescricaoCargo));
        lstOrder.add(new Order("idHabilidade"));

        return super.findByCondition(lstCondicao, lstOrder);
    }

    public void deleteByIdDescricaoCargo(final Integer idDescricaoCargo) throws PersistenceException {
        final Condition cond = new Condition("idDescricaoCargo", "=", idDescricaoCargo);
        final List lstCond = new ArrayList();
        lstCond.add(cond);

        super.deleteByCondition(lstCond);
    }

}
