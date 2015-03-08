package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PrioridadeDao extends CrudDaoDefaultImpl {

	public PrioridadeDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Class getBean() {
		return PrioridadeDTO.class;
	}

	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idPrioridade", "idPrioridade", true, true, false, false));
		listFields.add(new Field("idEmpresa", "idEmpresa", false, false, false, false));
		listFields.add(new Field("nomePrioridade", "nomePrioridade", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("grupoPrioridade", "grupoPrioridade", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return "PRIORIDADE";
	}

	public Collection find(IDto obj) throws PersistenceException {
		List ordem = new ArrayList();
		ordem.add(new Order("nomePrioridade"));
		return super.find(obj, ordem);
	}

	public Collection list() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("situacao", "=", "A"));
		ordenacao.add(new Order("nomePrioridade"));
		return super.findByCondition(condicao, ordenacao);
	}
}
