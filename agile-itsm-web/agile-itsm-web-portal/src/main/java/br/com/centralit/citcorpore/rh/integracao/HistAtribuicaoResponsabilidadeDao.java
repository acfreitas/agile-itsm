package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.HistAtribuicaoResponsabilidadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class HistAtribuicaoResponsabilidadeDao extends CrudDaoDefaultImpl {

    public HistAtribuicaoResponsabilidadeDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idhistAtribuicaoResponsabilidade", "idhistAtribuicaoResponsabilidade", true, true, false, false));
        listFields.add(new Field("descricaoPerspectivaComplexidade", "descricaoPerspectivaComplexidade", false, false, false, false));
        listFields.add(new Field("idNivel", "idNivel", false, false, false, false));
        listFields.add(new Field("idManualFuncao", "idManualFuncao", false, false, false, false));
        listFields.add(new Field("dataAlteracao", "dataAlteracao", false, false, false, false));
        listFields.add(new Field("horaAlteracao", "horaAlteracao", false, false, false, false));
        listFields.add(new Field("idUsuarioAlteracao", "idUsuarioAlteracao", false, false, false, false));
        listFields.add(new Field("idhistManualFuncao", "idhistManualFuncao", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "rh_histatribuicaoresponsabilidade";
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
        return HistAtribuicaoResponsabilidadeDTO.class;
    }

    public Collection findByIdHistManualFuncao(final Integer idHistManualFuncao) throws PersistenceException {

        final StringBuilder sql = new StringBuilder();

        sql.append("select  c.idhistAtribuicaoResponsabilidade, c.descricaoPerspectivaComplexidade, c.idNivel, c.idManualFuncao ");
        sql.append(" from rh_histatribuicaoresponsabilidade c ");
        sql.append(" where c.idHistManualFuncao = ?");

        final Object[] objs = new Object[] {idHistManualFuncao};

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idhistAtribuicaoResponsabilidade");
        listRetorno.add("descricaoPerspectivaComplexidade");
        listRetorno.add("idNivel");
        listRetorno.add("idManualFuncao");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

}
