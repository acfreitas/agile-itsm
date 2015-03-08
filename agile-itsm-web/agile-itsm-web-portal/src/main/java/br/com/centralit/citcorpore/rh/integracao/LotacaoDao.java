package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.rh.bean.LotacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class LotacaoDao extends CrudDaoDefaultImpl {

    public LotacaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idLotacao", "idLotacao", true, true, false, false));
        listFields.add(new Field("idCargo", "idCargo", false, false, false, false));
        listFields.add(new Field("idEmpregado", "idEmpregado", false, false, false, false));
        listFields.add(new Field("idEmpresa", "idEmpresa", false, false, false, false));
        listFields.add(new Field("idFuncao", "idFuncao", false, false, false, false));
        listFields.add(new Field("idUnidade", "idUnidade", false, false, false, false));
        listFields.add(new Field("nomeLotacao", "nomeLotacao", false, false, false, false));
        listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
        listFields.add(new Field("dataFim", "dataFim", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_HistoricoLotacao";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return LotacaoDTO.class;
    }

}
