package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.PedidoPortalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PedidoPortalDAO extends CrudDaoDefaultImpl{

	public PedidoPortalDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}


	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	
	@Override
	public Collection<Field> getFields() {
Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idPedidoPortal", "idPedidoPortal", true, true, false, false));
		listFields.add(new Field("idEmpregado", "idEmpregado", false, false, false, false));
		listFields.add(new Field("dataPedido", "dataPedido", false, false, false, false));
		listFields.add(new Field("precoTotal", "precoTotal", false, false, false, false));
		listFields.add(new Field("status", "status", false, false, false, false));
		
		return listFields;
		
	}

	@Override
	public String getTableName() {
		return "PedidoPortal";
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return PedidoPortalDTO.class;
	}

}
