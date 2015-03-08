package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class TipoFluxoDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "bpm_tipofluxo";

    public TipoFluxoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idTipoFluxo", "idTipoFluxo", true, true, false, false));
        listFields.add(new Field("nomeFluxo", "nomeFluxo", false, false, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("nomeClasseFluxo", "nomeClasseFluxo", false, false, false, false));
        listFields.add(new Field("idProcessoNegocio", "idProcessoNegocio", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("nomeFluxo"));
        return super.list(list);
    }

    @Override
    public Class<TipoFluxoDTO> getBean() {
        return TipoFluxoDTO.class;
    }

    @Override
    public Collection<TipoFluxoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public TipoFluxoDTO findByNome(final String nome) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("nomeFluxo", "=", nome));
        ordenacao.add(new Order("idTipoFluxo"));
        final Collection col = super.findByCondition(condicao, ordenacao);
        if (col != null && !col.isEmpty()) {
            return (TipoFluxoDTO) ((List) col).get(0);
        }
        return null;
    }

    @Override
    public void updateNotNull(final IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }

    public Collection findByIdProcessoNegocio(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idProcessoNegocio", "=", parm));
        ordenacao.add(new Order("nomeFluxo"));
        return super.findByCondition(condicao, ordenacao);
    }

}
