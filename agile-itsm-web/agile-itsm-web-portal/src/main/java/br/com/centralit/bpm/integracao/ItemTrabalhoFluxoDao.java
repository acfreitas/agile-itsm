package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ItemTrabalhoFluxoDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "bpm_itemtrabalhofluxo";

    public ItemTrabalhoFluxoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idItemTrabalho", "idItemTrabalho", true, true, false, false));
        listFields.add(new Field("idInstancia", "idInstancia", false, false, false, false));
        listFields.add(new Field("idElemento", "idElemento", false, false, false, false));
        listFields.add(new Field("idResponsavelAtual", "idResponsavelAtual", false, false, false, false));
        listFields.add(new Field("dataHoraCriacao", "dataHoraCriacao", false, false, false, false));
        listFields.add(new Field("dataHoraInicio", "dataHoraInicio", false, false, false, false));
        listFields.add(new Field("dataHoraFinalizacao", "dataHoraFinalizacao", false, false, false, false));
        listFields.add(new Field("dataHoraExecucao", "dataHoraExecucao", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Class<?> getBean() {
        return ItemTrabalhoFluxoDTO.class;
    }

    public Collection<ItemTrabalhoFluxoDTO> findByIdInstanciaAndIdElemento(final Integer idInstancia, final Integer idElemento) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idInstancia", "=", idInstancia));
        condicao.add(new Condition("idElemento", "=", idElemento));
        return super.findByCondition(condicao, null);
    }

    public Collection<ItemTrabalhoFluxoDTO> findDisponiveisByIdInstancia(final Integer idInstancia) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idInstancia", "=", idInstancia));
        condicao.add(new Condition("situacao", "<>", Enumerados.SituacaoItemTrabalho.Executado.name()));
        condicao.add(new Condition("situacao", "<>", Enumerados.SituacaoItemTrabalho.Cancelado.name()));
        return super.findByCondition(condicao, null);
    }

    public ItemTrabalhoFluxoDTO lastByIdInstanciaAndIdElemento(final Integer idInstancia, final Integer idElemento) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordem = new ArrayList<>();
        condicao.add(new Condition("idInstancia", "=", idInstancia));
        condicao.add(new Condition("idElemento", "=", idElemento));
        ordem.add(new Order("idItemTrabalho", Order.DESC));

        final Collection col = super.findByCondition(condicao, null);
        if (col == null || col.size() == 0) {
            return null;
        }
        return (ItemTrabalhoFluxoDTO) ((List) col).get(0);
    }

}
