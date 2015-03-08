package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citquestionario.bean.CategoriaQuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CategoriaQuestionarioDao extends CrudDaoDefaultImpl {

    public CategoriaQuestionarioDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<CategoriaQuestionarioDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idCategoriaQuestionario", "idCategoriaQuestionario", true, true, false, false));
        listFields.add(new Field("nomeCategoriaQuestionario", "nomeCategoriaQuestionario", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "CategoriaQuestionario";
    }

    @Override
    public Collection<CategoriaQuestionarioDTO> list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("nomeCategoriaQuestionario"));
        return super.list(list);
    }

    @Override
    public Class<CategoriaQuestionarioDTO> getBean() {
        return CategoriaQuestionarioDTO.class;
    }

}
