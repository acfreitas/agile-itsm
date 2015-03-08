package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.HistoricoItemTrabalhoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class HistoricoItemTrabalhoDao extends CrudDaoDefaultImpl {

    private final static String TABLE_NAME = "bpm_historicoitemtrabalho";

    public HistoricoItemTrabalhoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idHistoricoItemTrabalho", "idHistoricoItemTrabalho", true, true, false, false));
        listFields.add(new Field("idItemTrabalho", "idItemTrabalho", false, false, false, false));
        listFields.add(new Field("idResponsavel", "idResponsavel", false, false, false, false));
        listFields.add(new Field("idUsuario", "idUsuario", false, false, false, false));
        listFields.add(new Field("idGrupo", "idGrupo", false, false, false, false));
        listFields.add(new Field("dataHora", "dataHora", false, false, false, false));
        listFields.add(new Field("acao", "acao", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection<HistoricoItemTrabalhoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<HistoricoItemTrabalhoDTO> getBean() {
        return HistoricoItemTrabalhoDTO.class;
    }

    @Override
    public Collection<HistoricoItemTrabalhoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection findByIdItemTrabalho(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idItemTrabalho", "=", parm));
        ordenacao.add(new Order("dataHora"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection findByIdUsuario(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idUsuario", "=", parm));
        ordenacao.add(new Order("dataHora"));
        return super.findByCondition(condicao, ordenacao);
    }

}
