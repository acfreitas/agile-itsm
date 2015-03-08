package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.NagiosNDOObjectDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class NagiosNDOObjectDao extends CrudDaoDefaultImpl {
	public NagiosNDOObjectDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("object_id" ,"object_id", true, true, false, false));
		listFields.add(new Field("instance_id" ,"instance_id", false, false, false, false));
		listFields.add(new Field("objecttype_id" ,"objecttype_id", false, false, false, false));
		listFields.add(new Field("name1" ,"name1", false, false, false, false));
		listFields.add(new Field("name2" ,"name2", false, false, false, false));
		listFields.add(new Field("is_active" ,"is_active", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "nagios_objects";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return NagiosNDOObjectDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByName1(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("name1", "=", parm)); 
		ordenacao.add(new Order("object_id"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByName1(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("name1", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByName2(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("name2", "=", parm)); 
		ordenacao.add(new Order("object_id"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByName2(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("name2", "=", parm));
		super.deleteByCondition(condicao);
	}
}
