package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citquestionario.bean.GrupoQuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class GrupoQuestionarioDao extends CrudDaoDefaultImpl {

    public GrupoQuestionarioDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<GrupoQuestionarioDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idGrupoQuestionario", "idGrupoQuestionario", true, true, false, false));
        listFields.add(new Field("idQuestionario", "idQuestionario", false, false, false, false));
        listFields.add(new Field("nomeGrupoQuestionario", "nomeGrupoQuestionario", false, false, false, false));
        listFields.add(new Field("ordem", "ordem", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "GrupoQuestionario";
    }

    @Override
    public Collection<GrupoQuestionarioDTO> list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("nomeGrupoQuestionario"));
        return super.list(list);
    }

    @Override
    public Class<GrupoQuestionarioDTO> getBean() {
        return GrupoQuestionarioDTO.class;
    }

    public Collection<GrupoQuestionarioDTO> listByIdQuestionario(final Integer idQuestionario) throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("ordem"));
        list.add(new Order("idGrupoQuestionario"));
        final GrupoQuestionarioDTO obj = new GrupoQuestionarioDTO();
        obj.setIdQuestionario(idQuestionario);
        return super.find(obj, list);
    }

    public void updateOrdem(final IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }

    public void updateNome(final IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }

}
