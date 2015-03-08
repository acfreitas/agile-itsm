package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoDemandaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class TipoDemandaDao extends CrudDaoDefaultImpl {

	public TipoDemandaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Class getBean() {
		return TipoDemandaDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("IDTIPODEMANDA", "idTipoDemanda", true, false, false, true));
		listFields.add(new Field("NOMETIPODEMANDA", "nomeTipoDemanda", false, false, false, false));
		listFields.add(new Field("CLASSIFICACAO", "classificacao", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "TIPODEMANDA";
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeTipoDemanda"));
		return super.list(list);
	}

}
