package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.EventoGrupoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class EventoGrupoDao extends CrudDaoDefaultImpl {

	public EventoGrupoDao() {
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
		listFields.add(new Field("IDGRUPO", "idGrupo", true, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		return "EVENTOGRUPO";
	}

	@Override
	public Collection<?> list() throws PersistenceException {
		return null;
	}

	@Override
	public Class<?> getBean() {
		return EventoGrupoDTO.class;
	}

	public void deleteByIdEvento(Integer idEvento) throws PersistenceException {
		List<Condition> lstCondicao = new ArrayList<Condition>();
		lstCondicao.add(new Condition(Condition.AND, "idEvento", "=", idEvento));
		super.deleteByCondition(lstCondicao);
	}

	@SuppressWarnings("unchecked")
	public Collection<EventoGrupoDTO> listByEvento(Integer idEvento) throws PersistenceException {
		String sql = "SELECT idevento, idgrupo FROM " + getTableName() +" WHERE idevento = ?";
		List<?> dados = this.execSQL(sql, new Object[] { idEvento });
		List<String> fields = new ArrayList<String>();
		fields.add("idEvento");
		fields.add("idGrupo");
		return this.listConvertion(getBean(), dados, fields);
	}

	/**
	 *
	 * @param idGrupo
	 * @return Collection de Contrato
	 * @throws Exception
	 */
	public Collection findByIdGrupo(Integer idGrupo) throws PersistenceException {
		List<Condition> condicao = new ArrayList<>();
		List<Order> ordenacao = new ArrayList<>();
		condicao.add(new Condition("idGrupo", "=", idGrupo));
		ordenacao.add(new Order("idGrupo"));
		return super.findByCondition(condicao, ordenacao);
	}

}
