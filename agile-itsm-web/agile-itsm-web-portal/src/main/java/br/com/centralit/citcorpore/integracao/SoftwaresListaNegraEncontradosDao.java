package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.SoftwaresListaNegraEncontradosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author ronnie.lopes
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SoftwaresListaNegraEncontradosDao extends CrudDaoDefaultImpl {

    public SoftwaresListaNegraEncontradosDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Class<SoftwaresListaNegraEncontradosDTO> getBean() {
        return SoftwaresListaNegraEncontradosDTO.class;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("IDSOFTWARESLISTANEGRAENCONTRAD", "idsoftwareslistanegraencontrados", true, true, false, false));
        listFields.add(new Field("IDITEMCONFIGURACAO", "iditemconfiguracao", false, false, false, false));
        listFields.add(new Field("IDSOFTWARESLISTANEGRA", "idsoftwareslistanegra", false, false, false, false));
        listFields.add(new Field("SOFTWARELISTANEGRAENCONTRADO", "softwarelistanegraencontrado", false, false, false, false));
        listFields.add(new Field("DATA", "data", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "SoftwaresListaNegraEncontrados";
    }

    @Override
    public Collection<SoftwaresListaNegraEncontradosDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<SoftwaresListaNegraEncontradosDTO> list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("idsoftwareslistanegraencontrad"));
        return super.list(list);
    }

}
