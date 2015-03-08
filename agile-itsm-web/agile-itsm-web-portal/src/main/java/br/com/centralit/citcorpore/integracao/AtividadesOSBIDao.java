package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.AtividadesOSBIDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({  "rawtypes", "unchecked" })
public class AtividadesOSBIDao extends CrudDaoDefaultImpl {
	public AtividadesOSBIDao() {
		super(Constantes.getValue("DATABASE_BI_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idAtividadesOS", "idAtividadesOS", true, true, false, false));
		listFields.add(new Field("idOS", "idOS", false, false, false, false));
		listFields.add(new Field("sequencia", "sequencia", false, false, false, false));
		listFields.add(new Field("idAtividadeServicoContrato", "idAtividadeServicoContrato", false, false, false, false));
		listFields.add(new Field("descricaoAtividade", "descricaoAtividade", false, false, false, false));
		listFields.add(new Field("obsAtividade", "obsAtividade", false, false, false, false));
		listFields.add(new Field("custoAtividade", "custoAtividade", false, false, false, false));
		listFields.add(new Field("glosaAtividade", "glosaAtividade", false, false, false, false));
		listFields.add(new Field("qtdeExecutada", "qtdeExecutada", false, false, false, false));
		listFields.add(new Field("complexidade", "complexidade", false, false, false, false));
		listFields.add(new Field("formula", "formula", false, false, false, false));
		listFields.add(new Field("contabilizar", "contabilizar", false, false, false, false));
		listFields.add(new Field("idServicoContratoContabil", "idServicoContratoContabil", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));
		listFields.add(new Field("idConexaoBI", "idConexaoBI", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return "AtividadesOS";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return AtividadesOSBIDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

}
