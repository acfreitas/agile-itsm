package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.HistoricoTentativaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings("unchecked")
public class HistoricoTentativaDao extends CrudDaoDefaultImpl {
    public HistoricoTentativaDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();
	listFields.add(new Field("idHistoricoTentativa", "idHistoricoTentativa", true, true, false, false));
	listFields.add(new Field("idEvento", "idevento", false, false, false, false));
	listFields.add(new Field("idItemConfiguracao", "iditemconfiguracao", false, false, false, false));
	listFields.add(new Field("idBaseItemConfiguracao", "idbaseitemconfiguracao", false, false, false, false));
	listFields.add(new Field("idEmpregado", "idempregado", false, false, false, false));
	listFields.add(new Field("descricao", "descricao", false, false, false, false));
	listFields.add(new Field("data", "data", false, false, false, false));
	listFields.add(new Field("hora", "hora", false, false, false, false));
	return listFields;
    }

    public String getTableName() {
	return this.getOwner() + "HistoricoTentativa";
    }

    public Collection list() throws PersistenceException {
	return null;
    }

    public Class getBean() {
	return HistoricoTentativaDTO.class;
    }

    public Collection find(IDto arg0) throws PersistenceException {
	return null;
    }
}
