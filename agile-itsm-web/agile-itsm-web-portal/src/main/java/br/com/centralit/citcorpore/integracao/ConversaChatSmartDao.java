package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ConversaChatSmartDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ConversaChatSmartDao extends CrudDaoDefaultImpl {

    public ConversaChatSmartDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto dto) throws PersistenceException {
        final List order = new ArrayList();
        order.add(new Order("idConversaChat"));
        return super.find(dto, order);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idConversaChat", "idConversaChat", true, true, false, false));
        listFields.add(new Field("idUsuario", "idUsuario", false, false, false, false));
        listFields.add(new Field("dataHoraConversa", "dataHoraConversa", false, false, false, false));
        listFields.add(new Field("conversa", "conversa", false, false, false, false));
        return listFields;
    }

    /*
     * public void gravar(ConversaChatSmartDTO obj) {
     * }
     */
    @Override
    public String getTableName() {
        return "conversachat";
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("idConversaChat"));
        return super.list(list);
    }

    @Override
    public Class getBean() {
        return ConversaChatSmartDTO.class;
    }

}
