package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citquestionario.bean.OpcaoRespostaQuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class OpcaoRespostaQuestionarioDao extends CrudDaoDefaultImpl {

    public OpcaoRespostaQuestionarioDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<OpcaoRespostaQuestionarioDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idOpcaoRespostaQuestionario", "idOpcaoRespostaQuestionario", true, true, false, false));
        listFields.add(new Field("idQuestaoQuestionario", "idQuestaoQuestionario", false, false, false, false));
        listFields.add(new Field("titulo", "titulo", false, false, false, false));
        listFields.add(new Field("peso", "peso", false, false, false, false));
        listFields.add(new Field("valor", "valor", false, false, false, false));
        listFields.add(new Field("geraAlerta", "geraAlerta", false, false, false, false));
        listFields.add(new Field("exibeComplemento", "exibeComplemento", false, false, false, false));
        listFields.add(new Field("idQuestaoComplemento", "idQuestaoComplemento", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "OpcaoRespostaQuestionario";
    }

    @Override
    public Collection<OpcaoRespostaQuestionarioDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<OpcaoRespostaQuestionarioDTO> getBean() {
        return OpcaoRespostaQuestionarioDTO.class;
    }

    public Collection<OpcaoRespostaQuestionarioDTO> listByIdQuestaoQuestionario(final Integer idQuestaoQuestionario) throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("idOpcaoRespostaQuestionario"));
        final OpcaoRespostaQuestionarioDTO obj = new OpcaoRespostaQuestionarioDTO();
        obj.setIdQuestaoQuestionario(idQuestaoQuestionario);
        return super.find(obj, list);
    }

    public int deleteByIdQuestaoQuestionario(final Integer idQuestaoQuestionario) throws PersistenceException {
        final Condition cond = new Condition("idQuestaoQuestionario", "=", idQuestaoQuestionario);
        final List<Condition> lstCond = new ArrayList<>();
        lstCond.add(cond);
        return super.deleteByCondition(lstCond);
    }

}
