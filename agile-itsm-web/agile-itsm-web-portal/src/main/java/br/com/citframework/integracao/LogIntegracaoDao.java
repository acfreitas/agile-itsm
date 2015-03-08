package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.IntegracaoSistemasExternosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.util.Constantes;

@SuppressWarnings("rawtypes")
public class LogIntegracaoDao extends CrudDaoDefaultImpl {

    public LogIntegracaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idLogIntegracao", "idLogIntegracao", true, true, false, false));
        listFields.add(new Field("idIntegracao", "idIntegracao", false, false, false, false));
        listFields.add(new Field("dataHora", "dataHora", false, false, false, false));
        listFields.add(new Field("resultado", "resultado", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "logintegracao";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<IntegracaoSistemasExternosDTO> getBean() {
        return IntegracaoSistemasExternosDTO.class;
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

}
