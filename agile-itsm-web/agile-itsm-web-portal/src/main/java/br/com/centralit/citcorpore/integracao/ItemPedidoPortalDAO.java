package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemPedidoPortalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ItemPedidoPortalDAO extends CrudDaoDefaultImpl{

	public ItemPedidoPortalDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}


	@Override
	public Collection find(IDto obj) throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idItemPedidoPortal"));
		return super.find(obj, ordenacao);
	}

	
	@Override
	public Collection<Field> getFields() {
Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idItemPedidoPortal", "idItemPedidoPortal", true, true, false, false));
		listFields.add(new Field("idPedidoPortal", "idPedidoPortal", false, false, false, false));
		listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("valor", "valor", false, false, false, false));
		
		return listFields;
		
	}

	@Override
	public String getTableName() {
		return "ItemPedidoPortal";
	}

	@Override
	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idItemPedidoPortal"));
		return super.list(ordenacao);
	}

	@Override
	public Class getBean() {
		return ItemPedidoPortalDTO.class;
	}

}
