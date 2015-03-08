package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CondicaoOperacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author ygor.magalhaes
 *
 */
public class CondicaoOperacaoDao extends CrudDaoDefaultImpl {

	public CondicaoOperacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@SuppressWarnings("rawtypes")
	public Class getBean() {
		return CondicaoOperacaoDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDCONDICAOOPERACAO", "idCondicaoOperacao", true, true, false, false));
		listFields.add(new Field("IDEMPRESA", "idEmpresa", false, false, false, false));
		listFields.add(new Field("NOMECONDICAOOPERACAO", "nomeCondicaoOperacao", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));

		return listFields;
	}
	public String getTableName() {
		return "CONDICAOOPERACAO";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection find(IDto obj) throws PersistenceException {
		List ordem = new ArrayList();
		ordem.add(new Order("nomeCondicaoOperacao"));
		return super.find(obj, ordem);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeCondicaoOperacao"));
		return super.list(list);
	}

}
