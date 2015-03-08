package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.BIItemDashBoardDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class BIItemDashBoardDao extends CrudDaoDefaultImpl {
	public BIItemDashBoardDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idItemDashBoard" ,"idItemDashBoard", true, true, false, false));
		listFields.add(new Field("idDashBoard" ,"idDashBoard", false, false, false, false));
		listFields.add(new Field("idConsulta" ,"idConsulta", false, false, false, false));
		listFields.add(new Field("titulo" ,"titulo", false, false, false, false));
		listFields.add(new Field("posicao" ,"posicao", false, false, false, false));
		listFields.add(new Field("itemTop" ,"itemTop", false, false, false, false));
		listFields.add(new Field("itemLeft" ,"itemLeft", false, false, false, false));
		listFields.add(new Field("itemWidth" ,"itemWidth", false, false, false, false));
		listFields.add(new Field("itemHeight" ,"itemHeight", false, false, false, false));
		listFields.add(new Field("parmsSubst" ,"parmsSubst", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "BI_ItemDashBoard";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return BIItemDashBoardDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdDashBoard(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idDashBoard", "=", parm)); 
		ordenacao.add(new Order("idItemDashBoard"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdDashBoard(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idDashBoard", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdConsulta(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idConsulta", "=", parm)); 
		ordenacao.add(new Order("idItemDashBoard"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdConsulta(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idConsulta", "=", parm));
		super.deleteByCondition(condicao);
	}
}
