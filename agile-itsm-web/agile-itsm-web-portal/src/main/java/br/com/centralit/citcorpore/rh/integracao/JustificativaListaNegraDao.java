package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.JustificativaListaNegraDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author david.silva
 *
 */
public class JustificativaListaNegraDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "rh_justificativalistanegra";

    public JustificativaListaNegraDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Class getBean() {
        return JustificativaListaNegraDTO.class;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("IDJUSTIFICATIVA", "idJustificativa", true, true, false, false));
        listFields.add(new Field("JUSTIFICATIVA", "justificativa", false, false, false, false));
        listFields.add(new Field("SITUACAO", "situacao", false, false, false, false));
        listFields.add(new Field("DTCRIACAO", "dtCriacao", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("justificativa"));
        return super.list(list);
    }

}
