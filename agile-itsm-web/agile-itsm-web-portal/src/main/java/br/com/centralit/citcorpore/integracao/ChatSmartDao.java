package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ChatSmartDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ChatSmartDao extends CrudDaoDefaultImpl {

    public ChatSmartDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto dto) throws PersistenceException {
        final List order = new ArrayList();
        order.add(new Order("nomeUsuario"));
        return super.find(dto, order);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("IDUSUARIO", "idUsuario", true, true, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "CHATSMART";
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("NomeUsuarioChat"));
        return super.list(list);
    }

    @Override
    public Class getBean() {
        return ChatSmartDTO.class;
    }

}
