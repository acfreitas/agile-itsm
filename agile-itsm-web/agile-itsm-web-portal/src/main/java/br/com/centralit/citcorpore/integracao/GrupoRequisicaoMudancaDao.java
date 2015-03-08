package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoRequisicaoMudancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author mario.haysaki
 *
 */
public class GrupoRequisicaoMudancaDao extends CrudDaoDefaultImpl {

    public GrupoRequisicaoMudancaDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        final List<Order> order = new ArrayList<>();
        order.add(new Order("idGrupoRequisicaoMudanca"));
        return super.find(obj, order);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idgruporequisicaomudanca", "idGrupoRequisicaoMudanca", true, true, false, false));
        listFields.add(new Field("idgrupo", "idGrupo", false, false, false, false));
        listFields.add(new Field("idrequisicaomudanca", "idRequisicaoMudanca", false, false, false, false));
        listFields.add(new Field("nomegrupo", "nomeGrupo", false, false, false, false));
        listFields.add(new Field("datafim", "dataFim", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "gruporequisicaomudanca";
    }

    @Override
    public Collection list() throws PersistenceException {
        return super.list("idGrupoRequisicaoMudanca");
    }

    @Override
    public Class<GrupoRequisicaoMudancaDTO> getBean() {
        return GrupoRequisicaoMudancaDTO.class;
    }

    public Collection findByIdGrupoRequisicaoMudanca(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idGrupoRequisicaoMudanca", "=", parm));
        ordenacao.add(new Order("idGrupoRequisicaoMudanca"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdGrupoRequisicaoMudanca(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idGrupoRequisicaoMudanca", "=", parm));
        super.deleteByCondition(condicao);
    }

    public Collection findByIdGrupo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idGrupo", "=", parm));
        ordenacao.add(new Order("idGrupo"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdGrupo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idGrupo", "=", parm));
        super.deleteByCondition(condicao);
    }

    public Collection findByIdRequisicaoMudanca(final Integer parm) throws PersistenceException {
        final List<Integer> parametro = new ArrayList<>();
        final List<String> fields = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();
        sql.append(" SELECT distinct grupo.idgrupo, grupo.nomegrupo, grupo.idgruporequisicaomudanca, grupo.idrequisicaomudanca  FROM gruporequisicaomudanca grupo ");
        sql.append(" JOIN requisicaomudanca rm ON grupo.idrequisicaomudanca = rm.idrequisicaomudanca ");
        sql.append(" WHERE rm.idrequisicaomudanca = ? and grupo.datafim is null ORDER BY grupo.idgrupo ");
        parametro.add(parm);
        final List list = this.execSQL(sql.toString(), parametro.toArray());
        fields.add("idGrupo");
        fields.add("nomeGrupo");
        if (list != null && !list.isEmpty()) {
            return this.listConvertion(this.getBean(), list, fields);
        }
        return null;
    }

    public Collection findByIdMudancaEDataFim(final Integer idRequisicaoMudanca) throws PersistenceException {
        final List<String> fields = new ArrayList<>();
        final List<Integer> parametro = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();

        sql.append("select idgruporequisicaomudanca, idgrupo, idrequisicaomudanca, nomegrupo, datafim from gruporequisicaomudanca WHERE idrequisicaomudanca = ? and datafim is null");
        parametro.add(idRequisicaoMudanca);
        final List resultado = this.execSQL(sql.toString(), parametro.toArray());

        fields.add("idGrupoRequisicaoMudanca");
        fields.add("idGrupo");
        fields.add("idRequisicaoMudanca");
        fields.add("nomeGrupo");
        fields.add("dataFim");

        return this.listConvertion(this.getBean(), resultado, fields);
    }

    public Collection listByIdHistoricoMudanca(final Integer idHistoricoMudanca) throws PersistenceException {
        final List<String> fields = new ArrayList<>();

        final String sql = "select distinct pr.idproblemamudanca, pr.idproblema, pr.idrequisicaomudanca, pr.datafim from problemamudanca pr "
                + "inner join ligacao_mud_hist_pr ligpr on ligpr.idproblemamudanca = pr.idproblemamudanca WHERE ligpr.idhistoricomudanca = ?";

        final List resultado = this.execSQL(sql, new Object[] {idHistoricoMudanca});

        fields.add("idProblemaMudanca");
        fields.add("idProblema");
        fields.add("idRequisicaoMudanca");
        fields.add("dataFim");

        return this.listConvertion(this.getBean(), resultado, fields);
    }

    public void deleteByIdRequisicaoMudanca(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idRequisicaoMudanca", "=", parm));
        super.deleteByCondition(condicao);
    }

}
