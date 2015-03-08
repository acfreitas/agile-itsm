package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.AnexoMudancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class AnexoMudancaDAO extends CrudDaoDefaultImpl {

    public AnexoMudancaDAO() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    public Class getBean() {
	return AnexoMudancaDTO.class;
    }

    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();

	listFields.add(new Field("IDANEXOMUDANCA", "idAnexoMudanca", true, true, false, false));
	listFields.add(new Field("IDMUDANCA", "idMudanca", false, false, false, false));
	listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
	listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
	listFields.add(new Field("NOME", "nomeAnexo", false, false, false, false));
	listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
	listFields.add(new Field("LINK", "link", false, false, false, false));
	listFields.add(new Field("EXTENSAO", "extensao", false, false, false, false));
	return listFields;
    }

    public String getTableName() {
	return "anexomudanca";
    }

    @Override
    public Collection find(IDto arg0) throws PersistenceException {
	return null;
    }

    @Override
    public Collection list() throws PersistenceException {
	return null;
    }

}
