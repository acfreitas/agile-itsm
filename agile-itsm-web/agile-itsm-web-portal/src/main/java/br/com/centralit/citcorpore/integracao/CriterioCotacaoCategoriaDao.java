package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CriterioCotacaoCategoriaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CriterioCotacaoCategoriaDao extends CrudDaoDefaultImpl {
	public CriterioCotacaoCategoriaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idCategoria" ,"idCategoria", true, false, false, false));
		listFields.add(new Field("idCriterio" ,"idCriterio", true, false, false, false));
		listFields.add(new Field("pesoCotacao" ,"pesoCotacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "CriterioCotacaoCategoria";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return CriterioCotacaoCategoriaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdCategoria(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCategoria", "=", parm)); 
		ordenacao.add(new Order("idCriterio"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdCategoria(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCategoria", "=", parm));
		super.deleteByCondition(condicao);
	}
}
