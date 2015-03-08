package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ClienteBIDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings( {"unchecked", "rawtypes" })
public class ClienteBIDao extends CrudDaoDefaultImpl {

	public ClienteBIDao() {
		super(Constantes.getValue("DATABASE_BI_ALIAS"), null);
	}

	public Class getBean() {
		return ClienteBIDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("IDCLIENTE", "idCliente", true, true, false, false));
		listFields.add(new Field("NOMERAZAOSOCIAL", "nomeRazaoSocial", false, false, false, false));
		listFields.add(new Field("NOMEFANTASIA", "nomeFantasia", false, false, false, false));
		listFields.add(new Field("CPFCNPJ", "cpfCnpj", false, false, false, false));
		listFields.add(new Field("OBSERVACOES", "observacoes", false, false, false, false));
		listFields.add(new Field("SITUACAO", "situacao", false, false, false, false));
		listFields.add(new Field("DELETED", "deleted", false, false, false, false));
		listFields.add(new Field("IDCONEXAOBI", "idConexaoBI", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "CLIENTES";
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeFantasia"));
		return super.list(list);
	}
	
}