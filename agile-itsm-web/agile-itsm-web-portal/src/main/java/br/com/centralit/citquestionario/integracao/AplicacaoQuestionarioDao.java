package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citquestionario.bean.AplicacaoQuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AplicacaoQuestionarioDao extends CrudDaoDefaultImpl {

    private static final String SQL_LIST_POR_QUESTIONARIO_APLICACAO = "SELECT IDAPLICACAOQUESTIONARIO, Q.IDQUESTIONARIO, IDTIPOPRODUTO, A.SITUACAO, APLICACAO "
            + "  FROM QUESTIONARIO Q, APLICACAOQUESTIONARIO A  WHERE A.IDQUESTIONARIO = Q.IDQUESTIONARIOORIGEM    AND A.SITUACAO = 'A'    AND Q.IDQUESTIONARIO = ? "
            + "   AND (A.APLICACAO = 'T' OR A.APLICACAO = ?) ";

    public AplicacaoQuestionarioDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<AplicacaoQuestionarioDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idAplicacaoQuestionario", "idAplicacaoQuestionario", true, true, false, false));
        listFields.add(new Field("idQuestionario", "idQuestionario", false, false, false, false));
        listFields.add(new Field("idTipoProduto", "idTipoProduto", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));
        listFields.add(new Field("aplicacao", "aplicacao", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "AplicacaoQuestionario";
    }

    @Override
    public Collection<AplicacaoQuestionarioDTO> list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("idAplicacaoQuestionario"));
        return super.list(list);
    }

    @Override
    public Class<AplicacaoQuestionarioDTO> getBean() {
        return AplicacaoQuestionarioDTO.class;
    }

    public Collection<AplicacaoQuestionarioDTO> listByIdQuestionarioAndAplicacao(final Integer idQuestionario, final String aplicacao) throws PersistenceException {
        final Object[] objs = new Object[] {idQuestionario, aplicacao};
        final List lista = this.execSQL(SQL_LIST_POR_QUESTIONARIO_APLICACAO, objs);

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idAplicacaoQuestionario");
        listRetorno.add("idQuestionario");
        listRetorno.add("idTipoProduto");
        listRetorno.add("situacao");
        listRetorno.add("aplicacao");

        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

}
