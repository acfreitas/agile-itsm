package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citquestionario.bean.QuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class QuestionarioDao extends CrudDaoDefaultImpl {

    private static final String SQL_EXISTE_RESPOSTA =  "SELECT COUNT(*) QTDE "+
            "  FROM RESPOSTAITEMQUESTIONARIO R, QUESTAOQUESTIONARIO Q, GRUPOQUESTIONARIO G "+
            " WHERE R.IDQUESTAOQUESTIONARIO = Q.IDQUESTAOQUESTIONARIO "+
            "   AND Q.IDGRUPOQUESTIONARIO = G.IDGRUPOQUESTIONARIO "+
            "   AND G.IDQUESTIONARIO = ?";

    private static final String SQL_EXISTE_REFERENCIA_COMPARTILHADA =   "SELECT COUNT(*) QTDE "+
             "  FROM QUESTAOQUESTIONARIO Q, GRUPOQUESTIONARIO G "+
             " WHERE Q.IDGRUPOQUESTIONARIO = G.IDGRUPOQUESTIONARIO "+
             "   AND G.IDQUESTIONARIO <> ? "+
             "   AND Q.IDQUESTAOCOMPARTILHADA IN " +
             "   (SELECT QQ.IDQUESTAOQUESTIONARIO "+
             "      FROM QUESTAOQUESTIONARIO QQ, GRUPOQUESTIONARIO G  "+
             "     WHERE QQ.IDGRUPOQUESTIONARIO = G.IDGRUPOQUESTIONARIO  "+
             "       AND G.IDQUESTIONARIO = ?)";

    private static final String SQL_LIST_QUESTIONARIO = "SELECT IDQUESTIONARIO, IDQUESTIONARIOORIGEM, IDCATEGORIAQUESTIONARIO, "+
             "       NOMEQUESTIONARIO, IDEMPRESA, ATIVO, javaScript "+
             "  FROM QUESTIONARIO "+
             " WHERE ATIVO = 'S' "+
             " ORDER BY NOMEQUESTIONARIO";

    private static final String SQL_LIST_POR_APLICACAO = "SELECT DISTINCT Q.IDQUESTIONARIO, IDQUESTIONARIOORIGEM, Q.IDCATEGORIAQUESTIONARIO, "+
              "       NOMEQUESTIONARIO, Q.IDEMPRESA, ATIVO, javaScript "+
              "  FROM QUESTIONARIO Q, APLICACAOQUESTIONARIO A "+
              " WHERE Q.IDQUESTIONARIO = Q.IDQUESTIONARIOORIGEM "+
              "   AND Q.IDQUESTIONARIO = A.IDQUESTIONARIO "+
              "   AND A.SITUACAO = 'A' "+
              "   AND (A.APLICACAO = 'T' OR A.APLICACAO = ?) "+
              "   AND Q.idEmpresa = ? "+
              " ORDER BY NOMEQUESTIONARIO";

    public QuestionarioDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<QuestionarioDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idQuestionario", "idQuestionario", true, true, false, false));
        listFields.add(new Field("idQuestionarioOrigem", "idQuestionarioOrigem", false, false, false, false));
        listFields.add(new Field("idCategoriaQuestionario", "idCategoriaQuestionario", false, false, false, false));
        listFields.add(new Field("nomeQuestionario", "nomeQuestionario", false, false, false, false));
        listFields.add(new Field("idEmpresa", "idEmpresa", false, false, false, false));
        listFields.add(new Field("ativo", "ativo", false, false, false, false));
        listFields.add(new Field("javaScript", "javaScript", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "Questionario";
    }

    @Override
    public Collection list() throws PersistenceException {
        final Object[] objs = new Object[] {};
        final List lista = this.execSQL(SQL_LIST_QUESTIONARIO, objs);

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idQuestionario");
        listRetorno.add("idQuestionarioOrigem");
        listRetorno.add("idCategoriaQuestionario");
        listRetorno.add("nomeQuestionario");
        listRetorno.add("idEmpresa");
        listRetorno.add("ativo");
        listRetorno.add("javaScript");

        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

    public Collection listByIdEmpresaAndAplicacao(final Integer idEmpresa, final String aplicacao) throws PersistenceException {
        final Object[] objs = new Object[] {aplicacao, idEmpresa};
        final List lista = this.execSQL(SQL_LIST_POR_APLICACAO, objs);

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idQuestionario");
        listRetorno.add("idQuestionarioOrigem");
        listRetorno.add("idCategoriaQuestionario");
        listRetorno.add("nomeQuestionario");
        listRetorno.add("idEmpresa");
        listRetorno.add("ativo");
        listRetorno.add("javaScript");

        return engine.listConvertion(this.getBean(), lista, listRetorno);
    }

    @Override
    public Class<QuestionarioDTO> getBean() {
        return QuestionarioDTO.class;
    }

    public Collection<QuestionarioDTO> listByIdEmpresa(final Integer idEmpresa) throws PersistenceException {
        final List<Condition> lstCond = new ArrayList<>();
        final List<Order> lstOrder = new ArrayList<>();
        lstCond.add(new Condition("idEmpresa", "=", idEmpresa));
        lstCond.add(new Condition("ativo", "=", "S"));
        lstOrder.add(new Order("nomeQuestionario"));
        return super.findByCondition(lstCond, lstOrder);
    }

    public QuestionarioDTO restoreByIdOrigem(final Integer idQuestionarioOrigem) throws PersistenceException {
        final List lstCond = new ArrayList();
        lstCond.add(new Condition("idQuestionarioOrigem", "=", idQuestionarioOrigem));
        lstCond.add(new Condition("ativo", "=", "S"));
        final Collection col = super.findByCondition(lstCond, null);
        if (col == null) {
            return null;
        }
        final Iterator it = col.iterator();
        if (it.hasNext()) {
            return (QuestionarioDTO) it.next();
        } else {
            return null;
        }
    }

    public boolean existeResposta(final Integer idQuestionario) throws PersistenceException {
        final Object[] objs = new Object[] {idQuestionario};
        final List lista = this.execSQL(SQL_EXISTE_RESPOSTA, objs);

        final Object[] row = (Object[]) lista.get(0);
        return Integer.parseInt(row[0].toString()) > 0;
    }

    public boolean existeReferenciaQuestaoCmpartilhada(final Integer idQuestionario) throws PersistenceException {
        final Object[] objs = new Object[] {idQuestionario, idQuestionario};
        final List lista = this.execSQL(SQL_EXISTE_REFERENCIA_COMPARTILHADA, objs);

        final Object[] row = (Object[]) lista.get(0);
        return Integer.parseInt(row[0].toString()) > 0;
    }

    @Override
    public void updateNotNull(final IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }

    /**
     * Metodo responsavel por identificar o questionário que são de resposta obrigatória.
     *
     * @param idQuestionario
     * @return
     */
    public boolean existeQuestaoObrigatoria(final Integer idQuestionario) {

        final String QUERY = "SELECT COUNT(qq.idquestaoquestionario) FROM questionario q " + "INNER JOIN grupoquestionario gq ON gq.idquestionario = q.idquestionario "
                + "INNER JOIN questaoquestionario qq ON qq.idgrupoquestionario = gq.idgrupoquestionario " + "WHERE qq.obrigatoria = 'S' AND q.idquestionario = ?";

        final Object[] param = new Object[] {idQuestionario};

        List lista;

        try {

            lista = this.execSQL(QUERY, param);

            if (lista != null && lista.size() > 0) {

                final Object[] row = (Object[]) lista.get(0);

                return Integer.parseInt(row[0].toString()) > 0;
            }

        } catch (final PersistenceException e) {

            e.printStackTrace();
        }

        return Boolean.FALSE;
    }
}
