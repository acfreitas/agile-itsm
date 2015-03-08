package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.PerspectivaComportamentalFuncaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class PerspectivaComportamentalFuncaoDao extends CrudDaoDefaultImpl {

    public PerspectivaComportamentalFuncaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idPerspectivaComportamental", "idPerspectivaComportamental", true, true, false, false));
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
        listFields.add(new Field("descricaoPerspectivaComportamental", "descricaoPerspectivaComportamental", false, false, false, false));
        listFields.add(new Field("detalhePerspectivaComportamental", "detalhePerspectivaComportamental", false, false, false, false));
        listFields.add(new Field("idAtitudeIndividual", "idAtitudeIndividual", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_perspectivacomportamentalfuncao";
    }

    @Override
    public Class getBean() {
        return PerspectivaComportamentalFuncaoDTO.class;
    }

    public Collection findByidSolicitacao(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idPerspectivaComportamental"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdSolicitacao(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        super.deleteByCondition(condicao);
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

}
