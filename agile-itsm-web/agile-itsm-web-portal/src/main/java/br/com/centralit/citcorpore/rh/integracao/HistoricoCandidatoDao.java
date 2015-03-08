package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.HistoricoCandidatoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class HistoricoCandidatoDao extends CrudDaoDefaultImpl {

    public HistoricoCandidatoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idHistoricoCandidato", "idHistoricoCandidato", true, true, false, false));
        listFields.add(new Field("idEntrevista", "idEntrevista", false, false, false, false));
        listFields.add(new Field("idCurriculo", "idCurriculo", false, false, false, false));
        listFields.add(new Field("resultado", "resultado", false, false, false, false));
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_HISTORICOCANDIDATO";
    }

    @Override
    public Class getBean() {
        return HistoricoCandidatoDTO.class;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("idCurriculo"));
        return super.list(list);
    }

    public Collection listByIdCurriculo(final Integer idCurriculo) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idCurriculo", "=", idCurriculo));
        ordenacao.add(new Order("idCurriculo"));
        final List list = (List) super.findByCondition(condicao, ordenacao);
        return list;
    }

}
