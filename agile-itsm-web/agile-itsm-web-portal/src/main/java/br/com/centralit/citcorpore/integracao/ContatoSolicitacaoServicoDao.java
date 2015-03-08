package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ContatoSolicitacaoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ContatoSolicitacaoServicoDao extends CrudDaoDefaultImpl {

	private static final String TABLE_NAME = "contatosolicitacaoservico";

	public ContatoSolicitacaoServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idcontatosolicitacaoservico", "idcontatosolicitacaoservico", true, true, false, false));
		listFields.add(new Field("nomecontato", "nomecontato", false, false, false, false));
		listFields.add(new Field("telefonecontato", "telefonecontato", false, false, false, false));
		listFields.add(new Field("emailcontato", "emailcontato", false, false, false, false));
		listFields.add(new Field("localizacaofisica", "observacao", false, false, false, false));
		listFields.add(new Field("idlocalidade", "idLocalidade", false, false, false, false));
		listFields.add(new Field("ramal", "ramal", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {

		return TABLE_NAME;
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return ContatoSolicitacaoServicoDTO.class;
	}

}
