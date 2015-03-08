package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.EventoItemConfigRelDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

/**
 * DAO da tabela de relacionamento entre item de configuração e evento
 * 
 * @author diego.rezende
 *
 */
public class EventoItemConfigRelDao extends CrudDaoDefaultImpl {

	public EventoItemConfigRelDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	@Override
	public Collection<?> find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<Field>();
		listFields.add(new Field("IDEVENTO", "idEvento", true, false, false, false));
		listFields.add(new Field("IDITEMCONFIGURACAO", "idItemConfiguracao", true, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		return "EVENTOITEMCONFIGURACAO";
	}

	@Override
	public Collection<?> list() throws PersistenceException {
		return null;
	}

	@Override
	public Class<?> getBean() {
		return EventoItemConfigRelDTO.class;
	}
	
	public void deleteByIdEvento(Integer idEvento) throws PersistenceException {
		List<Condition> lstCondicao = new ArrayList<Condition>();
		lstCondicao.add(new Condition(Condition.AND, "idEvento", "=", idEvento));
		super.deleteByCondition(lstCondicao);
	}

	@SuppressWarnings("unchecked")
	public Collection<EventoItemConfigRelDTO> listByEvento(Integer idEvento) throws PersistenceException {
		String sql = "SELECT idevento, iditemconfiguracao FROM "
				+ getTableName() +" WHERE idevento = ?";
		List<?> dados = this.execSQL(sql, new Object[] { idEvento });
		List<String> fields = new ArrayList<String>();
		fields.add("idEvento");
		fields.add("idItemConfiguracao");
		return this.listConvertion(getBean(), dados, fields);
	}

}
