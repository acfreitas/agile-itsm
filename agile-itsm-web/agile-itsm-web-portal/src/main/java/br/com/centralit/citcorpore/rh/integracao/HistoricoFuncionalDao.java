package br.com.centralit.citcorpore.rh.integracao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.HistoricoFuncionalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author david.silva
 *
 */
public class HistoricoFuncionalDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "rh_historicofuncional";

    public HistoricoFuncionalDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Class getBean() {
        return HistoricoFuncionalDTO.class;
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("IDHISTORICOFUNCIONAL", "idHistoricoFuncional", true, true, false, false));
        listFields.add(new Field("IDCANDIDATO", "idCandidato", false, false, false, false));
        listFields.add(new Field("IDCURRICULO", "idCurriculo", false, false, false, false));
        listFields.add(new Field("DTCRIACAO", "dtCriacao", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("idHistoricoFuncional"));
        return super.list(list);
    }

    public Collection<HistoricoFuncionalDTO> findByidCandidato(final Integer idCandidato) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idCandidato", "=", idCandidato));
        ordenacao.add(new Order("idCandidato"));
        return super.findByCondition(condicao, ordenacao);
    }

    public HistoricoFuncionalDTO restoreByIdCandidato(final Integer idCandidato) throws PersistenceException {
        final List<HistoricoFuncionalDTO> lista = (List<HistoricoFuncionalDTO>) this.findByidCandidato(idCandidato);
        return lista != null ? lista.get(0) : null;
    }

    public Collection<HistoricoFuncionalDTO> findByidCurriculo(final Integer idCurriculo) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idCurriculo", "=", idCurriculo));
        ordenacao.add(new Order("idCurriculo"));
        return super.findByCondition(condicao, ordenacao);
    }

    public HistoricoFuncionalDTO restoreByIdCurriculo(final Integer idCurriculo) throws PersistenceException {
        final List<HistoricoFuncionalDTO> lista = (List<HistoricoFuncionalDTO>) this.findByidCurriculo(idCurriculo);
        return lista != null ? lista.get(0) : null;
    }

    public Date getUltimaAtualizacao(final Integer idCurriculo) throws PersistenceException {
        final List parametro = new ArrayList();
        final List fields = new ArrayList();
        List list = new ArrayList();

        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT MAX(ihf.dtcriacao) dtCriacao ");
        sql.append("FROM rh_historicofuncional hf LEFT JOIN rh_itemhistoricofuncional ihf ON ihf.idhistoricofuncional=hf.idhistoricofuncional ");
        sql.append("WHERE hf.idcurriculo=?");

        parametro.add(idCurriculo);
        list = this.execSQL(sql.toString(), parametro.toArray());
        fields.add("dtCriacao");

        if (list != null && !list.isEmpty()) {
            return ((List<HistoricoFuncionalDTO>) this.listConvertion(this.getBean(), list, fields)).get(0).getDtCriacao();
        }
        return null;
    }

}
