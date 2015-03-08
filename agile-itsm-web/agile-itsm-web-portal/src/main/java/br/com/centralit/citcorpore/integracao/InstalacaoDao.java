package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.InstalacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

/**
 * @author flavio.santana
 * 
 */
public class InstalacaoDao extends CrudDaoDefaultImpl {

	public InstalacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@SuppressWarnings("rawtypes")
	public Class getBean() {
		return InstalacaoDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<Field>();

		listFields.add(new Field("idinstalacao", "idinstalacao", true, true, false, true));
		listFields.add(new Field("sucesso", "sucesso", false, false, false, true));
		listFields.add(new Field("passo", "passo", false, false, false, true));

		return listFields;
	}

	public String getTableName() {
		return "instalacao";
	}

	public boolean isSucessoInstalacao() throws PersistenceException {
		List<Object> parametro = new ArrayList<Object>();
		List fields = new ArrayList();
		List list = new ArrayList();
		String sql = "select * from " + getTableName() + " where sucesso = 'S' ";
		list = this.execSQL(sql, parametro.toArray());
		fields.add("idinstalacao");
		fields.add("sucesso");
		fields.add("passo");
		if (list.isEmpty())
			return false;
		else 
			return true;
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

}
