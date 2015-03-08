/**
 * CentralIT - CITSmart
 *
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.AtividadesServicoContratoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({  "rawtypes", "unchecked" })
public class AtividadesServicoContratoBIDao extends CrudDaoDefaultImpl {
	public AtividadesServicoContratoBIDao() {
		super(Constantes.getValue("DATABASE_BI_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idAtividadeServicoContrato", "idAtividadeServicoContrato", true, true, false, false));
		listFields.add(new Field("idServicoContrato", "idServicoContrato", false, false, false, false));
		listFields.add(new Field("descricaoAtividade", "descricaoAtividade", false, false, false, false));
		listFields.add(new Field("obsAtividade", "obsAtividade", false, false, false, false));
		listFields.add(new Field("custoAtividade", "custoAtividade", false, false, false, false));
		listFields.add(new Field("complexidade", "complexidade", false, false, false, false));
		listFields.add(new Field("hora", "hora", false, false, false, false));
		listFields.add(new Field("quantidade", "quantidade", false, false, false, false));
		listFields.add(new Field("periodo", "periodo", false, false, false, false));
		listFields.add(new Field("formula", "formula", false, false, false, false));
		listFields.add(new Field("contabilizar", "contabilizar", false, false, false, false));
		listFields.add(new Field("idServicoContratoContabil", "idServicoContratoContabil", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));
		listFields.add(new Field("tipoCusto", "tipoCusto", false, false, false, false));
		listFields.add(new Field("idConexaoBI", "idConexaoBI", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return "AtividadesServicoContrato";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return AtividadesServicoContratoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

}
