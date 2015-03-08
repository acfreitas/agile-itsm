package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoSubscricaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 *  @author Pedro
 *
 */
public class TipoSubscricaoDao extends CrudDaoDefaultImpl {

    public TipoSubscricaoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @SuppressWarnings("rawtypes")
    public Class getBean() {
	return TipoSubscricaoDTO.class;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();

	listFields.add(new Field("idtiposubscricao", "idTipoSubscricao", true, true, false, false));
	listFields.add(new Field("nometiposubscricao", "nomeTipoSubscricao", false, false, false, false));

	return listFields;
    }

    public String getTableName() {
	return "TIPOSUBSCRICAO";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection find(IDto obj) throws PersistenceException {
	List ordem = new ArrayList();
	ordem.add(new Order("nomeTipoSubscricao"));
	return super.find(obj, ordem);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection list() throws PersistenceException {
	List list = new ArrayList();
	list.add(new Order("nomeTipoSubscricao"));
	return super.list(list);
    }

}
