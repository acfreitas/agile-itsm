package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoUnidadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author Central IT Projects
 *
 */
public class TipoUnidadeDao extends CrudDaoDefaultImpl {

	public TipoUnidadeDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@SuppressWarnings("rawtypes")
	public Class getBean() {
		return TipoUnidadeDTO.class;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("IDTIPOUNIDADE", "idTipoUnidade", true, true, false, false));
		listFields.add(new Field("IDEMPRESA", "idEmpresa", false, false, false, false));
		listFields.add(new Field("NOMETIPOUNIDADE", "nomeTipoUnidade", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return "TipoUnidade";
	}

	@SuppressWarnings("rawtypes")
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeTipoUnidade"));
		return super.list(list);
	}
	
}
