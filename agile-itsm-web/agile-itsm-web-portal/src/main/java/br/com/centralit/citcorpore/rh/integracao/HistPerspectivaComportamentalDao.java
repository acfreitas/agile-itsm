package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.HistPerspectivaComportamentalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class HistPerspectivaComportamentalDao extends CrudDaoDefaultImpl {

    public HistPerspectivaComportamentalDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idhistPerspectivaComportamental", "idhistPerspectivaComportamental", true, true, false, false));
        listFields.add(new Field("cmbCompetenciaComportamental", "cmbCompetenciaComportamental", false, false, false, false));
        listFields.add(new Field("comportamento", "comportamento", false, false, false, false));
        listFields.add(new Field("idManualFuncao", "idManualFuncao", false, false, false, false));
        listFields.add(new Field("dataAlteracao", "dataAlteracao", false, false, false, false));
        listFields.add(new Field("horaAlteracao", "horaAlteracao", false, false, false, false));
        listFields.add(new Field("idUsuarioAlteracao", "idUsuarioAlteracao", false, false, false, false));
        listFields.add(new Field("idhistManualFuncao", "idhistManualFuncao", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "rh_histperspectivacomportamental";
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return HistPerspectivaComportamentalDTO.class;
    }

    public Collection findByIdHistManualFuncao(final Integer idHistManualFuncao) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();

        sql.append("select  c.idhistPerspectivaComportamental, c.cmbCompetenciaComportamental, c.comportamento, c.idManualFuncao ");
        sql.append(" from rh_histperspectivacomportamental c ");
        sql.append(" where c.idhistManualFuncao = ?");

        final Object[] objs = new Object[] {idHistManualFuncao};

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idhistPerspectivaComportamental");
        listRetorno.add("cmbCompetenciaComportamental");
        listRetorno.add("comportamento");
        listRetorno.add("idManualFuncao");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

}
