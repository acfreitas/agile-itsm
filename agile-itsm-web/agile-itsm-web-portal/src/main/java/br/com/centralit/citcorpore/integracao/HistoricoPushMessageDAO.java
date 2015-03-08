package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoPushMessageDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class HistoricoPushMessageDAO extends CrudDaoDefaultImpl {

    public HistoricoPushMessageDAO() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<HistoricoPushMessageDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final List<Field> fields = new ArrayList<>();
        fields.add(new Field("id", "id", true, true, false, true));
        fields.add(new Field("idusuario", "idUsuario", false, false, false, false));
        fields.add(new Field("message", "message", false, false, false, false));
        fields.add(new Field("datetime", "dateTime", false, false, false, false));
        return fields;
    }

    @Override
    public String getTableName() {
        return "historicopushmessage";
    }

    @Override
    public Collection<HistoricoPushMessageDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<HistoricoPushMessageDTO> getBean() {
        return HistoricoPushMessageDTO.class;
    }

}
