package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ExcecaoCalendarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ExcecaoCalendarioDao extends CrudDaoDefaultImpl {
	public ExcecaoCalendarioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idExcecaoCalendario" ,"idExcecaoCalendario", true, true, false, false));
		listFields.add(new Field("idCalendario" ,"idCalendario", false, false, false, false));
		listFields.add(new Field("idJornada" ,"idJornada", false, false, false, false));
		listFields.add(new Field("tipo" ,"tipo", false, false, false, false));
		listFields.add(new Field("dataInicio" ,"dataInicio", false, false, false, false));
		listFields.add(new Field("dataTermino" ,"dataTermino", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ExcecaoCalendario";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ExcecaoCalendarioDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdCalendario(Integer idCalendario) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCalendario", "=", idCalendario)); 
		ordenacao.add(new Order("dataInicio"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdCalendario(Integer idCalendario) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCalendario", "=", idCalendario));
		super.deleteByCondition(condicao);
	}
	public ExcecaoCalendarioDTO findByIdCalendarioAndData(Integer idCalendario, Date data) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCalendario", "=", idCalendario)); 
		condicao.add(new Condition("dataInicio", "<=", data)); 
		condicao.add(new Condition("dataTermino", ">=", data)); 
		ordenacao.add(new Order("dataInicio"));
		List result = (List) super.findByCondition(condicao, ordenacao);
		if (result != null && !result.isEmpty())
			return (ExcecaoCalendarioDTO) result.get(0);
		else
			return null;
	}
}
