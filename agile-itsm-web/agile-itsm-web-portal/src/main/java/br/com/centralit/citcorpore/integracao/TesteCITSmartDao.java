package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TesteCITSmartDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TesteCITSmartDao extends CrudDaoDefaultImpl  {
	public TesteCITSmartDao() 
	{
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	@Override
	public Collection getFields() 
	{
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idTesteCITSmart", "idTesteCITSmart", true, true, false, false));
		listFields.add(new Field("classe", "classe", false, false, false, false));
		listFields.add(new Field("metodo", "metodo", false, false, false, false));
		listFields.add(new Field("resultado", "resultado", false, false, false, false));
		listFields.add(new Field("data_hora", "dataHora", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() 
	{
		return "testecitsmart";
	}
	public Collection list() throws PersistenceException 
	{
		List list = new ArrayList();
		list.add(new Order("idTesteCITSmart"));
		return super.list(list);
	}
	
	public Collection findByCondition(Integer id) throws Exception 
	{
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		list1.add(new Condition("idTesteCITSmart", "=", id));
		list2.add(new Order("idTesteCITSmart"));
		return super.findByCondition(list1, list2);
	}
	public Collection find(IDto obj) throws PersistenceException 
    {
		List ordem = new ArrayList();
		ordem.add(new Order("idTesteCITSmart"));
		return super.find(obj, ordem);
    }
	@Override
	public Class getBean() {
		return TesteCITSmartDTO.class;
	}
}
