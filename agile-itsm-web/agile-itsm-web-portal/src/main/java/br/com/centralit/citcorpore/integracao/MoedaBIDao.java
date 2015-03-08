package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.MoedaBIDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.InvalidTransactionControler;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MoedaBIDao extends CrudDaoDefaultImpl {

	public MoedaBIDao() {
		super(Constantes.getValue("DATABASE_BI_ALIAS"), null);
	}

	public MoedaBIDao(TransactionControler tc, Usuario usuario)
			throws InvalidTransactionControler {
		super(tc, usuario);		
	}

	@Override
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idMoeda", "idMoeda", true, true, false, false));
		listFields.add(new Field("nomeMoeda", "nomeMoeda", false, false, false, false));
		listFields.add(new Field("usarCotacao", "usarCotacao", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		listFields.add(new Field("idConexaoBI", "idConexaoBI", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		return "MOEDAS";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeMoeda"));
        return super.list(list);	}

	@Override
	public Class getBean() {
		return MoedaBIDTO.class;
	}

}
