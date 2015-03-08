package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioOpcoesDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RespostaItemQuestionarioOpcoesDao extends CrudDaoDefaultImpl {

    public RespostaItemQuestionarioOpcoesDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<RespostaItemQuestionarioOpcoesDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idRespostaItemQuestionario", "idRespostaItemQuestionario", true, false, false, false));
        listFields.add(new Field("idOpcaoRespostaQuestionario", "idOpcaoRespostaQuestionario", true, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RespostaItemQuestionarioOpcoes";
    }

    @Override
    public Collection<RespostaItemQuestionarioOpcoesDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<RespostaItemQuestionarioOpcoesDTO> getBean() {
        return RespostaItemQuestionarioOpcoesDTO.class;
    }

    public Collection<RespostaItemQuestionarioOpcoesDTO> listByIdRespostaItemQuestionario(final Integer idRespostaItemQuestionario) throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("idOpcaoRespostaQuestionario"));
        final RespostaItemQuestionarioOpcoesDTO obj = new RespostaItemQuestionarioOpcoesDTO();
        obj.setIdRespostaItemQuestionario(idRespostaItemQuestionario);
        return super.find(obj, list);
    }

    public Collection<RespostaItemQuestionarioOpcoesDTO> getRespostasOpcoesByIdRespostaItemQuestionario(final Integer idRespostaItemQuestionario) throws Exception {
        final Object[] objs = new Object[] {idRespostaItemQuestionario};

        final String sql = "SELECT OPCAORESPOSTAQUESTIONARIO.idOpcaoRespostaQuestionario, titulo, peso, valor, geraAlerta, exibeComplemento, idQuestaoComplemento "
                + "FROM RESPOSTAITEMQUESTIONARIOOPCOES "
                + "INNER JOIN OPCAORESPOSTAQUESTIONARIO ON OPCAORESPOSTAQUESTIONARIO.idOpcaoRespostaQuestionario = RESPOSTAITEMQUESTIONARIOOPCOES.idOpcaoRespostaQuestionario "
                + "WHERE idRespostaItemQuestionario = ?";
        final List lista = this.execSQL(sql, objs);

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idOpcaoRespostaQuestionario");
        listRetorno.add("titulo");
        listRetorno.add("peso");
        listRetorno.add("valor");
        listRetorno.add("geraAlerta");
        listRetorno.add("exibeComplemento");
        listRetorno.add("idQuestaoComplemento");

        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

    public void deleteByIdRespostaItemQuestionario(final Integer idRespostaItemQuestionario) throws Exception {
        final Condition where = new Condition("idRespostaItemQuestionario", "=", idRespostaItemQuestionario);
        final List<Condition> lstCond = new ArrayList<>();
        lstCond.add(where);
        super.deleteByCondition(lstCond);
    }

}
