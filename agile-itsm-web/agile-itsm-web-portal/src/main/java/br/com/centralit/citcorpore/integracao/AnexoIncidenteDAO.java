package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.AnexoIncidenteDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class AnexoIncidenteDAO extends CrudDaoDefaultImpl {

    public AnexoIncidenteDAO() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    public Class getBean() {
	return AnexoIncidenteDTO.class;
    }

    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();

	listFields.add(new Field("IDANEXOINCIDENTE", "idAnexoIncidente", true, true, false, false));
	listFields.add(new Field("IDINCIDENTE", "idIncidente", false, false, false, false));
	listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
	listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
	listFields.add(new Field("NOME", "nomeAnexo", false, false, false, false));
	listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
	listFields.add(new Field("LINK", "link", false, false, false, false));
	listFields.add(new Field("EXTENSAO", "extensao", false, false, false, false));
	return listFields;
    }

    public String getTableName() {
	return "anexoincidente";
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
