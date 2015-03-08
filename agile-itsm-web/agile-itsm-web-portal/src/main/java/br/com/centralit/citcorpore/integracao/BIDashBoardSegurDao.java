package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.BIDashBoardSegurDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class BIDashBoardSegurDao extends CrudDaoDefaultImpl {
	public BIDashBoardSegurDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idGrupo" ,"idGrupo", true, false, false, false));
		listFields.add(new Field("idDashBoard" ,"idDashBoard", true, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "BI_DashBoardSegur";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return BIDashBoardSegurDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdPerfilSeguranca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idGrupo", "=", parm)); 
		ordenacao.add(new Order("idDashBoard"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdPerfilSeguranca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idGrupo", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdDashBoard(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idDashBoard", "=", parm)); 
		ordenacao.add(new Order("idGrupo"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdDashBoard(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idDashBoard", "=", parm));
		super.deleteByCondition(condicao);
	}
}
