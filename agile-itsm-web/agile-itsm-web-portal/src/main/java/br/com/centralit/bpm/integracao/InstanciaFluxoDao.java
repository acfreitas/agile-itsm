package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.InstanciaFluxoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class InstanciaFluxoDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "bpm_instanciafluxo";

    public InstanciaFluxoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idInstancia", "idInstancia", true, true, false, false));
        listFields.add(new Field("idFluxo", "idFluxo", false, false, false, false));
        listFields.add(new Field("dataHoraCriacao", "dataHoraCriacao", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));
        listFields.add(new Field("dataHoraFinalizacao", "dataHoraFinalizacao", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection<InstanciaFluxoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<InstanciaFluxoDTO> getBean() {
        return InstanciaFluxoDTO.class;
    }

    @Override
    public Collection<InstanciaFluxoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection findByIdFluxo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idFluxo", "=", parm));
        ordenacao.add(new Order("idInstancia"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdFluxo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idFluxo", "=", parm));
        super.deleteByCondition(condicao);
    }

    public Collection findAtivasByIdFluxo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idFluxo", "=", parm));
        condicao.add(new Condition("situacao", "=", Enumerados.INSTANCIA_ABERTA));
        ordenacao.add(new Order("idInstancia"));
        return super.findByCondition(condicao, ordenacao);
    }

}
