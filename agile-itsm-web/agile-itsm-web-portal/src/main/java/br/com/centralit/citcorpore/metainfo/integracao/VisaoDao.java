package br.com.centralit.citcorpore.metainfo.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class VisaoDao extends CrudDaoDefaultImpl {

    public VisaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idVisao", "idVisao", true, true, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("tipoVisao", "tipoVisao", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));
        listFields.add(new Field("classeName", "classeName", false, false, false, false));
        listFields.add(new Field("identificador", "identificador", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "Visao";
    }

    public Collection listAtivos() throws Exception {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();

        condicao.add(new Condition("situacao", "=", "A"));
        ordenacao.add(new Order("descricao"));

        return super.findByCondition(condicao, ordenacao);
    }

    public VisaoDTO findByIdentificador(final String identificador) throws Exception {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();

        condicao.add(new Condition("identificador", "=", identificador));
        ordenacao.add(new Order("descricao"));

        final List result = (List) super.findByCondition(condicao, ordenacao);
        if (result != null) {
            return (VisaoDTO) result.get(0);
        }
        return null;
    }

    @Override
    public Class<VisaoDTO> getBean() {
        return VisaoDTO.class;
    }

    @Override
    public List execSQL(final String sql, final Object[] parametros) throws PersistenceException {
        return super.execSQL(sql, parametros);
    }

    @Override
    public int execUpdate(final String sql, final Object[] parametros) throws PersistenceException {
        return super.execUpdate(sql, parametros);
    }

}
