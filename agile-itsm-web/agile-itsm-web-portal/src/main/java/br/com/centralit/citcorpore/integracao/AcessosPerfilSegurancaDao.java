package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AcessosPerfilSegurancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AcessosPerfilSegurancaDao extends CrudDaoDefaultImpl {

	public AcessosPerfilSegurancaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws PersistenceException
	{
	    List order =  new ArrayList();
	    order.add(new Order("idPerfilSeguranca", "ASC"));
	    return super.find(obj, order);
	}
	
	public void deleteByPerfilSeguranca(Integer idPerfilSeguranca) throws PersistenceException {
		List cond = new ArrayList();
		cond.add(new Condition("idPerfilSeguranca", "=", idPerfilSeguranca));
		super.deleteByCondition(cond);
	}
	
	public Collection findByIdPerfilSeguranca(Integer idPerfilSeguranca) throws Exception
	{
		List order =  new ArrayList();
		List cond = new ArrayList();
		
		cond.add(new Condition("idPerfilSeguranca", "=", idPerfilSeguranca));
		
		order.add(new Order("path", "ASC"));
		
		return super.findByCondition(cond, order);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idPerfilSeguranca" ,"idPerfilSeguranca", true, false, false, true));
		listFields.add(new Field("path" ,"path", true, false, false, true));
		
		return listFields;
	}

	public String getTableName() {
		return "ACESSOSPERFILSEGURANCA";
	}

	public Collection list() throws PersistenceException {
		return null;
	}
	
	public Class getBean() {
		return AcessosPerfilSegurancaDTO.class;
	}
}