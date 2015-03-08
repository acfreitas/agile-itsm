package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.SequenciaFluxoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class SequenciaFluxoDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "bpm_sequenciafluxo";

    public SequenciaFluxoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idElementoOrigem", "idElementoOrigem", true, false, false, false));
        listFields.add(new Field("idElementoDestino", "idElementoDestino", true, false, false, false));
        listFields.add(new Field("idFluxo", "idFluxo", false, false, false, false));
        listFields.add(new Field("condicao", "condicao", false, false, false, false));
        listFields.add(new Field("idConexaoOrigem", "idConexaoOrigem", false, false, false, false));
        listFields.add(new Field("idConexaoDestino", "idConexaoDestino", false, false, false, false));
        listFields.add(new Field("bordaX", "bordaX", false, false, false, false));
        listFields.add(new Field("bordaY", "bordaY", false, false, false, false));
        listFields.add(new Field("posicaoAlterada", "posicaoAlterada", false, false, false, false));
        listFields.add(new Field("nome", "nome", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection<SequenciaFluxoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<SequenciaFluxoDTO> getBean() {
        return SequenciaFluxoDTO.class;
    }

    @Override
    public Collection<SequenciaFluxoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public List<SequenciaFluxoDTO> findByIdFluxo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idFluxo", "=", parm));
        ordenacao.add(new Order("idElementoOrigem"));
        return (List<SequenciaFluxoDTO>) super.findByCondition(condicao, ordenacao);
    }

    public Collection<SequenciaFluxoDTO> findByIdFluxoAndIdOrigem(final Integer idFluxo, final Integer idOrigem) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idFluxo", "=", idFluxo));
        condicao.add(new Condition("idElementoOrigem", "=", idOrigem));
        ordenacao.add(new Order("idElementoOrigem"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<SequenciaFluxoDTO> findByIdFluxoAndIdDestino(final Integer idFluxo, final Integer idDestino) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idFluxo", "=", idFluxo));
        condicao.add(new Condition("idElementoDestino", "=", idDestino));
        ordenacao.add(new Order("idElementoDestino"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdFluxo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idFluxo", "=", parm));
        super.deleteByCondition(condicao);
    }

}
