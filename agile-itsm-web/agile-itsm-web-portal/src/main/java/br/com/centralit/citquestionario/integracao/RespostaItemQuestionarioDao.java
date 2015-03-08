package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RespostaItemQuestionarioDao extends CrudDaoDefaultImpl {

    private static final String SQL_LIST_BY_ID_TABELA = "SELECT * FROM (    SELECT idRespostaItemQuestionario, idIdentificadorResposta, "
            + "          R.IDQUESTAOQUESTIONARIO AS idQuestaoQuestionario, sequencialResposta,          respostaTextual, respostaPercentual, respostaValor, "
            + "          respostaValor2, respostaNumero, respostaNumero2,           respostaData, respostaHora, respostaMes, respostaAno, "
            + "          respostaIdListagem, sequenciaQuestao, idQuestaoAgrupadora, respostaDia      FROM RESPOSTAITEMQUESTIONARIO R, QUESTAOQUESTIONARIO Q "
            + "    WHERE R.IDQUESTAOQUESTIONARIO = Q.IDQUESTAOQUESTIONARIO) R  WHERE IDIDENTIFICADORRESPOSTA = ?    AND IDQUESTAOAGRUPADORA = ? "
            + " ORDER BY SEQUENCIALRESPOSTA, SEQUENCIAQUESTAO ";

    public RespostaItemQuestionarioDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<RespostaItemQuestionarioDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idRespostaItemQuestionario", "idRespostaItemQuestionario", true, true, false, false));
        listFields.add(new Field("idIdentificadorResposta", "idIdentificadorResposta", false, false, false, false));
        listFields.add(new Field("idQuestaoQuestionario", "idQuestaoQuestionario", false, false, false, false));
        listFields.add(new Field("sequencialResposta", "sequencialResposta", false, false, false, false));
        listFields.add(new Field("respostaTextual", "respostaTextual", false, false, false, false));
        listFields.add(new Field("respostaPercentual", "respostaPercentual", false, false, false, false));
        listFields.add(new Field("respostaValor", "respostaValor", false, false, false, false));
        listFields.add(new Field("respostaValor2", "respostaValor2", false, false, false, false));
        listFields.add(new Field("respostaNumero", "respostaNumero", false, false, false, false));
        listFields.add(new Field("respostaNumero2", "respostaNumero2", false, false, false, false));
        listFields.add(new Field("respostaData", "respostaData", false, false, false, false));
        listFields.add(new Field("respostaHora", "respostaHora", false, false, false, false));
        listFields.add(new Field("respostaMes", "respostaMes", false, false, false, false));
        listFields.add(new Field("respostaAno", "respostaAno", false, false, false, false));
        listFields.add(new Field("respostaDia", "respostaDia", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "RespostaItemQuestionario";
    }

    @Override
    public Collection<RespostaItemQuestionarioDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<RespostaItemQuestionarioDTO> getBean() {
        return RespostaItemQuestionarioDTO.class;
    }

    public Collection<RespostaItemQuestionarioDTO> listByIdIdentificadorAndIdQuestao(final Integer idIdentificadorResposta, final Integer idQuestaoQuestionario)
            throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("idRespostaItemQuestionario"));
        final RespostaItemQuestionarioDTO obj = new RespostaItemQuestionarioDTO();
        obj.setIdIdentificadorResposta(idIdentificadorResposta);
        obj.setIdQuestaoQuestionario(idQuestaoQuestionario);
        return super.find(obj, list);
    }

    public Collection<RespostaItemQuestionarioDTO> listByIdIdentificadorAndIdTabela(final Integer idIdentificadorResposta, final Integer idQuestaoQuestionario)
            throws PersistenceException {
        final Object[] objs = new Object[] {idIdentificadorResposta, idQuestaoQuestionario};
        final List lista = this.execSQL(SQL_LIST_BY_ID_TABELA, objs);

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idRespostaItemQuestionario");
        listRetorno.add("idIdentificadorResposta");
        listRetorno.add("idQuestaoQuestionario");
        listRetorno.add("sequencialResposta");
        listRetorno.add("respostaTextual");
        listRetorno.add("respostaPercentual");
        listRetorno.add("respostaValor");
        listRetorno.add("respostaValor2");
        listRetorno.add("respostaNumero");
        listRetorno.add("respostaNumero2");
        listRetorno.add("respostaData");
        listRetorno.add("respostaHora");
        listRetorno.add("respostaMes");
        listRetorno.add("respostaAno");
        listRetorno.add("respostaIdListagem");
        listRetorno.add("sequenciaQuestao");
        listRetorno.add("idQuestaoAgrupadora");
        listRetorno.add("respostaDia");

        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

    public void deleteByIdIdentificadorResposta(final Integer idIdentificadorResposta) throws Exception {
        final Condition cond = new Condition("idIdentificadorResposta", "=", idIdentificadorResposta);
        final List<Condition> lstCond = new ArrayList<>();
        lstCond.add(cond);
        super.deleteByCondition(lstCond);
    }

    public void deleteByIdQuestaoAndIdentificadorResposta(final Integer idQuestaoQuestionario, final Integer idIdentificadorResposta) throws Exception {
        final List<Condition> lstCond = new ArrayList<>();
        lstCond.add(new Condition("idQuestaoQuestionario", "=", idQuestaoQuestionario));
        lstCond.add(new Condition("idIdentificadorResposta", "=", idIdentificadorResposta));
        super.deleteByCondition(lstCond);
    }

}
