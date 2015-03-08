package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.VisaoPersonalizadaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class VisaoPersonalizadaDao extends CrudDaoDefaultImpl {
	public VisaoPersonalizadaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idvisao" ,"idvisao", true, false, false, false));
		listFields.add(new Field("personalizada" ,"personalizada", false, false, false, false));
		listFields.add(new Field("dataModif" ,"dataModif", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "VisaoPersonalizada";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return VisaoPersonalizadaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public void deleteAll() throws PersistenceException {
	    Condition cond = new Condition("idvisao", "<>", -1);
	    List lst = new ArrayList();
	    lst.add(cond);
	    super.deleteByCondition(lst);
	}
}
