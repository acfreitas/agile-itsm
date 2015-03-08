package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.ManualFuncaoComplexidadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ManualFuncaoComplexidadeDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "rh_complexidade";

    public ManualFuncaoComplexidadeDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idComplexidade", "idComplexidade", true, false, false, false));
        listFields.add(new Field("nivel", "nivel", false, false, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));

        return listFields;
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("nivel"));
        return super.list(list);
    }

    public Collection listAtivos() throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();

        condicao.add(new Condition("situacao", "=", "A"));
        ordenacao.add(new Order("descricao"));
        return super.findByCondition(condicao, ordenacao);
    }

    @Override
    public Class getBean() {
        return ManualFuncaoComplexidadeDTO.class;
    }

}
