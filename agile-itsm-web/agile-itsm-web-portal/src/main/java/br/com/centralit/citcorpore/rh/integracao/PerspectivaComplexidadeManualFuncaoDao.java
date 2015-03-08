package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.rh.bean.PerspecitivaComplexidadeManualFuncaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class PerspectivaComplexidadeManualFuncaoDao extends CrudDaoDefaultImpl {

    public PerspectivaComplexidadeManualFuncaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idAtribuicao", "idAtribuicao", true, true, false, true));
        listFields.add(new Field("idDescricao", "idDescricao", false, false, false, false));
        listFields.add(new Field("idNivel", "idNivel", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_atribuicaoresponsabilidade";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return PerspecitivaComplexidadeManualFuncaoDTO.class;
    }

}
