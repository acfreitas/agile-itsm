package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.CheckinDeniedDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class CheckinDeniedDao extends CrudDaoDefaultImpl {

    public CheckinDeniedDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<CheckinDeniedDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<Field>();
        listFields.add(new Field("idCheckinDenied", "idCheckinDenied", true, true, false, false));
        listFields.add(new Field("idTarefa", "idTarefa", false, false, false, false));
        listFields.add(new Field("idUsuario", "idUsuario", false, false, false, false));
        listFields.add(new Field("idJustificativa", "idJustificativa", false, false, false, false));
        listFields.add(new Field("latitude", "latitude", false, false, false, false));
        listFields.add(new Field("longitude", "longitude", false, false, false, false));
        listFields.add(new Field("dataHora", "dataHora", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "CHECKINDENIED";
    }

    @Override
    public Collection<CheckinDeniedDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<CheckinDeniedDTO> getBean() {
        return CheckinDeniedDTO.class;
    }

}
