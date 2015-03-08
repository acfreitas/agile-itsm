package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class DescricaoCargoDao extends CrudDaoDefaultImpl {

    public DescricaoCargoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idDescricaoCargo", "idDescricaoCargo", true, true, false, false));
        listFields.add(new Field("nomeCargo", "nomeCargo", false, false, false, false));
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
        listFields.add(new Field("idCbo", "idCbo", false, false, false, false));
        listFields.add(new Field("atividades", "atividades", false, false, false, false));
        listFields.add(new Field("observacoes", "observacoes", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));
        listFields.add(new Field("idParecerValidacao", "idParecerValidacao", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_DescricaoCargo";
    }

    @Override
    public Class getBean() {
        return DescricaoCargoDTO.class;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("nomeCargo"));
        return super.list(list);
    }

    public DescricaoCargoDTO findByIdSolicitacaoServico(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idDescricaoCargo"));
        final List<DescricaoCargoDTO> result = (List<DescricaoCargoDTO>) super.findByCondition(condicao, ordenacao);
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

}
